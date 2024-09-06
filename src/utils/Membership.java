package utils;

public enum Membership {
    NONE(0),
    SILVER(5),
    GOLD(10),
    PLATINUM(15);

    private final int discountPercentage;

    Membership(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public static Membership determineMembership(double totalSpending) {
        if (totalSpending >= 250_000_000) {
            return PLATINUM;
        } else if (totalSpending >= 100_000_000) {
            return GOLD;
        } else if (totalSpending >= 30_000_000) {
            return SILVER;
        } else {
            return NONE;
        }
    }
}
