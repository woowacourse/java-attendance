package attendance.domain;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import attendance.exception.AttendanceArgumentException;

public record AttendanceBook(Map<String, Attendances> attendances) {
    private static final String NOT_REGISTERED_NICKNAME = "등록되지 않은 닉네임입니다.";

    public Optional<Attendance> findAttendance(String nickname, LocalDate date) {
        var attendances = getAttendances(nickname);
        return attendances.findAttendance(date);
    }

    public Attendances getAttendances(String nickname) {
        if (!attendances.containsKey(nickname)) {
            throw new AttendanceArgumentException(NOT_REGISTERED_NICKNAME);
        }
        return attendances.get(nickname);
    }

    public Set<String> getNicknameSet() {
        return attendances.keySet();
    }

}
