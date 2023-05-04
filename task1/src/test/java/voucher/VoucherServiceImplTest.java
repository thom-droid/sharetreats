package voucher;

import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import mock.CommandTestUtils;
import mock.TestMockData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VoucherServiceImplTest {

    private TestMockData mockData;
    private VoucherService voucherService;

    @BeforeEach
    public void setup() {
        mockData = (TestMockData) CommandTestUtils.MockCommandInitiator.getMockupData();
        voucherService = CommandTestUtils.MockCommandInitiator.getVoucherService();
    }

    @Test
    void givenNull_thenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> voucherService.check(null));
    }

    @Test
    void givenNotVerifiedCode_thenThrowsNoSuchElementException() {

        //given
        String falseCode = "11d0a.c2d";

        //then
        Throwable t = assertThrows(CustomRuntimeException.class, () -> voucherService.check(falseCode));
        System.out.println(t.getMessage());

    }

    @Test
    void givenVerifiedCode_thenChecked() {

        //given
        String code = mockData.getAnyCode();

        //then
        assertDoesNotThrow(() -> voucherService.check(code));

    }

    @Test
    public void givenExpiredCode_whenClaimRequested_thenThrows() {

        //given
        Voucher voucher = mockData.getRandomExpiredVoucher();
        String shopCode = voucher.getItem().getShop().getCode();
        String expiredCode = voucher.getCode();

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> voucherService.claim(shopCode, expiredCode));
        assertEquals(CustomRuntimeExceptionCode.EXPIRED_CODE.getMessage(), throwable.getMessage());

    }

    @Test
    public void givenAlreadyUsedCode_whenClaimRequested_thenThrows() {

        //given
        String shopCode = mockData.getAnyShopCode();
        String usedCode = mockData.getAnyCode();
        Voucher voucher = mockData.getOne(usedCode);

        //when
        Voucher updated = voucher.updateAsUsed();
        mockData.save(updated);

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> voucherService.claim(shopCode, usedCode));
        assertEquals(CustomRuntimeExceptionCode.ALREADY_USED_CODE.getMessage(), throwable.getMessage());

    }

    @Test
    void givenNotMatchedShopCodeAndItemCode_whenClaimRequested_thenThrows() {

        //given
        String itemCode = mockData.getAnyCode();
        String randomCode = mockData.getRandomCodeFromDifferentShop(itemCode);
        String differentShopCode = mockData.getOne(randomCode).getItem().getShop().getCode();

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> voucherService.claim(differentShopCode, itemCode));
        assertEquals(CustomRuntimeExceptionCode.SHOPCODE_NOT_MATCHED.getMessage(), throwable.getMessage());

    }

}