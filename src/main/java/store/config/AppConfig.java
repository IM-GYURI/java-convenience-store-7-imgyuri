package store.config;

import store.controller.StoreController;
import store.service.StoreService;
import store.util.ProductLoader;
import store.util.PromotionLoader;
import store.view.InputView;
import store.view.OutputView;
import store.view.View;

public enum AppConfig {

    INSTANCE;

    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";

    public StoreController storeController() {
        return new StoreController(view(), storeService());
    }

    private StoreService storeService() {
        return new StoreService(productLoader());
    }

    private ProductLoader productLoader() {
        return new ProductLoader(promotionLoader()
                .loadPromotions(PROMOTION_FILE_PATH));
    }

    private PromotionLoader promotionLoader() {
        return new PromotionLoader();
    }

    private View view() {
        return new View(inputView(), outputView());
    }

    private InputView inputView() {
        return new InputView();
    }

    private OutputView outputView() {
        return new OutputView();
    }
}
