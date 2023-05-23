package voucher;

import java.time.LocalDateTime;

/** 상품 교환권이 발급된 날짜, 만료 날짜, 사용된 날짜의 정보를 포함하고 있는 불변 클래스입니다.
 *
 * */
public final class VoucherDate {

    private final LocalDateTime issueDate;
    private final LocalDateTime expirationDate;
    private final LocalDateTime usedDate;
    private final ExpirationPeriod expirationPeriod;

    public static VoucherDate of(VoucherDate voucherDate) {
        return new VoucherDate(voucherDate);
    }

    public static VoucherDate of(ExpirationPeriod expirationPeriod) {
        return new VoucherDate(expirationPeriod);
    }

    public static VoucherDate of(LocalDateTime issueDate, ExpirationPeriod expirationPeriod) {
        return new VoucherDate(issueDate, null, expirationPeriod, null);
    }

    public static VoucherDate of(LocalDateTime issueDate, LocalDateTime expirationDate) {
        return new VoucherDate(issueDate, expirationDate, null, null);
    }

    private VoucherDate(ExpirationPeriod expirationPeriod) {
        this.issueDate = LocalDateTime.now();
        this.expirationDate = issueDate.plusDays(expirationPeriod.getDays());
        this.expirationPeriod = expirationPeriod;
        this.usedDate = null;
    }

    private VoucherDate(LocalDateTime issueDate, LocalDateTime expirationDate, ExpirationPeriod expirationPeriod, LocalDateTime usedDate) {
        this.issueDate = issueDate;
        this.expirationPeriod = expirationPeriod;
        this.expirationDate = expirationDate == null ? issueDate.plusDays(expirationPeriod.getDays()) : expirationDate;
        this.usedDate = usedDate;
    }

    private VoucherDate(VoucherDate voucherDate) {
        this.issueDate = voucherDate.getIssueDate();
        this.expirationDate = voucherDate.getExpirationDate();
        this.expirationPeriod = voucherDate.getExpirationPeriod();
        this.usedDate = voucherDate.getUsedDate();
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public ExpirationPeriod getExpirationPeriod() {
        return expirationPeriod;
    }

    public LocalDateTime getUsedDate() {
        return usedDate;
    }

    public VoucherDate updateUsedDate() {
        return new VoucherDate(this.issueDate, this.expirationDate, this.expirationPeriod, LocalDateTime.now());
    }

    public enum ExpirationPeriod {

        DAY(1),
        WEEK(7),
        TWO_WEEKS(14),
        MONTH(30),
        THREE_MONTHS(90);

        final int days;

        ExpirationPeriod(int days) {
            this.days = days;
        }

        public int getDays() {
            return days;
        }

    }

}
