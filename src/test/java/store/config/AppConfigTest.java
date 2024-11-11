package store.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import store.controller.StoreController;

class AppConfigTest {

    @Test
    void 컨트롤러가_제대로_생성된다() {
        StoreController storeController = AppConfig.INSTANCE.storeController();
        assertTrue(storeController instanceof StoreController);
    }

    @Test
    void 동일한_인스턴스를_얻는다() {
        AppConfig config1 = AppConfig.INSTANCE;
        AppConfig config2 = AppConfig.INSTANCE;

        assertEquals(config1, config2);
    }
}