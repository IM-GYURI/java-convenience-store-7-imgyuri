package store.validation;

import store.exception.ErrorMessage;
import store.exception.ValidatorBuilder;

public class CommonInputValidator {

    private static final String YES_ANSWER = "Y";
    private static final String NO_ANSWER = "N";

    public static void validateInput(String userInput) {
        ValidatorBuilder.from(userInput)
                .validate(input -> input == null || input.isBlank(), ErrorMessage.INPUT_NOT_EMPTY)
                .validate(input -> !input.equals(YES_ANSWER) && !input.equals(
                        NO_ANSWER), ErrorMessage.INVALID_YN_INPUT)
                .get();
    }
}
