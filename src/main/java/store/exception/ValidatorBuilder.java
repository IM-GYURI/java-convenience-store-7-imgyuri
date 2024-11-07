package store.exception;

import java.util.function.Predicate;

public class ValidatorBuilder<T> {

    private final T value;
    private int numericValue;

    private ValidatorBuilder(final T Value) {
        this.value = Value;
    }

    public static <T> ValidatorBuilder<T> from(final T Value) {
        return new ValidatorBuilder<T>(Value);
    }

    public ValidatorBuilder<T> validate(final Predicate<T> condition, final ErrorMessage errorMessage) {
        if (condition.test(value)) {
            throw new IllegalArgumentException(errorMessage.message);
        }

        return this;
    }

    public ValidatorBuilder<T> validateInteger(final Predicate<Integer> condition, final ErrorMessage errorMessage) {
        if (condition.test(numericValue)) {
            throw new IllegalArgumentException(errorMessage.message);
        }

        return this;
    }

    public ValidatorBuilder<T> validateIsInteger() {
        try {
            numericValue = Integer.parseInt(value.toString());
            return this;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.PURCHASE_QUANTITY_NOT_INTEGER.message);
        }
    }

    public T get() {
        return value;
    }

    public int getNumericValue() {
        return numericValue;
    }
}
