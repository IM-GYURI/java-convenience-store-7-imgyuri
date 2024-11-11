package store;

import store.config.AppConfig;
import store.controller.StoreController;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        try {
            StoreController storeController = AppConfig.INSTANCE.storeController();
            storeController.enterStore();
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
        }
    }
}
