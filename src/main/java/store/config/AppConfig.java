package store.config;

import store.controller.StoreController;
import store.service.StoreService;
import store.util.ProductLoader;
import store.util.PromotionLoader;
import store.view.OutputView;

public class AppConfig {

    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";

    private static final AppConfig appConfig = new AppConfig();

    public static AppConfig getInstance() {
        return appConfig;
    }

    public StoreController storeController() {
        return new StoreController(outputView(), storeService());
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

    private OutputView outputView() {
        return new OutputView();
    }
}
