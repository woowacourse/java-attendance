import java.time.LocalDateTime;
import java.time.LocalTime;

public class Crew {
    private final String nickname;
    private final AttendTimes attendTimes;

    public Crew(String nickname) {
        this.nickname = nickname;
        this.attendTimes = new AttendTimes();
    }

    public Crew(String nickname, LocalDateTime localDateTime) {
        this.nickname = nickname;
        this.attendTimes = new AttendTimes();
        attendTimes.add(new AttendTime(localDateTime.toLocalDate(),localDateTime.toLocalTime()));
    }

    public AttendTime attend(LocalDateTime localDateTime) {
        AttendTime attendTime =new AttendTime(localDateTime.toLocalDate(),localDateTime.toLocalTime());
        attendTimes.add(attendTime);
        return attendTime;
    }

    public AttendTime changeAttendanceTime(int date, LocalTime localTime) {
        attendTimes.removeAttendance(date);

        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, date, localTime.getHour(), localTime.getMinute());
        return attend(localDateTime);
    }

    public AttendTime findAttendanceByDate(int dayOfMonth) {
        return attendTimes.findAttendanceByDate(dayOfMonth).orElseThrow(()->new IllegalArgumentException("없는 이름입니다."));
    }

    public boolean isDismissalCrew() {
        return DangerousTarget.getWarningStatus(attendTimes.calculateLateCount(),attendTimes.calculateAbsentCount())!=DangerousTarget.SAFE;
    }
    public String getNickname() {
        return nickname;
    }

    public AttendTimes getAttendTimes() {
        return attendTimes;
    }

}
