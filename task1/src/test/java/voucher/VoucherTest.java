package voucher;

import org.junit.jupiter.api.Test;
import shop.Item;
import shop.Shop;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoucherTest {

    @Test
    void checkAvailability() {

        //given
        Shop shop = Shop.of(1, "shop1");
        Item item = Item.of(1, "딸기맛바나나", shop);
        VoucherDate voucherDate = VoucherDate.of(
                LocalDateTime.of(2023, 4, 5, 23, 59),
                VoucherDate.ExpirationPeriod.WEEK);
        Voucher voucher = Voucher.of(1, item, "voucher1", voucherDate);
        Voucher.Status expected = Voucher.Status.EXPIRED;

        //when
        Voucher updated = voucher.updateStatus();

        //then
        assertEquals(expected, updated.getStatus());

    }
}