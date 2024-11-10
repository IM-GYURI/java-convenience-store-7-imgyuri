package store.exception;

public enum ErrorMessage {
    INPUT_NOT_EMPTY("입력은 비어있을 수 없습니다."),
    INVALID_PURCHASE_FORMAT("잘못된 형식의 입력입니다. [상품명-수량],[상품명-수량] 형식으로 입력해주세요."),
    PURCHASE_ITEM_DUPLICATED("상품명이 중복되었습니다."),
    PRODUCT_NOT_EXISTS("해당 상품이 존재하지 않습니다."),
    INVALID_QUANTITY("수량은 1 이상이어야 합니다."),
    STOCK_SHORTAGE("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    PURCHASE_QUANTITY_NOT_INTEGER("구매 수량은 정수만 가능합니다."),
    INVALID_YN_INPUT("입력값은 'Y' 또는 'N'이어야 합니다.");

    private static final String PREFIX = "[ERROR] ";

    final String message;

    ErrorMessage(final String message) {
        this.message = PREFIX + message;
    }
}
