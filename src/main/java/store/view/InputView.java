package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Product;
import store.exception.ErrorMessage;
import store.exception.ValidatorBuilder;

public class InputView {

    private static final String YES_ANSWER = "Y";
    private static final String NO_ANSWER = "N";

    public String requestProductSelect() {
        System.out.println(Sentence.PRODUCT_SELECT_STATEMENT.message);
        return Console.readLine();
    }

    public String askAdditionalPurchase(Product product) {
        System.out.printf(
                Sentence.NEW_LINE.message + Sentence.ADDITIONAL_PURCHASE_FORMAT.message + Sentence.NEW_LINE.message,
                product.name(), product.promotion().getGet());
        String userInput = Console.readLine();

        validateInput(userInput);
        return userInput;
    }

    public String askNoPromotion(Product product, int shortageQuantity) {
        System.out.printf(Sentence.NEW_LINE.message + Sentence.NO_PROMOTION_STATEMENT.message
                + Sentence.NEW_LINE.message, product.name(), shortageQuantity);
        String userInput = Console.readLine();

        validateInput(userInput);
        return userInput;
    }

    public String askMembership() {
        System.out.println(Sentence.NEW_LINE.message + Sentence.MEMBERSHIP_STATEMENT.message);
        String userInput = Console.readLine();

        validateInput(userInput);
        return userInput;
    }

    public String askPurchaseAgain() {
        System.out.println(Sentence.NEW_LINE.message + Sentence.PURCHASE_AGAIN_STATEMENT.message);
        String userInput = Console.readLine();

        validateInput(userInput);
        return userInput;
    }

    private void validateInput(String userInput) {
        ValidatorBuilder.from(userInput)
                .validate(input -> input == null || input.isBlank(), ErrorMessage.INPUT_NOT_EMPTY)
                .validate(input -> !input.equals(YES_ANSWER) && !input.equals(
                        NO_ANSWER), ErrorMessage.INVALID_YN_INPUT)
                .get();
    }
}
