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

    private final static String SPLITTER = ",";

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

        String name = parts[0];
        int buy = parseNumber(parts[1]);
        int get = parseNumber(parts[2]);
        LocalDate startDate = parseDate(parts[3]);
        LocalDate endDate = parseDate(parts[4]);

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
