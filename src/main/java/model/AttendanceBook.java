package model;

import dto.DismissalCrewDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import model.policy.DismissalCrewSortPolicy;

public class AttendanceBook {

    private final Map<String, AttendanceHistory> attendanceBook;

    public AttendanceBook(final Map<String, AttendanceHistory> attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public AttendanceHistory getAttendanceHistoryByNickname(final String nickname) {
        if (!attendanceBook.containsKey(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        return attendanceBook.get(nickname);
    }

    public List<DismissalCrewDto> getAllDismissalCrewByOrder(final LocalDate todayDate) {
        return attendanceBook.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(),
                        AttendanceTypeCountResult.from(
                                entry.getValue().getHistoryWithAttendanceType(todayDate)).typeResult()))
                .map(entry -> new DismissalCrewDto(
                        entry.getKey(), entry.getValue(), DismissalType.from(entry.getValue())))
                .sorted(DismissalCrewSortPolicy.getComparator())
                .toList();
    }
}
