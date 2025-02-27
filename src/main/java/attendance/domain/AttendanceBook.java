package attendance.domain;

import static attendance.constant.ErrorMessage.UNREGISTERED_NICKNAME;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, Attendances> crewAttendances;

    public AttendanceBook(final Map<String, Attendances> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public void attend(final String nickname, final Attendance attendance) {
        validateNickname(nickname);
        Attendances attendances = crewAttendances.get(nickname);
        attendances.add(attendance);
        crewAttendances.put(nickname, attendances);
    }

    public Attendance updateAttendance(String nickname, LocalDateTime dateTime) {
        Attendances attendances = crewAttendances.get(nickname);
        Attendance before = attendances.updateAttendance(dateTime);
        crewAttendances.put(nickname, attendances);
        return before;
    }

    public Attendances findByNickname(String nickname) {
        validateNickname(nickname);
        return crewAttendances.get(nickname);
    }

    public Attendance findByNicknameAndDate(String nickname, LocalDateTime dateTime) {
        Attendances attendances = crewAttendances.get(nickname);
        return attendances.findByDate(dateTime.toLocalDate());
    }

    public Map<String, Attendances> findWarningCrews() {
        Map<String, Attendances> result = new HashMap<>();
        for (String nickname : crewAttendances.keySet()) {
            Attendances attendances = crewAttendances.get(nickname);
            if (!attendances.calculateWarning().equals(Warning.NONE)) {
                result.put(nickname, attendances);
            }
        }
        return result;
    }

    private void validateNickname(final String nickname) {
        if (!crewAttendances.containsKey(nickname)) {
            throw new IllegalArgumentException(UNREGISTERED_NICKNAME.getMessage());
        }
    }
}
