package code;

/**
 * 코드 생성을 위한 설정 파일입니다. 생성될 코드의 길이, 개수, 인코딩 등의 설정을 할 수 있습니다.
 * 파라미터의 개수가 많으므로 빌더 패턴으로 세팅할 수 있게 구성하였습니다.
 *
 * <pre>
 *     {@code
 *      CodeGeneratorConfigure config =
 *              CodeGeneratorConfigure.builder().length(6).amount(4).build();
 *      CodeGenerator generator = CodeGenerator.build(config);
 *      HashSet<String> code = generator.generate();
 *      }
 * </pre>
 * 위와 같은 설정을 했을 때 {@code "aBcDef"}와 같이 띄어쓰기 없이 알파벳 대소문자로 이루어진 길이 6의 {@code String}이 4개 생성됩니다.
 * 아무 설정도 하지 않을 경우 {@code "### ### ###"}와 같이 숫자로 이루어진 길이 9의 {@code String}이 20개 생성됩니다.
 *  */

public class CodeGeneratorConfigure {

    private final Integer length;
    private final Integer amount;
    private final String charset;
    private final Integer space;

    public static class Charset {
        public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijlmnopqrstuvwxyz";
        public static final String ALPHABET_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String ALPHABET_LOWERCASE = "abcdefghijlmnopqrstuvwxyz";
        public static final String NUMERIC = "0123456789";
    }

    private CodeGeneratorConfigure(Integer length, Integer amount, String charset, Integer space) {
        if (length == null) {
            length = 9;
        }

        if (amount == null) {
            amount = 20;
        }

        if (charset == null) {
            charset = Charset.NUMERIC;
        }

        if (space == null) {
            space = 3;
        }

        this.length = length;
        this.charset = charset;
        this.amount = amount;
        this.space = space;
    }

    private CodeGeneratorConfigure(Builder builder) {
        this.length = builder.length;
        this.charset = builder.charset;
        this.amount = builder.amount;
        this.space = builder.space;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getCharset() {
        return charset;
    }

    public Integer getSpace() {
        return space;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int length = 9;
        private String charset = Charset.NUMERIC;
        private int amount = 20;
        private int space = 3;

        public Builder length(int length) {
            this.length = length;
            return this;
        }

        public Builder charset(String charset) {
            this.charset = charset;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder space(int space) {
            this.space = space;
            return this;
        }

        public CodeGeneratorConfigure build(){
            return new CodeGeneratorConfigure(this);
        }

    }
}
