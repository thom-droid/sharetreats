package lottery;

import item.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class LotteryInventoryImpl implements LotteryInventory {

    private final List<Item> gradeA;
    private final List<Item> gradeB;
    private static final int chanceA = 90;
    private static final int chanceB = 10;
    private static final Random random = new Random();
    private int countB;

    public LotteryInventoryImpl() {
        this.gradeA = createAItem();
        this.gradeB = createBItem();
        this.countB = 3;
    }

    @Override
    public Optional<Item> pick(LocalDateTime drawTime) {
        Optional<Item> optionalItem = pickA(drawTime);

        if (optionalItem.isEmpty()) {
            optionalItem = pickB(drawTime);
        }

        return optionalItem;

    }

    private Optional<Item> pickA(LocalDateTime drawTime) {

        int chance = random.nextInt(chanceA + chanceB);

        if (chanceA >= chance) {
            Collections.shuffle(gradeA);
            return gradeA.stream().filter(i -> i.getDueDate().isBefore(drawTime)).findAny();
        }

        return Optional.empty();

    }

    private Optional<Item> pickB(LocalDateTime drawTime) {
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

    private ArrayList<Item> createAItem() {

        String[] names = new String[]{"COKE", "SODA", "AMERICANO", "CAFE LATTE", "CHOCOLATE"};
        ArrayList<Item> items = new ArrayList<>();

        for (String name : names) {
            Item item = Item.of(name, Item.Grade.A, buildRandomDateTime());
            items.add(item);
            System.out.println(item);
        }

        return items;

    }

    private ArrayList<Item> createBItem() {

        String[] names = new String[]{"CHICKEN", "PIZZA", "BURGER", "PASTA", "STEAK"};

        return Arrays.stream(names)
                .map(s -> Item.of(s, Item.Grade.B, buildRandomDateTime()))
                .peek(System.out::println)
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

}
