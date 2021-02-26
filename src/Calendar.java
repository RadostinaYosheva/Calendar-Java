import java.util.Arrays;
import java.util.Scanner;


public class Calendar {

    public static void printCalendar(int year) {
        String [] monthNames = {"ERROR", "JANUARY", "FEBRUARY", "MARCH",
                                "APRIL", "MAY", "JUNE", "JULY", "AUGUST",
                                "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        int [] daysInMonth = {-1, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (isLeapYear(year)) {
            daysInMonth[2]++;
        }

        String[] january = splitString(generateMonthString(year,1, daysInMonth, monthNames));
        String[] february = splitString(generateMonthString(year,2, daysInMonth, monthNames));
        String[] march = splitString(generateMonthString(year, 3, daysInMonth, monthNames));
        String[] april = splitString(generateMonthString(year, 4, daysInMonth, monthNames));
        String[] may = splitString(generateMonthString(year, 5, daysInMonth, monthNames));
        String[] june = splitString(generateMonthString(year, 6, daysInMonth, monthNames));
        String[] july = splitString(generateMonthString(year, 7, daysInMonth, monthNames));
        String[] august = splitString(generateMonthString(year, 8, daysInMonth, monthNames));
        String[] september = splitString(generateMonthString(year, 9, daysInMonth, monthNames));
        String[] october = splitString(generateMonthString(year, 10, daysInMonth, monthNames));
        String[] november = splitString(generateMonthString(year, 11, daysInMonth, monthNames));
        String[] december = splitString(generateMonthString(year, 12, daysInMonth, monthNames));

        String [][] firstGroup = {january, february, march, april};
        String [][] secondGroup = {may, june, july, august};
        String [][] thirdGroup = {september, october, november, december};

        printGroup(firstGroup);
        printGroup(secondGroup);
        printGroup(thirdGroup);
    }

    private static void printGroup(String[][]group) {
        int maxSize = findMaxSize(group);
        resizeSubgroups(group, maxSize);

        String space = "    ";

        for (int i = 0; i < maxSize; i++) {
            for (String[] strings : group) {
                System.out.print(strings[i]);
                System.out.print(space);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void resizeSubgroups(String[][] group, int size) {
        for (int i = 0; i < group.length; i++) {
            // add the diff between maxSize and currSize
            int difference = size - group[i].length;
            String[] resizedMonth =  Arrays.copyOf(group[i], size);
            group[i] = resizedMonth;

            for (int j = difference; j > 0; j--) {
                group[i][size-j] = String.format("%28s", " ");
            }
        }
    }

    private static int findMaxSize(String[][] group) {
        int maxLength = group[0].length;

        for (int i = 1; i < group.length; i++) {
            if (group[i].length > maxLength) {
                maxLength = group[i].length;
            }
        }
        return maxLength;
    }

    private static
    String generateMonthString(int year, int month, int[] daysInMonth, String[] monthNames) {
        final int MaxLineSpace = 28;
        final int MaxDaySpace = 4;

        int startDay = getStartDay(year, month);
        int counter = 0;

        String monthName = String.format("%28s\n", monthNames[month]);
        String dayNames = " Sun Mon Tue Wed Thu Fri Sat\n";
        StringBuilder days = new StringBuilder();

        days.append("    ".repeat(Math.max(0, startDay)));

        for (int i = 1; i <= daysInMonth[month]; i++) {
            days.append(String.format("%4d", i));

            counter++;

            if ((startDay + i) % 7 == 0) {
                days.append("\n");
                counter = 0;
            }
        }

        days.append(" ".repeat(MaxLineSpace - counter * MaxDaySpace));

        return monthName + dayNames + days;
    }

    private static String[] splitString(String month) {

        return month.split("\n");
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
        System.out.print("Enter year: ");

        Scanner scan = new Scanner(System.in);
        int year = scan.nextInt();

        printCalendar(year);
    }


}
