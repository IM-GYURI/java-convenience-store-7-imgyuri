package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(final String name, final int buy, final int get, final LocalDate startDate,
                     final LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isWithinPromotionPeriod() {
        LocalDate today = DateTimes.now().toLocalDate();
        return (today.isEqual(startDate) || today.isAfter(startDate))
                && (today.isEqual(endDate) || today.isBefore(endDate));
    }

    public int calculateAdditionalPurchase(int purchaseQuantity) {
        int remain = purchaseQuantity % (buy + get);

        if (remain != 0) {
            return (buy + get) - remain;
        }

        return remain;
    }

    public String getName() {
        return name;
    }

    public int getGet() {
        return get;
    }
}
