package code;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CodeGeneratorTest {

    @Test
    void givenNoConfig_then20ItemCodeCreated() {

        //given
        final CodeGenerator codeGenerator = CodeGenerator.build();
        HashSet<String> pool = codeGenerator.generate();

        //then
        assertEquals(20, pool.size());

    }

    @Test
    void givenLengthConfigured_thenItemCodeCreatedAsDefault() {

        //given
        Integer length = 12;
        CodeGeneratorConfigurer codeGeneratorConfigurer = CodeGeneratorConfigurer.builder().length(length).build();
        CodeGenerator codeGenerator = CodeGenerator.build(codeGeneratorConfigurer);
        Integer space = codeGenerator.getConfig().getSpace();
        HashSet<String> pool = codeGenerator.generate();

        //then
        for (String s : pool) {
            assertEquals(length + space, s.length());
        }

    }

    @Test
    void givenCharsetConfigured_thenItemCodeCreatedAsExpected() {

        //given
        String charset = CodeGeneratorConfigurer.Charset.ALPHABET_UPPERCASE;
        CodeGeneratorConfigurer codeGeneratorConfigurer = CodeGeneratorConfigurer.builder().charset(charset).build();
        CodeGenerator codeGenerator = CodeGenerator.build(codeGeneratorConfigurer);
        HashSet<String> pool = codeGenerator.generate();
        String regex = "[A-Z]+";

        //then
        for (String s : pool) {
            s = s.replaceAll(" ", "");
            assertTrue(s.matches(regex));
        }

    }

    @Test
    void givenAmountConfigured_thenItemCodeCreatedAsExpected() {

        //given
        Integer amount = 10;
        CodeGeneratorConfigurer codeGeneratorConfigurer = CodeGeneratorConfigurer.builder().amount(amount).build();
        CodeGenerator codeGenerator = CodeGenerator.build(codeGeneratorConfigurer);
        HashSet<String> pool = codeGenerator.generate();

        //then
        assertEquals(amount, pool.size());

    }

}