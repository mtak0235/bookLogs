package enumeration.ref3;

import static enumeration.ref3.Grade.*;

public class StringGradeEx01 {
    public static void main(String[] args) {
        int price = 10_000;
        DiscountService discountService = new DiscountService();

        int basic = discountService.discount(BASIC, price);
        int gold = discountService.discount(GOLD, price);
        int diamond = discountService.discount(DIAMOND, price);

        System.out.println("basic = " + basic);
        System.out.println("diamond = " + diamond);
        System.out.println("gold = " + gold);

    }
}
