package voucher;

/** 컨트롤러에서 검증된 요청을 처리하는 서비스 인터페이스입니다.
 * 기본 구현체는 {@link VoucherServiceImpl} 이며 문자열로 결과를 출력하도록 합니다.*/

public interface VoucherService {

    String check(String code);

    String claim(String shopCode, String ItemCode);

}
