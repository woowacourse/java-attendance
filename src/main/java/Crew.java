public class Crew {

    private final String name;
    private final DailyAttendance dailyAttendance;
    private final Penalty penalty;

    public Crew(String name, DailyAttendance dailyAttendance, Penalty penalty) {
        this.name = name;
        this.dailyAttendance = dailyAttendance;
        this.penalty = penalty;
    }
}
