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
        CodeGeneratorConfigure codeGeneratorConfigure = CodeGeneratorConfigure.builder().length(length).build();
        CodeGenerator codeGenerator = CodeGenerator.build(codeGeneratorConfigure);
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
        String charset = CodeGeneratorConfigure.Charset.ALPHABET_UPPERCASE;
        CodeGeneratorConfigure codeGeneratorConfigure = CodeGeneratorConfigure.builder().charset(charset).build();
        CodeGenerator codeGenerator = CodeGenerator.build(codeGeneratorConfigure);
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
        CodeGeneratorConfigure codeGeneratorConfigure = CodeGeneratorConfigure.builder().amount(amount).build();
        CodeGenerator codeGenerator = CodeGenerator.build(codeGeneratorConfigure);
        HashSet<String> pool = codeGenerator.generate();

        //then
        assertEquals(amount, pool.size());

    }

}