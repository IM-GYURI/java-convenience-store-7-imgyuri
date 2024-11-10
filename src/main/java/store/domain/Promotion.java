package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.dto.PromotionDetailDto;

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
        int remain = purchaseQuantity % sumOfBuyAndGet();

        if (remain != 0) {
            return sumOfBuyAndGet() - remain;
        }

        return remain;
    }

    public PromotionDetailDto calculatePromotionAndFree(int requestedQuantity, int promotionStock) {
        int availablePromotion = (promotionStock / sumOfBuyAndGet()) * sumOfBuyAndGet();
        return new PromotionDetailDto(availablePromotion, requestedQuantity - availablePromotion);
    }

    public int calculateFreeQuantity(int requestedQuantity, int promotionStock) {
        if (requestedQuantity > promotionStock) {
            return promotionStock / sumOfBuyAndGet();
        }

        return requestedQuantity / sumOfBuyAndGet();
    }

    private int sumOfBuyAndGet() {
        return buy + get;
    }

    public String getName() {
        return name;
    }

    public int getGet() {
        return get;
    }
}
