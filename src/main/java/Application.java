public class Application {
    public static void main(String[] args) {
    }

    public static boolean lateCheck(int dayOfWeek, int hour, int minute) {
        if (dayOfWeek == 1) {
            return hour > 13 || (hour == 13 && minute > 5);
        }

        return hour > 10 || (hour == 10 && minute > 5);
    }
}
