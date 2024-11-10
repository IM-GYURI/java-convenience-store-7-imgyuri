package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Product;
import store.exception.ErrorMessage;
import store.exception.ValidatorBuilder;

public class InputView {

    public String requestProductSelect() {
        System.out.println(Statement.PRODUCT_SELECT_STATEMENT.message);
        return Console.readLine();
    }

    public String askAdditionalPurchase(Product product) {
        System.out.printf(Statement.ADDITIONAL_PURCHASE_FORMAT.message + Statement.NEW_LINE.message,
                product.getName(), product.getPromotion().getGet());
        String userInput = Console.readLine();

        validateInput(userInput);
        return userInput;
    }

    private void validateInput(String userInput) {
        ValidatorBuilder.from(userInput)
                .validate(input -> input == null || input.isBlank(), ErrorMessage.INPUT_NOT_EMPTY)
                .validate(input -> !input.equals(Statement.YES_ANSWER.message) && !input.equals(
                        Statement.NO_ANSWER.message), ErrorMessage.INVALID_YN_INPUT)
                .get();
    }
}
