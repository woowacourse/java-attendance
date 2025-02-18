public class DailyAttendance {
    private final String date;
    private final String time;

    public DailyAttendance(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public boolean hasDate(String date) {
        return this.date.equals(date);
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
