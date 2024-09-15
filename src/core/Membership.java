package core;

import java.math.BigDecimal;

public enum Membership {
    NONE(0),
    SILVER(5),
    GOLD(10),
    PLATINUM(15);

    private final int discountPercentage;

    // Constructor
    Membership(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    // Determine membership based on total spending
    public static Membership determineMembership(BigDecimal totalSpending) {
        if (totalSpending.compareTo(new BigDecimal(250000000)) >= 0) {
            return PLATINUM;
        } else if (totalSpending.compareTo(new BigDecimal(100000000)) >= 0) {
            return GOLD;
        } else if (totalSpending.compareTo(new BigDecimal(30000000)) >= 0) {
            return SILVER;
        } else {
            return NONE;
        }
    }
}
