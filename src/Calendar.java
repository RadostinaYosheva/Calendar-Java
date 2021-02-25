import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Calendar {

    public static
    String generateMonthString(int year, int month, int[] daysInMonth, String[] monthNames) {
        int startDay = getStartDay(year, month);

        String monthName = String.format("%28s\n", monthNames[month]);
        String dayNames = " Sun Mon Tue Wed Thu Fri Sat\n";
        StringBuilder days = new StringBuilder();

        days.append("    ".repeat(Math.max(0, startDay)));

        for (int i = 1; i < daysInMonth[month]; i++) {
            days.append(String.format("%4d", i));

            if ((startDay + i) % 7 == 0) {
                days.append("\n");
            }
        }

        return monthName + dayNames + days;
    }

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


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int year = scan.nextInt();

        int [] daysInMonth = {-1, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isLeapYear(year)) {
            daysInMonth[2]++;
        }

        String [] monthNames = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};

        String feb = generateMonthString(year, 2, daysInMonth, monthNames);
        System.out.println(feb);
    }


}
