package mock;

import voucher.Voucher;


/** database 역할을 하는 인터페이스입니다. */

public interface MockupData {

    Voucher getOne(String code);

    void save(Voucher voucher);

}
