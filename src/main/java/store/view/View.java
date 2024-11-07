package store.view;

import store.domain.Products;

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

    public void printHello() {
        outputView.printHello();
    }

    public void printCurrentProducts(Products products) {
        outputView.printCurrentProducts(products);
    }


}
