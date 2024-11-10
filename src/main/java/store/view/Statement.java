package store.view;

public enum Statement {

    PRODUCT_SELECT_STATEMENT("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    HELLO_STATEMENT("안녕하세요. W편의점입니다."),
    CURRENT_PRODUCTS_STATEMENT("현재 보유하고 있는 상품입니다."),
    NUMBER_FORMAT("#,###"),
    PRODUCT_FORMAT("- %s %s원 "),
    QUANTITY_FORMAT("%s개 "),
    OUT_OF_STOCK("재고 없음 "),
    ADDITIONAL_PURCHASE_FORMAT("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    YES_ANSWER("Y"),
    NO_ANSWER("N"),
    BLANK(""),
    NEW_LINE(System.lineSeparator());

    final String message;

    Statement(final String message) {
        this.message = message;
    }
}
