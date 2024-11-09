package store.exception;

import java.util.function.Supplier;
import store.view.OutputView;

public class ExceptionHandler {

    public static <T> T getValidInput(final Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}