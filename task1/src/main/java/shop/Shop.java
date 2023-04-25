package shop;

import java.util.ArrayList;
import java.util.List;

/** 상점 코드와 상품의 정보를 포함하는 클래스입니다. */

public class Shop {

    private final int id;
    private final String code;
    private final List<Item> items;

    private Shop(int id, String code) {
        this.id = id;
        this.code = code;
        this.items = new ArrayList<>();
    }

    private Shop(int id, String code, List<Item> items) {
        this.id = id;
        this.code = code;
        this.items = items;
    }

    public static Shop of(int id, String code) {
        return new Shop(id, code);
    }

    public static Shop of(Shop shop) {
        return new Shop(shop.getId(), shop.getCode(), shop.getItems());
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        if (!this.items.contains(item)) {
            this.items.add(item);
        }
    }
}
