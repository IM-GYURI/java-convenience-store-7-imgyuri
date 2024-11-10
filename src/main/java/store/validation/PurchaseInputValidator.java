package store.validation;

import java.util.Set;
import store.domain.Product;
import store.domain.Products;
import store.dto.ParsedItemDto;
import store.exception.ErrorMessage;
import store.exception.ValidatorBuilder;

public class PurchaseInputValidator {

    private static final String PURCHASE_ITEM_REGEX = "^\\[[^\\[]+?-\\d+]\\s*(,\\s*\\[[^\\[]+?-\\d+])*$";
    private static final int QUANTITY_STANDARD = 1;

    public static void validateInputFormat(String input) {
        ValidatorBuilder.from(input)
                .validate(value -> value == null || value.isBlank(), ErrorMessage.INPUT_NOT_EMPTY)
                .validate(value -> !value.matches(PURCHASE_ITEM_REGEX), ErrorMessage.INVALID_PURCHASE_FORMAT);
    }

    public static void validateStockAndDuplication(ParsedItemDto parsedItemDto, Set<String> itemNames,
                                                   Products products) {
        Product product = findMatchProduct(parsedItemDto.name(), products);
        validateStock(product, parsedItemDto.quantity());
        validateDuplication(parsedItemDto.name(), itemNames);
    }

    public static int validateQuantity(String input) {
        return ValidatorBuilder.from(input)
                .validateIsInteger()
                .validateInteger(value -> value < QUANTITY_STANDARD, ErrorMessage.INVALID_QUANTITY)
                .getNumericValue();
    }

    private static Product findMatchProduct(String name, Products products) {
        return products.products().stream()
                .filter(product -> product.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.PRODUCT_NOT_EXISTS.name()));
    }

    private static void validateStock(Product product, int quantity) {
        int fullStock = product.stock().getFullStock();
        ValidatorBuilder.from(quantity)
                .validate(value -> value > fullStock, ErrorMessage.STOCK_SHORTAGE);
    }

    private static void validateDuplication(String name, Set<String> itemNames) {
        ValidatorBuilder.from(name)
                .validate(itemNames::contains, ErrorMessage.PURCHASE_ITEM_DUPLICATED);
    }
}
