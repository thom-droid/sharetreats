package mock;

import code.CodeGenerator;
import code.CodeGeneratorConfigurer;
import code.CodeGeneratorConfigGetter;
import shop.Item;
import shop.Shop;
import voucher.Voucher;
import voucher.VoucherDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;


/**
 * <p>
 *     데이터베이스 역할을 하는 클래스입니다. 기본 생성자를 통해 인스턴스가 생성될 때 {@link MockupDataImpl#setupStorage}가
 *     호출되어 예시로 사용할 교환권 20장을 생성합니다.
 *     이 설정은 생성자 오버라이딩의 파라미터로 {@link CodeGeneratorConfigurer}를 사용함으로써 변경할 수 있습니다.
 * </p>
 * <p>
 *     코드자체는 중복을 막기 위해 {@link HashSet}으로 생성되며 {@code itemCode}에 할당됩니다.
 *     실제 저장소의 역할은 {@link HashMap}인 {@code voucherStorage}가 하게 되며 {@code itemCode}의 값이 key, 상품 정보와
 *     교환권 상태 등의 정보를 담은 {@link Voucher}가 value로 사용됩니다.
 * </p>
 * */

public class MockupDataImpl implements MockupData, CodeGeneratorConfigGetter {

    private final Map<String, Voucher> voucherStorage = new HashMap<>();
    private final CodeGenerator itemCodeGenerator;
    private final CodeGenerator shopCodeGenerator;
    private final HashSet<String> itemCode;
    private final HashSet<String> shopCode;

    public MockupDataImpl(CodeGeneratorConfigurer itemCodeConfig, CodeGeneratorConfigurer shopCodeConfig) {
        this.itemCodeGenerator = CodeGenerator.build(itemCodeConfig);
        this.itemCode = itemCodeGenerator.generate();
        this.shopCodeGenerator = CodeGenerator.build(shopCodeConfig);
        this.shopCode = shopCodeGenerator.generate();
        setupStorage();
    }

    public MockupDataImpl() {
        this.itemCodeGenerator = CodeGenerator.build();
        this.itemCode = itemCodeGenerator.generate();
        this.shopCodeGenerator = CodeGenerator.build(CodeGeneratorConfigurer.create(6, 4, CodeGeneratorConfigurer.Charset.ALPHABET, 0));
        this.shopCode = shopCodeGenerator.generate();
        setupStorage();
    }

    private void setupStorage() {
        List<Voucher> vouchers = populateVoucher();

        for (Voucher voucher : vouchers) {
            Voucher voucher1 = voucher.updateStatus();
            voucherStorage.put(voucher.getCode(), voucher1);
            System.out.println(voucher1);
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

    private LocalDateTime buildRandomDateTime() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 12, 31);
        Random random = new Random();

        Period period = Period.between(start, end);

        int days = period.getDays() + 1;
        int months = period.getMonths() + 3;

        // random date from 20230101 ~ 20231231
        return start
                .plusDays(random.nextInt(32))
                .plusMonths((random.nextInt(13)))
                .atTime(23, 59);
    }

    @Override
    public Voucher getOne(String code) {
        return voucherStorage.get(code);
    }

    @Override
    public void save(Voucher voucher) {
        voucherStorage.put(voucher.getCode(), voucher);
    }

    @Override
    public CodeGeneratorConfigurer getItemCodeConfig() {
        return itemCodeGenerator.getConfig();
    }

    @Override
    public CodeGeneratorConfigurer getShopCodeConfig() {
        return shopCodeGenerator.getConfig();
    }
}
