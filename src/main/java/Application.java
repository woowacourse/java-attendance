import java.util.List;

public class Application {
    public static void main(String[] args) {
    }

    public static boolean lateCheck(int dayOfWeek, int hour, int minute) {
        if (dayOfWeek == 1) {
            return hour > 13 || (hour == 13 && minute > 5);
        }

        return hour > 10 || (hour == 10 && minute > 5);
    }

    public static boolean absenceCheck(int dayOfWeek, int hour, int minute) {
        if (dayOfWeek == 1) {
            return hour > 13 || (hour == 13 && minute > 30);
        }
        return hour > 10 || (hour == 10 && minute > 30);
    }

    public static boolean openTimeCheck(int hour, int minute) {
        if (hour == 23 && minute > 0) {
            return false;
        }
        return hour >= 8 && hour <= 23;
    }

    public static boolean restDayCheck(int day) {
        List<Integer> restDays = List.of(1, 7, 8, 14, 15, 21, 22, 25, 28, 29);
        return restDays.contains(day);
    }
}

