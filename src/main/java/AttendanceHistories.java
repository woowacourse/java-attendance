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

    public void update(AttendanceHistory oldAttendanceHistory, AttendanceHistory newAttendanceHistory) {
        int indexOfTarget = attendanceHistories.indexOf(oldAttendanceHistory);
        attendanceHistories.set(indexOfTarget, newAttendanceHistory);
    }

    public AttendanceHistory findByCrewAndDate(Crew crew, LocalDate date) {
        return attendanceHistories.stream()
                .filter(attendanceHistory -> attendanceHistory.isAboutSameCrew(crew)
                        && attendanceHistory.isAboutSameDate(date))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("조건에 해당하는 기록이 존재하지 않습니다."));
    }

    public List<AttendanceHistory> findAllHistoriesOfCrewDateBefore(Crew crew, LocalDate requestedDate) {
        return attendanceHistories.stream()
                .filter(attendanceHistory -> attendanceHistory.isAboutSameCrew(crew) &&
                        attendanceHistory.isBefore(requestedDate))
                .toList();
    }
}
