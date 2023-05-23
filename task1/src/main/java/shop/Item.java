package shop;

import java.util.Objects;

/** 상품과 상점의 정보를 포함하는 클래스입니다. */

public final class Item {

    private final int id;
    private final String name;
    private final Shop shop;

    private Item(int id, String name, Shop shop) {
        this.id = id;
        this.name = name;
        this.shop = shop;
    }

    public static Item of(int id, String name, Shop shop) {
        return new Item(id, name, shop);
    }

    public static Item of(Item item) {
        return new Item(item.getId(), item.getName(), item.getShop());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Shop getShop() {
        return Shop.of(shop);
    }

}
