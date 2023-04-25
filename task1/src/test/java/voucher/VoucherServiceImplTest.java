package voucher;

import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import mock.TestMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class VoucherServiceImplTest {

    private final TestMockData mockData = new TestMockData();
    private final VoucherServiceImpl voucherService = new VoucherServiceImpl(new VoucherRepositoryImpl(mockData));

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
        assertEquals(throwable.getMessage(), CustomRuntimeExceptionCode.EXPIRED_CODE.getMessage());

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
        assertEquals(throwable.getMessage(), CustomRuntimeExceptionCode.ALREADY_USED_CODE.getMessage());

    }

    @Test
    void givenNotMatchedShopCodeAndItemCode_whenClaimRequested_thenThrows() {

        //given
        String itemCode = mockData.getAnyCode();
        String randomCode = mockData.getRandomCodeFromDifferentShop(itemCode);
        String differentShopCode = mockData.getOne(randomCode).getItem().getShop().getCode();

        //then
        Throwable throwable = assertThrows(CustomRuntimeException.class, () -> voucherService.claim(differentShopCode, itemCode));
        assertEquals(throwable.getMessage(), CustomRuntimeExceptionCode.SHOPCODE_NOT_MATCHED.getMessage());

    }

}