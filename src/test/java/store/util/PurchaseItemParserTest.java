package store.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.dto.ParsedItemDto;

class PurchaseItemParserTest {

    @Test
    void 정상적인_입력을_처리한다() {
        String input = "[상품1-1],[상품2-2],[상품3-3]";
        List<String> items = PurchaseItemParser.splitItems(input);

        assertEquals(3, items.size());
        assertEquals("[상품1-1]", items.get(0));
        assertEquals("[상품2-2]", items.get(1));
        assertEquals("[상품3-3]", items.get(2));
    }

    @Test
    void 각_항목을_정상적으로_파싱한다() {
        String part = "[  상품1 - 1]";
        ParsedItemDto parsedItem = PurchaseItemParser.parseItem(part);

        assertEquals("상품1", parsedItem.name());
        assertEquals(1, parsedItem.quantity());
    }

    @Test
    void 수량이_1_미만일_경우_예외를_발생시킨다() {
        String input = "[상품1-0]";

        assertThatThrownBy(() -> PurchaseItemParser.parseItem(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 수량이_숫자가_아닐_경우_예외를_발생시킨다() {
        String input = "[상품1-a]";

        assertThatThrownBy(() -> PurchaseItemParser.parseItem(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}