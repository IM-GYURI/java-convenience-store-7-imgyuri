package store.view;

public enum Statement {

    PRODUCT_SELECT_STATEMENT("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    HELLO_STATEMENT("안녕하세요. W편의점입니다."),
    CURRENT_PRODUCTS_STATEMENT("현재 보유하고 있는 상품입니다."),
    NUMBER_FORMAT("#,###"),
    PRODUCT_FORMAT("- %s %s원 "),
    QUANTITY_FORMAT("%s개 "),
    OUT_OF_STOCK("재고 없음"),
    BLANK(""),
    NEW_LINE(System.lineSeparator());

    final String message;

    Statement(final String message) {
        this.message = message;
    }
}
