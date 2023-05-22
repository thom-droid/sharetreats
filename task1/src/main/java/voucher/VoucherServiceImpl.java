package voucher;

import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import mock.MockupData;

import java.util.Objects;

/**
 * <p>
 * {@link VoucherService}의 기본 구현체입니다.
 * 유효성 검사를 통과한 코드를 입력받아 저장소로부터 저장된 상품을 검색하고, 상품의 상태를 확인한 뒤 작업을 수행합니다.
 * 저장소로부터 검색 결과가 없다면(null이 리턴된다면) {@link CustomRuntimeException}이 발생합니다.
 * </p>
 *
 * <p> {@code check, claim} 메서드는 기본적으로 다음과 같은 동작을 합니다.
 *
 * <ul>
 *     <li>
 *         {@link VoucherServiceImpl#getVoucherIfValidated} 를 통해 저장소에 접근하여 상품 교환권인 {@link Voucher}를 얻어옵니다.
 *     </li>
 *     <li>
 *         {@link Voucher#updateStatus}를 호출하여 교환권의 사용 여부를 확인합니다.
 *          이 메서드는 {@code usedDate}와 {@code expirationDate}를 확인해서 업데이트된 내용이 있으면
 *          {@link Voucher.Status}의 값을 변경한 새로운 {@code Voucher}를 리턴합니다.
 *     </li>
 *     <li>
 *         업데이트된 내용이 없다면 자신의 레퍼런스({@code this})를 리턴합니다.
 *     </li>
 *     <li>
 *         업데이트된 {@link Voucher}를 저장소에 덮어쓰기 합니다.
 *         저장소는 {@link java.util.HashMap}으로 구현되어 있어 같은 키가 입력될 때 값을 덮어쓰기 합니다.
 *     </li>
 * </ul>
 */

public class VoucherServiceImpl implements VoucherService {

    private static VoucherService instance;
    private final VoucherRepository voucherRepository;

    public static VoucherService getInstance() {
        if (instance == null) {
            VoucherRepository vr = VoucherRepositoryImpl.getInstance();
            instance = new VoucherServiceImpl(vr);
        }
        return instance;
    }

    public static VoucherService getInstance(MockupData mockupData) {
        if (instance == null) {
            VoucherRepository vr = VoucherRepositoryImpl.getInstance(mockupData);
            instance = new VoucherServiceImpl(vr);
        }
        return instance;
    }

    private VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

//    public VoucherServiceImpl(VoucherRepository voucherRepository) {
//        this.voucherRepository = voucherRepository;
//    }

    /**
     * <p>
     * 상품 교환권 코드를 입력받아 사용 가능 여부를 확인하는 메서드입니다.
     *  <ul>
     *      <li>
     *          상품 코드를 검색하여 코드가 유효한지 확인합니다.
     *      </li>
     *      <li>
     *          코드로 찾은 교환권이 상점에서 발급된 것이 맞는지 확인합니다.
     *          아니라면, {@link CustomRuntimeExceptionCode#SHOPCODE_NOT_MATCHED} 메세지를 포함하는 예외를 발생시킵니다.
     *      </li>
     *      <li>
     *          {@link Voucher#updateStatus}로 요청받은 시점에 사용 가능 여부를 확인하여 상태를 최신화합니다.
     *      </li>
     *      <li>
     *          업데이트된 {@code Voucher}를 저장합니다.
     *      </li>
     *      <li>
     *          {@link Voucher#toStringWithAvailability}를 호출하여 교환권 상태에 따른 메시지를 리턴합니다.
     *      </li>
     *  </ul>
     *
     * </p>
     *
     * @param   itemCode 상품 교환권 코드
     * @throws  CustomRuntimeException {@code Voucher.Status.USED}, {@code Voucher.Status.EXPIRED} 일 때
     */

    @Override
    public String check(String itemCode) {
        Voucher found = getVoucherIfValidated(itemCode);
        Voucher updated = found.updateStatus();

        voucherRepository.update(updated);

        return updated.toStringWithAvailability();
    }

    /**
     * <p>
     * 상점 코드와 상품 교환권 코드를 입력받아 상품으로 교환하는 메서드입니다.
     *  <ul>
     *      <li>
     *          상품 코드를 검색하여 코드가 유효한지 확인합니다.
     *      </li>
     *      <li>
     *          코드로 찾은 교환권이 상점에서 발급된 것이 맞는지 확인합니다.
     *          아니라면, {@link CustomRuntimeExceptionCode#SHOPCODE_NOT_MATCHED} 메세지를 포함하는 예외를 발생시킵니다.
     *      </li>
     *      <li>
     *          {@link Voucher#checkAvailability}로 사용가능한 교환권인지 확인합니다.
     *          사용된 교환권이라면 {@link CustomRuntimeExceptionCode#ALREADY_USED_CODE},
     *          만료된 교환권이라면 {@link CustomRuntimeExceptionCode#EXPIRED_CODE} 메시지의 예외가 발생합니다.
     *      </li>
     *      <li>
     *          사용할 수 있는 상태라면 {@link Voucher#updateAsUsed}를 호출합니다.
     *          이 메서드는 교환권의 상태를 {@link voucher.Voucher.Status#USED}로,
     *          {@code Voucher.usedDate}를 현재 요청 날짜로 변경한 새로운 {@code Voucher}를 리턴합니다.
     *      </li>
     *      <li>
     *          업데이트된 {@code Voucher}를 저장하고, 사용된 상품 코드와 사용일자의 내용을 담은 문자열을 리턴합니다.
     *      </li>
     *  </ul>
     *
     * </p>
     *
     * @param   shopCode 상점코드 문자열
     * @param   itemCode 상품 교환권 코드
     * @throws  CustomRuntimeException {@code Voucher.Status.USED}, {@code Voucher.Status.EXPIRED} 일 때,
     *          {@code itemCode, shopCode}가 {@code null}일 때
     */

    @Override
    public String claim(String shopCode, String itemCode) {
        Voucher found = getVoucherIfValidated(itemCode);
        found.checkShopCode(shopCode);
        Voucher updated = found.updateStatus();
        voucherRepository.update(updated);

        updated.checkAvailability();

        Voucher used = updated.updateAsUsed();
        voucherRepository.update(used);

        return used.toStringWithClaimedItem();
    }

    private Voucher getVoucherIfValidated(String code) {
        Objects.requireNonNull(code);
        return voucherRepository.findByCode(code).orElseThrow(
                () -> new CustomRuntimeException(CustomRuntimeExceptionCode.NOT_VALID_CODE));
    }

}
