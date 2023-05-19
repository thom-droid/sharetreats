package code;

/** DB 단에 저장되어 있는 설정을 다른 레이어에서도 가져올 수 있도록 작성된 인터페이스입니다. */

public interface CodeGeneratorConfigGetter {

    CodeGeneratorConfigure getItemCodeConfig();

    CodeGeneratorConfigure getShopCodeConfig();

}
