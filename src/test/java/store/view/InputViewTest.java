package store.view;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class InputViewTest {

    private final InputView inputView = new InputView();

    @Test
    void 공백을_입력할_시_예외를_발생시킨다() {
        String userInput = "   ";

        assertThatThrownBy(() -> inputView.validateInput(userInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Y나_N이_아닌_입력을_할_경우_예외를_발생시킨다() {
        String userInput = "A";

        assertThatThrownBy(() -> inputView.validateInput(userInput))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Y를_입력할_경우_정상_처리한다() {
        String userInput = "Y";

        assertDoesNotThrow(() -> inputView.validateInput(userInput));
    }

    @Test
    void N을_입력할_경우_정상_처리한다() {
        String userInput = "N";

        assertDoesNotThrow(() -> inputView.validateInput(userInput));
    }
}