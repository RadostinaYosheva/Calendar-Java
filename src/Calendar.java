import java.util.Scanner;

public class Calendar {

    private static int getStartDay(int year, int month) {
        int centuryValue = getCenturyValue(year);
        int monthValue = getMonthValue(month);
        int lastDigits = year % 100;
        int correction;

        if ((month == 1 || month == 2) && isLeapYear(year)) {
            correction = -1;
        } else {
            correction = 0;
        }

        /*
        * Formula:
        * Take the last 2 digits of the year.
        * Divide it by 4 and discard any remainder.
        * Add the day of the month.
        * Add the month’s key value.
        * If the date is in January or February of a leap year, subtract 1.
        * Add the year (century) value.
        * Add the last two digits of the year.
        * Divide this value by 7 and take the remainder.
        */

        int key = (lastDigits/4 + 1 + monthValue + correction + centuryValue + lastDigits) % 7;

        /* By the value of the remainder Saturday is the first day. Correct by subtracting 1.*/
        key -= 1;
        if (key == -1) {
            return 6;
        }

        return key;
    }

    private static int getMonthValue(int month) {
        int[] values = {-1, 1, 4, 4, 0, 2, 5, 0, 3, 6, 1, 4, 6};
        return values[month];
    }

    private static int getCenturyValue(int year) {
        while (year / 100 > 20) {
            year -= 400;
        }

        while (year / 100 < 17) {
            year += 400;
        }

        int century = year / 100;

        switch (century) {
            case 17:
                return 4;

            case 18:
                return 2;

            case 19:
                return 0;

            case 20:
                return 6;

            default:
                throw new IllegalArgumentException();

        }
    }

    private static boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 != 0) {
                return true;
            }
            return year % 400 == 0;
        }
        return false;
    }


}
