package store.util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.PurchaseItem;
import store.domain.Stock;

class PurchaseItemProcessorTest {

    private PurchaseItemProcessor processor;
    private Stock stock;
    private Products products;

    @BeforeEach
    void setUp() {
        processor = new PurchaseItemProcessor();
        stock = new Stock(0, 10);
        products = new Products(List.of(
                new Product("상품1", 1000, stock, null),
                new Product("상품2", 1000, stock, null),
                new Product("상품3", 1500, stock, null)
        ));
    }

    @Test
    void 정상적인_입력을_처리한다() {
        String input = "[상품1-1],[상품2-2],[상품3-3]";
        List<PurchaseItem> purchaseItems = processor.parsePurchaseItems(input, products);

        assertEquals(3, purchaseItems.size());
        assertEquals("상품1", purchaseItems.get(0).getProduct().name());
        assertEquals(1, purchaseItems.get(0).getQuantity());
        assertEquals("상품2", purchaseItems.get(1).getProduct().name());
        assertEquals(2, purchaseItems.get(1).getQuantity());
        assertEquals("상품3", purchaseItems.get(2).getProduct().name());
        assertEquals(3, purchaseItems.get(2).getQuantity());
    }

    @Test
    void 입력이_null일_경우_예외를_발생시킨다() {
        String input = null;

        assertThatThrownBy(() -> processor.parsePurchaseItems(input, products))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 입력이_공백일_경우_예외를_발생시킨다() {
        String input = " ";

        assertThatThrownBy(() -> processor.parsePurchaseItems(input, products))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 입력이_형식에_맞지_않을_경우_예외를_발생시킨다() {
        String input = "상품1-1";

        assertThatThrownBy(() -> processor.parsePurchaseItems(input, products))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품이_존재하지_않을_경우_예외를_발생시킨다() {
        String input = "[상품1-1],[상품100-100]";

        assertThatThrownBy(() -> processor.parsePurchaseItems(input, products))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품의_재고가_부족할_경우_예외를_발생시킨다() {
        String input = "[상품1-100]";

        assertThatThrownBy(() -> processor.parsePurchaseItems(input, products))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 입력이_중복될_경우_예외를_발생시킨다() {
        String input = "[상품-1],[상품-1]";

        assertThatThrownBy(() -> processor.parsePurchaseItems(input, products))
                .isInstanceOf(IllegalArgumentException.class);
    }
}