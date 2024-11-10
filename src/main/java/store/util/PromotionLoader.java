package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import store.domain.Promotion;

public class PromotionLoader {

    private static final String SPLITTER = ",";
    private static final int NAME_INDEX = 0;
    private static final int BUY_INDEX = 1;
    private static final int GET_INDEX = 2;
    private static final int START_DATE_INDEX = 3;
    private static final int END_DATE_INDEX = 4;

    public Map<String, Promotion> loadPromotions(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            return processLines(br);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Promotion> processLines(BufferedReader br) throws IOException {
        Map<String, Promotion> promotions = new HashMap<>();
        String line = br.readLine();

        while (line != null) {
            Promotion promotion = createPromotion(line);
            promotions.put(promotion.getName(), promotion);
            line = br.readLine();
        }

        return promotions;
    }

    private Promotion createPromotion(String line) {
        String[] parts = line.split(SPLITTER);

        String name = parts[NAME_INDEX];
        int buy = parseNumber(parts[BUY_INDEX]);
        int get = parseNumber(parts[GET_INDEX]);
        LocalDate startDate = parseDate(parts[START_DATE_INDEX]);
        LocalDate endDate = parseDate(parts[END_DATE_INDEX]);

        return new Promotion(name, buy, get, startDate, endDate);
    }

    private int parseNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw e;
        }
    }
}
