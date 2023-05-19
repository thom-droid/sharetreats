package code;

import java.util.HashSet;
import java.util.Random;

/** 상품 교환권 코드, 상점 코드를 생성하는 클래스입니다. 인스턴스가 생성되면 변수가 수정되지 않는 불변 클래스입니다.
 * <p>
 * {@link CodeGeneratorConfigurer}로 코드 생성을 위한 설정을 한 뒤 {@code build()}를 호출하여 인스턴스를 생성할 수 있습니다.
 * 설정 파일이 따로 주어지지 않는 경우 기본 설정 값으로 '### ### ###' 형태의 숫자로 이루어진 문자열을 생성합니다.
 * </p>
 *
 * <p>
 * 인스턴스 생성 후에는 {@code generate}로 코드를 생성합니다. 코드는 {@link Random}과 {@link StringBuilder}를 사용하며, 결과값들은
 * {@link HashSet}에 저장되어 중복을 방지하고 삭제, 추가, 검색 시간이 상수 시간으로 가능해집니다.
 * </p>
 * */

public final class CodeGenerator {

    private final Integer length;
    private final Integer amount;
    private final char[] chars;
    private final Integer space;
    private final CodeGeneratorConfigurer config;

    private CodeGenerator(CodeGeneratorConfigurer config) {
        this.amount = config.getAmount();
        this.length = config.getLength();
        this.chars = config.getCharset().toCharArray();
        this.space = config.getSpace();
        this.config = config;
    }

    public static CodeGenerator build() {
        return new CodeGenerator(CodeGeneratorConfigurer.builder().build());
    }

    public static CodeGenerator build(CodeGeneratorConfigurer config) {
        return new CodeGenerator(config);
    }

    public HashSet<String> generate() {
        HashSet<String> pool = new HashSet<>();

        for (int i = 0; i < amount; i++) {
            String code = createCode();
            pool.add(code);
        }

        return pool;
    }

    private String createCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= length; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
            if (space != null && space != 0) {
                if (i != length && i % space == 0) {
                    sb.append(" ");
                }
            }
        }

        return sb.toString();
    }

    public CodeGeneratorConfigurer getConfig() {
        return this.config;
    }

}
