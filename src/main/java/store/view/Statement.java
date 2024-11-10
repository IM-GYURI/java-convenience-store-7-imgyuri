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
    NO_PROMOTION_STATEMENT("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    MEMBERSHIP_STATEMENT("멤버십 할인을 받으시겠습니까? (Y/N)"),
    START_OF_RECEIPT("==============W 편의점================"),
    HEADER_OF_PURCHASE("상품명\t\t수량\t금액"),
    PURCHASE_FORMAT("%s\t\t%s \t%s"),
    HEADER_OF_GIVEN("=============증\t정==============="),
    GIVEN_FORMAT("%s\t\t%s"),
    DIVIDE_LINE("===================================="),
    TOTAL_PRICE_FORMAT("총구매액\t\t%s\t%s"),
    PROMOTION_DISCOUNT_FORMAT("행사할인\t\t\t%s"),
    MEMBERSHIP_DISCOUNT_FORMAT("멤버십할인\t\t\t%s"),
    PAY_PRICE_FORMAT("내실돈\t\t\t %s"),
    BLANK(""),
    NEW_LINE(System.lineSeparator());

    final String message;

    Statement(final String message) {
        this.message = message;
    }
}
