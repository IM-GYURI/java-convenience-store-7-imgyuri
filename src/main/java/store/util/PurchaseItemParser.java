package store.util;

import java.util.Arrays;
import java.util.List;
import store.dto.ParsedItem;
import store.validation.PurchaseInputValidator;

public class PurchaseItemParser {

    private static final String ITEM_SPLITTER = ",";
    private static final String DETAIL_SPLITTER = "-";
    private static final String LEFT_BRACKET = "[";
    private static final String RIGHT_BRACKET = "]";
    private static final String REPLACE_BLANK = "";
    private static final int NAME_INDEX = 0;
    private static final int QUANTITY_INDEX = 1;

    public static List<String> splitItems(String input) {
        return Arrays.stream(input.split(ITEM_SPLITTER))
                .toList();
    }

    public static ParsedItem parseItem(String part) {
        String[] itemDetails = part.split(DETAIL_SPLITTER);
        String name = parseEachInput(itemDetails[NAME_INDEX]);
        int quantity = PurchaseInputValidator.validateQuantity(parseEachInput(itemDetails[QUANTITY_INDEX]));

        return new ParsedItem(name, quantity);
    }

    private static String parseEachInput(String splitPart) {
        return splitPart.replace(LEFT_BRACKET, REPLACE_BLANK)
                .replace(RIGHT_BRACKET, REPLACE_BLANK)
                .trim();
    }
}
