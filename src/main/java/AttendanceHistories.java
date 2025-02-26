import java.time.LocalDate;
import java.util.List;

public class AttendanceHistories {
    private final List<AttendanceHistory> attendanceHistories;

    // TODO: 파라미터로 받는 것이 나을지 고민
    public AttendanceHistories(List<AttendanceHistory> attendanceHistories) {
        this.attendanceHistories = attendanceHistories;
    }

    public boolean checkExistenceByCrewAndDate(Crew crew, LocalDate requestedDate) {
        return attendanceHistories.stream()
                .anyMatch(attendanceHistory -> attendanceHistory.isAboutSameCrew(crew)
                        && attendanceHistory.isAboutSameDate(requestedDate));
    }

    public void addNewHistory(AttendanceHistory attendanceHistory) {
        attendanceHistories.add(attendanceHistory);
    }
}
