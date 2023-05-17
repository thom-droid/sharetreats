package code;

/** 코드 생성을 위한 설정 파일입니다. 생성될 코드의 길이, 개수, 인코딩 등의 설정을 할 수 있습니다.
 *  {@code create()}를 통해 인스턴스를 생성할 수 있으며, {@link CodeGenerator}의 생성자의 파라미터로 사용하여 코드 설정이 가능합니다.
 *
 * <pre>{@code
 *      CodeGeneratorConfigurer config =
 *              CodeGeneratorConfigurer.create(6, 4, Charset.ALPHABET, 0);
 *      CodeGenerator generator = CodeGenerator.build(config);
 *      HashSet<String> code = generator.generate();
 * }
 * </pre>
 *
 * 위와 같은 설정을 했을 때 {@code "aBcDef"}와 같이 띄어쓰기 없이 알파벳 대소문자로 이루어진 길이 6의 {@code String}이 4개 생성됩니다.
 * 아무 설정도 하지 않을 경우 {@code "### ### ###"}와 같이 숫자로 이루어진 길이 9의 {@code String}이 20개 생성됩니다.
 * */

public class CodeGeneratorConfigurer {

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

    private CodeGeneratorConfigurer(Integer length, Integer amount, String charset, Integer space) {
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

    public static CodeGeneratorConfigurer create() {
        return new CodeGeneratorConfigurer(null, null, null, null);
    }

    public static CodeGeneratorConfigurer createWithLength(Integer length) {
        return new CodeGeneratorConfigurer(length, null, null, null);
    }

    public static CodeGeneratorConfigurer createWithCharset(String charset) {
        return new CodeGeneratorConfigurer(null, null, charset, null);
    }

    public static CodeGeneratorConfigurer createWithAmount(Integer amount) {
        return new CodeGeneratorConfigurer(null, amount, null, null);
    }

    public static CodeGeneratorConfigurer create(Integer length, Integer amount) {
        return new CodeGeneratorConfigurer(length, amount, null, null);
    }

    public static CodeGeneratorConfigurer create(Integer length, Integer amount, String charset) {
        return new CodeGeneratorConfigurer(length, amount, charset, null);
    }

    public static CodeGeneratorConfigurer create(Integer length, Integer amount, String charset, Integer space) {
        return new CodeGeneratorConfigurer(length, amount, charset, space);
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

    //todo builder 패턴으로 바꿔볼 것
}
