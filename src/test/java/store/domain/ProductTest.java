package store.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {

    private final int price = 1_000;
    private final int promotionQuantity1 = 10;
    private final int promotionQuantity2 = 0;
    private final int regularQuantity = 10;
    private final int buy = 2;
    private final int get = 1;

    private Promotion promotion;

    @BeforeEach
    void setUp() {
        promotion = new Promotion("프로모션1", buy, get, LocalDate.of(2024, 1, 1)
                , LocalDate.of(2024, 12, 31));
    }

    @Test
    void 총_가격을_계산한다() {
        int quantity = 3;
        Stock stock = new Stock(promotionQuantity2, regularQuantity);
        Product product = new Product("상품1", price, stock, null);

        int totalPrice = product.calculateTotalPrice(quantity);
        assertEquals(price * quantity, totalPrice);
    }

    @Test
    void 프로모션_재고를_업데이트한다() {
        int quantity = 3;
        Stock stock = new Stock(promotionQuantity1, regularQuantity);
        Product product = new Product("상품1", price, stock, promotion);

        product.updateStock(quantity);

        assertEquals(promotionQuantity1 - quantity, product.getPromotionStock());
        assertEquals(regularQuantity, product.getRegularStock());
    }

    @Test
    void 일반_재고를_업데이트한다() {
        int quantity = 3;
        Stock stock = new Stock(promotionQuantity2, regularQuantity);
        Product product = new Product("상품1", price, stock, null);

        product.updateStock(quantity);

        assertEquals(promotionQuantity2, product.getPromotionStock());
        assertEquals(regularQuantity - quantity, product.getRegularStock());
    }

    @Test
    void 프로모션_재고와_일반_재고를_업데이트한다() {
        int quantity = 12;
        Stock stock = new Stock(promotionQuantity1, regularQuantity);
        Product product = new Product("상품1", price, stock, promotion);

        product.updateStock(quantity);

        int minusFromPromotion = Math.min(promotionQuantity1, quantity);

        assertEquals(promotionQuantity1 - minusFromPromotion, product.getPromotionStock());
        assertEquals(regularQuantity - (quantity - minusFromPromotion), product.getRegularStock());
    }

    @Test
    void 프로모션_할인_금액을_계산한다() {
        Stock stock = new Stock(promotionQuantity1, regularQuantity);
        Product product = new Product("상품1", price, stock, promotion);

        int purchased = 4;
        int discount = product.calculatePromotionDiscount(purchased);

        assertEquals((purchased / (buy + get)) * price, discount);
    }

    @Test
    void 프로모션_기간이_지났을_경우_할인_금액을_계산한다() {
        promotion = new Promotion("프로모션1", 2, 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        Stock stock = new Stock(promotionQuantity1, regularQuantity);
        Product product = new Product("상품1", price, stock, promotion);

        int purchased = 4;
        int discount = product.calculatePromotionDiscount(purchased);

        assertEquals(0, discount);
    }

    @Test
    void 상품의_프로모션_재고를_조회한다() {
        Stock stock = new Stock(promotionQuantity1, regularQuantity);
        Product product = new Product("상품1", price, stock, promotion);

        assertEquals(promotionQuantity1, product.getPromotionStock());
    }

    @Test
    void 상품의_일반_재고를_조회한다() {
        Stock stock = new Stock(promotionQuantity1, regularQuantity);
        Product product = new Product("상품1", price, stock, promotion);

        assertEquals(regularQuantity, product.getRegularStock());
    }

    @Test
    void 상품의_전체_재고를_조회한다() {
        Stock stock = new Stock(promotionQuantity1, regularQuantity);
        Product product = new Product("상품1", price, stock, promotion);

        assertEquals(promotionQuantity1 + regularQuantity, product.getFullStock());
    }
}