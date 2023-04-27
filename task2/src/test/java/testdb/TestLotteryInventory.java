package testdb;

import item.Item;
import lottery.LotteryInventory;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class TestLotteryInventory implements LotteryInventory {

    private List<Item> gradeA;
    private List<Item> gradeB;
    private final int chanceA = 90;
    private final int chanceB = 10;
    private final int whole = chanceA + chanceB;
    private static final Random random = new Random();
    private int countB;

    public TestLotteryInventory() {
        this.gradeA = createAItems();
        this.gradeB = createBItems();
        this.countB = 3;
    }

    @Override
    public Optional<Item> pick(LocalDateTime drawTime) {
        Optional<Item> optionalItem = pickAnyA();

        if (pickAnyA().isEmpty()) {
            optionalItem = pickAnyB();
        }

        return optionalItem;
    }

    public Optional<Item> pick() {
        Optional<Item> optionalItem = pickAnyA();

        if (pickAnyA().isEmpty()) {
            optionalItem = pickAnyB();
        }

        return optionalItem;
    }

    public Optional<Item> pickAnyA() {
        int chance = random.nextInt(whole);
        return chanceA >= chance ? gradeA.stream().findAny() : Optional.empty();
    }

    public Optional<Item> pickAnyB() {
        int chance = random.nextInt(whole);
        return chanceB >= chance ? gradeB.stream().findAny() : Optional.empty();
    }

    public Optional<Item> pickBWithCount(LocalDateTime drawTime) {
        if (countB != 0) {

            int chance = random.nextInt(chanceA + chanceB);

            if (chanceB >= chance) {
                countB--;
                Collections.shuffle(gradeB);
                return gradeB.stream().filter(i -> i.getDueDate().isBefore(drawTime)).findAny();
            }
        }

        return Optional.empty();
    }

    private ArrayList<Item> createAItems() {

        String[] names = new String[]{"COKE", "SODA", "AMERICANO", "CAFE LATTE", "CHOCOLATE"};
        ArrayList<Item> items = new ArrayList<>();

        for (String name : names) {
            Item item = Item.of(name, Item.Grade.A, buildRandomDateTime());
            items.add(item);
        }

        return items;
    }

    private ArrayList<Item> createBItems() {

        String[] names = new String[]{"CHICKEN", "PIZZA", "BURGER", "PASTA", "STEAK"};

        return Arrays.stream(names)
                .map(s -> Item.of(s, Item.Grade.B, buildRandomDateTime()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private LocalDateTime buildRandomDateTime() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.now();
        Random random = new Random();

        Period period = Period.between(start, end);

        int days = period.getDays() + 1;
        int months = period.getMonths() + 1;

        return start
                .plusDays(random.nextInt(days))
                .plusMonths((random.nextInt(months)))
                .atTime(23, 59);
    }

    public List<Item> getGradeA() {
        return gradeA;
    }

    public List<Item> getGradeB() {
        return gradeB;
    }

    public int getCountB() {
        return countB;
    }

    public int getChanceA() {
        return chanceA;
    }

    public int getChanceB() {
        return chanceB;
    }

}
