package lottery;

import item.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *     {@link LotteryInventory} 구현체입니다.
 *     인스턴스화할 때 각 등급에 맞는 상품을 생성하여 {@link List}로 관리합니다.
 * </p>
 * <p>
 *     상품의 당첨 확률은 {@link Random} 클래스를 활용했습니다. A 상품의 당첨 확률은 90,
 *     B 상품은 10으로 설정하고, {@code random.nextInt()}로 1부터 100까지의 숫자를 임의로 뽑아
 *     나온 숫자가 상품의 당첨 확률에 해당하는지 확인하는 방식으로 동작하도록 설정했습니다. 당첨의 유무는
 *     {@code Optional<Item>}의 값이 null이면 꽝, 그렇지 않으면 당첨으로 구분할 수 있게 했습니다.
 *
 * </p>
 * <p>
 *     핵심 메서드인 pick()은 다음과 같이 동작합니다.
 *
 *     <li>
 *         숫자를 뽑습니다. 1 ~ 90 이면 A 상품을 뽑습니다. 결과를 담은 {@code Optional<Item>}를
 *         리턴합니다.
 *     </li>
 *     <li>
 *         리턴한 {@code Optional}의 값이 null이 아니라면 A가 당첨된 것이므로 리턴합니다.
 *     </li>
 *     <li>
 *         그렇지 않으면 꽝이므로, 다시 숫자를 뽑아 B 당첨 확률에 해당하는지 확인하고 {@code Optional}
 *         객체를 리턴합니다. 이 때 B 상품은 총 3번까지만 당첨될 수 있으므로, 당첨된 경우 countB 필드의
 *         값을 감소시킵니다.
 *     </li>
 *     <li>
 *         이 메서드를 호출한 객체는 리턴값의 내용물을 확인하고 값이 비었으면 꽝, 그렇지 않으면 상품의 정보를
 *         출력할 수 있게 됩니다.
 *     </li>
 * </p>
 *
 * */

public class LotteryInventoryImpl implements LotteryInventory {

    private static final String[] A_ITEM_NAMES = new String[]{"COKE", "SODA", "AMERICANO", "CAFE LATTE", "CHOCOLATE"};
    private static final String[] B_ITEM_NAMES = new String[]{"CHICKEN", "PIZZA", "BURGER", "PASTA", "STEAK"};

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
        System.out.println("A 등급 상품 : " +Arrays.toString(A_ITEM_NAMES));
        System.out.println("B 등급 상품 : " +Arrays.toString(B_ITEM_NAMES));
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
        ArrayList<Item> items = new ArrayList<>();

        for (String name : A_ITEM_NAMES) {
            Item item = Item.of(name, Item.Grade.A, buildRandomDateTime());
            items.add(item);

        }

        return items;
    }

    private ArrayList<Item> createBItem() {
        return Arrays.stream(B_ITEM_NAMES)
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

}
