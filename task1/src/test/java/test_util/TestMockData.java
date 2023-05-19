package test_util;

import code.CodeGenerator;
import code.CodeGeneratorConfigure;
import code.CodeGeneratorConfigGetter;
import mock.MockupData;
import mock.MockupDataImpl;
import shop.Item;
import shop.Shop;
import voucher.Voucher;
import voucher.VoucherDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

import static voucher.Voucher.Status.USED;

/** 테스트에 맞게 데이터를 가공하기 위해 만든 클래스입니다. 실제 런타임 시 사용되는 클래스는 {@link MockupDataImpl} 입니다.
 * 해당 타입의 인스턴스가 생성될 때 상품 교환권({@link Voucher})이 20장 발급되어 {@code voucherStorage}에 저장됩니다.
 * 상품 코드의 길이, 문자열타입 등의 설정은 클래스의 생성자 파라미터 값인 {@link CodeGeneratorConfigure}의 설정을 통해 가능합니다. */

public class TestMockData implements MockupData, CodeGeneratorConfigGetter {

    private final Map<String, Voucher> voucherStorage = new HashMap<>();
    private final CodeGenerator itemCodeGenerator;
    private final CodeGenerator shopCodeGenerator;
    private final HashSet<String> itemCode;
    private final HashSet<String> shopCode;

    public TestMockData(CodeGeneratorConfigure itemCodeConfig, CodeGeneratorConfigure shopCodeConfig) {
        this.itemCodeGenerator = CodeGenerator.build(itemCodeConfig);
        this.itemCode = itemCodeGenerator.generate();
        this.shopCodeGenerator = CodeGenerator.build(shopCodeConfig);
        this.shopCode = shopCodeGenerator.generate();
        setupStorage();
    }

    public TestMockData() {
        this.itemCodeGenerator = CodeGenerator.build();
        this.itemCode = itemCodeGenerator.generate();
        this.shopCodeGenerator = CodeGenerator.build(
                CodeGeneratorConfigure.builder()
                        .length(6)
                        .amount(4)
                        .charset(CodeGeneratorConfigure.Charset.ALPHABET)
                        .space(0)
                        .build());
        this.shopCode = shopCodeGenerator.generate();
        setupStorage();
    }

    private void setupStorage() {
        System.out.println("USING TEST MOCK DB");
        List<Voucher> vouchers = populateVoucher();

        for (Voucher voucher : vouchers) {
            Voucher voucher1 = voucher.updateStatus();
            voucherStorage.put(voucher1.getCode(), voucher1);
        }
    }

    private List<Voucher> populateVoucher() {
        List<Voucher> vouchers = new ArrayList<>();
        List<Item> items = populateItem();

        for (int i = 0; i < itemCode.size(); i++) {

            String code = (String) itemCode.toArray()[i];
            Item item = items.get(i);
            VoucherDate voucherDate = VoucherDate.of(
                    LocalDateTime.of(2023, 1, 1, 0, 0),
                    buildRandomDateTime());
            Voucher voucher = Voucher.of(item.getId(), item, code, voucherDate);
            vouchers.add(voucher);

        }

        return vouchers;
    }

    private List<Item> populateItem() {
        List<Shop> shops = populateShop();
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Shop shop = shops.get(i / 5);
            Item item = Item.of((i + 1), "엄마는외계인", shop);
            shop.addItem(item);
            items.add(item);
        }

        return items;
    }

    private List<Shop> populateShop() {
        List<Shop> shops = new ArrayList<>();
        int idx = 1;

        for (String s : shopCode) {
            shops.add(Shop.of(idx++, s));
        }

        return shops;
    }

    @Override
    public void save(Voucher voucher) {
        voucherStorage.put(voucher.getCode(), voucher);
    }

    @Override
    public CodeGeneratorConfigure getItemCodeConfig() {
        return itemCodeGenerator.getConfig();
    }

    @Override
    public CodeGeneratorConfigure getShopCodeConfig() {
        return shopCodeGenerator.getConfig();
    }

    public String getAnyCode() {
        return itemCode.stream().findAny().orElseThrow();
    }

    public String getAnyShopCode() {
        return shopCode.stream().findAny().orElseThrow();
    }

    @Override
    public Voucher getOne(String code) {
        return voucherStorage.get(code);
    }

    public String getExpiredCode() {
        return voucherStorage.values().stream()
                .findAny()
                .map(v -> Voucher.of(v.getId(), v.getItem(), v.getCode(), v.getAuditDate(), Voucher.Status.EXPIRED))
                .get().getCode();
    }

    public Voucher getRandomVoucher() {
        return voucherStorage.entrySet().stream().findAny().get().getValue();
    }

    public Voucher getRandomExpiredVoucher() {
        return voucherStorage.values().stream()
                .filter(v -> v.getStatus() != USED
                        && v.getAuditDate().getExpirationDate().isBefore(LocalDateTime.of(2023, 1, 31, 0, 0)))
                .findAny()
                .get();
    }

    public Voucher getRandomAvailableVoucher() {
        return voucherStorage.values().stream()
                .filter(v -> v.getAuditDate().getExpirationDate().isAfter(LocalDateTime.now()))
                .findAny()
                .get();
    }

    public String getRandomCodeFromDifferentShop(String itemCode) {
        Voucher voucher = voucherStorage.get(itemCode);
        String shopCode = voucher.getItem().getShop().getCode();

        return voucherStorage.values().stream()
                .filter(v -> !v.getItem().getShop().getCode().equals(shopCode))
                .findAny().get().getCode();
    }

    private LocalDateTime buildRandomDateTime() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.now();
        Random random = new Random();

        Period period = Period.between(start, end);

        int days = period.getDays() + 1;
        int months = period.getMonths() + 3;

        return start
                .plusDays(random.nextInt(32))
                .plusMonths((random.nextInt(13)))
                .atTime(23, 59);
    }
}
