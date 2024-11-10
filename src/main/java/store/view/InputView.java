package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Product;
import store.validation.CommonInputValidator;

public class InputView {

    public String requestProductSelect() {
        System.out.println(Statement.PRODUCT_SELECT_STATEMENT.message);
        return Console.readLine();
    }

    public String askAdditionalPurchase(Product product) {
        System.out.printf(
                Statement.NEW_LINE.message + Statement.ADDITIONAL_PURCHASE_FORMAT.message + Statement.NEW_LINE.message,
                product.getName(), product.getPromotion().getGet());
        String userInput = Console.readLine();

        CommonInputValidator.validateInput(userInput);
        return userInput;
    }

    public String askNoPromotion(Product product, int shortageQuantity) {
        System.out.printf(Statement.NEW_LINE.message + Statement.NO_PROMOTION_STATEMENT.message
                + Statement.NEW_LINE.message, product.getName(), shortageQuantity);
        String userInput = Console.readLine();

        CommonInputValidator.validateInput(userInput);
        return userInput;
    }

    public String askMembership() {
        System.out.println(Statement.NEW_LINE.message + Statement.MEMBERSHIP_STATEMENT.message);
        String userInput = Console.readLine();

        CommonInputValidator.validateInput(userInput);
        return userInput;
    }
}
