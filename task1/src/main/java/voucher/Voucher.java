package voucher;

import exception.CustomRuntimeException;
import exception.CustomRuntimeExceptionCode;
import shop.Item;
import util.Formatter;

import java.time.LocalDateTime;

/** 교환권을 의미하는 클래스입니다. 비즈니스 로직에 사용되는 메서드를 포함하고 있으며,
 * 임의로 값을 변경하지 않도록 불변한 클래스로 설정하였습니다.
 * 다른 객체의 참조를 표현하는 composite 클래스도 불변하게 설정하였고, {@code get()}로 값을 얻어올 때에는
 * 새로운 객체를 리턴하여 실제 객체로의 접근을 막도록 했습니다.
 * */

public final class Voucher {

    private final int id;
    private final Status status;
    private final Item item;
    private final String code;
    private final VoucherDate voucherDate;

    public static Voucher of(int id, Item item, String code, VoucherDate voucherDate) {
        return new Voucher(id, item, code, null, voucherDate, null);
    }

    public static Voucher of(int id, Item item, String code, VoucherDate voucherDate, Status status) {
        return new Voucher(id, item, code, null, voucherDate, status);
    }

    private Voucher(int id, Item item, String code, VoucherDate.ExpirationPeriod expiresAt, VoucherDate voucherDate, Status status) {
        this.id = id;
        this.item = item;
        this.status = status == null ? Status.AVAILABLE : status;
        this.code = code;
        this.voucherDate = expiresAt == null ? voucherDate : VoucherDate.of(expiresAt);
    }

    public int getId() {
        return id;
    }

    public Item getItem() {
        return Item.of(item);
    }

    public String getCode() {
        return code;
    }

    public VoucherDate getAuditDate() {
        return VoucherDate.of(voucherDate);
    }

    public void checkShopCode(String shopCode) {
        if (isNotIssuedFrom(shopCode)) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.SHOPCODE_NOT_MATCHED);
        }
    }

    private boolean isNotIssuedFrom(String shopCode) {
        return !item.getShop().getCode().equals(shopCode);
    }

    public void checkAvailability() {
        if(this.status == Voucher.Status.EXPIRED)
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.EXPIRED_CODE);
        if (this.status == Voucher.Status.USED) {
            throw new CustomRuntimeException(CustomRuntimeExceptionCode.ALREADY_USED_CODE);
        }
    }

    public Voucher updateAsUsed() {
        VoucherDate voucherDate = this.voucherDate.updateUsedDate();
        return Voucher.of(this.id, this.item, this.code, voucherDate, Status.USED);
    }

    public Voucher updateStatus() {

        if (this.voucherDate.getUsedDate() != null) {
            return Voucher.of(this.id, this.item, this.code, this.voucherDate, Status.USED);

        } else if (LocalDateTime.now().isAfter(this.voucherDate.getExpirationDate())) {
            return Voucher.of(this.id, this.item, this.code, this.voucherDate, Status.EXPIRED);
        }

        return this;
    }

    public enum Status {

        AVAILABLE(1, "사용 가능"),
        EXPIRED(2, "유효기간 만료"),
        USED(3, "사용 완료");

        final int code;

        final String message;

        Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public Status getStatus() {
        return status;
    }

    public String toStringWithClaimedItem() {
        return "상품 [" + this.getItem().getName() + "] 교환을 성공하였습니다.. \n" +
                "교환코드 : [ " + this.getCode() + " ] \n" +
                "교환상점 : [ " + this.item.getShop().getCode() + " ] \n" +
                "사용날짜 : [ " + Formatter.convertDateToString(this.getAuditDate().getUsedDate()) + " ] ";
    }

    public String toStringWithAvailability() {
        String usedDate = "";
        String message = "상품 [" + this.getItem().getName() + "] 에 대한 정보입니다. \n" +
                "상태 : [ " + this.getStatus().getMessage() + " ] \n" +
                "교환코드 : [ " + this.getCode() + " ] \n" +
                "상점코드 : [ " + this.getItem().getShop().getCode() + " ] \n" +
                "발급날짜 : [ " + Formatter.convertDateToString(this.getAuditDate().getIssueDate()) + " ] \n" +
                "만료날짜 : [ " + Formatter.convertDateToString(this.getAuditDate().getExpirationDate()) + " ] \n";

        if (this.getAuditDate().getUsedDate() != null) {
            usedDate = "\n사용날짜 : [ " + Formatter.convertDateToString(this.getAuditDate().getUsedDate()) + " ] ";
        }
        return message + usedDate;
    }

    @Override
    public String toString() {
        return "상품 : [ " + item.getName() +" ], " +
                "상태 : [ " + status.getMessage() + " ], " +
                "교환코드 : [ " + code + " ], " +
                "교환상점 : [ " + item.getShop().getCode() + " ], "  +
                "발급일자 : [ " + Formatter.convertDateToString(voucherDate.getIssueDate()) + " ], " +
                "만료일자 : [ " + Formatter.convertDateToString(voucherDate.getExpirationDate()) +" ] " +
                '}';
    }

}
