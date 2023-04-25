package voucher;

import java.util.Optional;

/** 상품 교환권을 검색하고 저장하는 저장소 인터페이스입니다. {@link mock.MockupData}를 통해 교환권을 관리하게 됩니다.
 * {@code null} 결과값을 방지하기 위해 {@link Optional<Voucher>}를 리턴하도록 했습니다. */

public interface VoucherRepository {

    Optional<Voucher> findByCode(String code);

    void update(Voucher voucher);

}
