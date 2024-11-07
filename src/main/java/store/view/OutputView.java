package store.view;

public class OutputView {

    private static final String HELLO_STATEMENT = "안녕하세요. W편의점입니다.";
    private static final String CURRENT_PRODUCTS_STATEMENT = "현재 보유하고 있는 상품입니다.";
    private static final String NEW_LINE = System.lineSeparator();

    public void printHello() {
        System.out.println(HELLO_STATEMENT);
    }

    public void printCurrentProducts() {
        System.out.println(CURRENT_PRODUCTS_STATEMENT + NEW_LINE);
        // 상품 목록 출력
    }
}
