package store.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductsTest {

    private final int promotionQuantity = 10;
    private final int regularQuantity = 10;
    private final int price = 1_000;

    private Products products;
    private Product product1;
    private Product product2;
    private Stock stock;

    @BeforeEach
    public void setUp() {
        stock = new Stock(promotionQuantity, regularQuantity);
        product1 = new Product("상품1", price, stock, null);
        product2 = new Product("상품2", price, stock, null);

        products = new Products(List.of(product1, product2));
    }

    @Test
    void 상품명을_통해_상품을_찾는다() {
        Product foundProduct = products.findProductByName("상품1");

        assertEquals(product1, foundProduct);
    }

    @Test
    void 존재하지_않는_상품명으로_상품을_찾을_경우_null을_반환한다() {
        Product foundProduct = products.findProductByName("상품100");

        assertNull(foundProduct);
    }

    @Test
    void 상품을_추가한다() {
        int productCount = 2;
        Product newProduct = new Product("상품3", price, stock, null);
        products.addProduct(newProduct);

        assertEquals(productCount + 1, products.getProducts().size());
    }
}