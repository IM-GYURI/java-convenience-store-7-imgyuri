package store.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockTest {

    private final int promotionQuantity = 10;
    private final int regularQuantity = 10;

    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock(promotionQuantity, regularQuantity);
    }

    @Test
    void 프로모션_재고를_조회한다() {
        assertEquals(stock.getPromotionStock(), promotionQuantity);
    }

    @Test
    void 일반_재고를_조회한다() {
        assertEquals(stock.getRegularStock(), regularQuantity);
    }

    @Test
    void 프로모션_재고를_감소시킨_후_확인한다() {
        int quantity = 3;

        stock.minusPromotionStock(quantity);
        assertEquals(stock.getPromotionStock(), promotionQuantity - quantity);
    }

    @Test
    void 일반_재고를_감소시킨_후_확인한다() {
        int quantity = 5;

        stock.minusRegularStock(quantity);
        assertEquals(stock.getRegularStock(), regularQuantity - quantity);
    }

    @Test
    void 프로모션_재고를_새로_세팅한다() {
        int newQuantity = 15;

        stock.setPromotionStock(newQuantity);
        assertEquals(stock.getPromotionStock(), newQuantity);
    }

    @Test
    void 일반_재고를_새로_세팅한다() {
        int newQuantity = 20;

        stock.setRegularStock(newQuantity);
        assertEquals(stock.getRegularStock(), newQuantity);
    }
}