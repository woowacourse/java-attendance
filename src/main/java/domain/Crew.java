package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Crew {
    private final String userName;
    private final AttendanceHistories attendanceHistories;

    public Crew(String userName, List<LocalDateTime> histories, LocalDate standard) {
        validateName(userName);
        this.userName = userName;
        this.attendanceHistories = new AttendanceHistories(histories, standard);
    }

    public void validateName(String userName) {
        if (userName.length() >= 5) {
            throw new IllegalArgumentException("[ERROR ]이름은 네 글자 이하여야 합니다. 네 글자 이하로 입력해 주세요.");
        }
    }

    public void addAttendance(LocalDateTime history) {
        attendanceHistories.addHistory(history);
    }

    public List<AttendanceHistory> getBeforeHistories(LocalDateTime standard) {
        return attendanceHistories.getSortedHistories(standard);
    }

    public String getHistoryResult(LocalDateTime attendanceTime) {
        return attendanceHistories.getHistoryResult(attendanceTime);
    }

    public String getUserName() {
        return userName;
    }

    public void editHistory(LocalDateTime attendanceTime) {
        attendanceHistories.editHistory(attendanceTime);
    }

    public LocalDateTime getHistoryDate(LocalDateTime time) {
        return attendanceHistories.getHistory(time);
    }

    public AbsenceLevel getClassifyAbsenceLevel(LocalDateTime time) {
        return attendanceHistories.classifyAbsenceLevel(time);
    }

    public Map<AttendanceResult, Integer> getAttendanceAllResult(LocalDateTime time) {
        return attendanceHistories.getAttendanceResultCount(time);
    }
}
