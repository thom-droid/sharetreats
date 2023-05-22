package voucher;

import mock.MockupData;
import mock.MockupDataImpl;

import java.util.*;

/** {@link VoucherRepository} 구현체입니다. {@link MockupData} 인터페이스를 주입하여 테스트할 때에는 다른 구현체를 사용할 수 있도록 했습니다. */

public class VoucherRepositoryImpl implements VoucherRepository {

    private static VoucherRepository instance;
    private final MockupData mockDb;

    public static VoucherRepository getInstance() {
        if (instance == null) {
            MockupData mockupData = MockupDataImpl.getInstance();
            instance = new VoucherRepositoryImpl(mockupData);
        }
        return instance;
    }

    public static VoucherRepository getInstance(MockupData mockupData) {
        if (instance == null) {
            instance = new VoucherRepositoryImpl(mockupData);
        }
        return instance;
    }

    private VoucherRepositoryImpl(MockupData mockDb) {
        this.mockDb = mockDb;
    }

    @Override
    public Optional<Voucher> findByCode(String code) {
        return Optional.ofNullable(mockDb.getOne(code));
    }

    @Override
    public void update(Voucher voucher) {
        mockDb.save(voucher);
    }

}
