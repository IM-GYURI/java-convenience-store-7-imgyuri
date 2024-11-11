package store.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

class ExceptionHandlerTest {
    @Test
    void 정상적인_입력을_처리한다() {
        String input = "유효한 입력";

        Supplier<String> supplier = () -> input;

        String result = ExceptionHandler.getValidInput(supplier);
        assertEquals(input, result);
    }

    @Test
    void 최대_횟수만큼_재시도_하고_예외를_발생시킨다() {
        String input = "유효하지 않은 입력";
        int attempts = 5;

        Supplier<String> supplier = () -> {
            throw new IllegalArgumentException(input);
        };

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ExceptionHandler.getValidInput(supplier);
        });

        assertEquals(ErrorMessage.TOO_MANY_INVALID_INPUT.message, exception.getMessage());

        String output = outputStream.toString();
        int errorMessagesCount = output.split(input).length - 1;
        assertEquals(attempts, errorMessagesCount);
    }
}