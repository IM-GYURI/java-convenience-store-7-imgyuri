package store.view;

import store.domain.Product;
import store.domain.Products;
import store.dto.ReceiptDto;

public class View {

    private final InputView inputView;
    private final OutputView outputView;

    public View(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public String requestProductSelect() {
        return inputView.requestProductSelect();
    }

    public String askAdditionalPurchase(Product product) {
        return inputView.askAdditionalPurchase(product);
    }

    public String askNoPromotion(Product product, int shortageQuantity) {
        return inputView.askNoPromotion(product, shortageQuantity);
    }

    public String askMembership() {
        return inputView.askMembership();
    }

    public void printHello() {
        outputView.printHello();
    }

    public void printCurrentProducts(Products products) {
        outputView.printCurrentProducts(products);
    }

    public void printReceipt(ReceiptDto receiptDto) {
        outputView.printReceipt(receiptDto);
    }
}
