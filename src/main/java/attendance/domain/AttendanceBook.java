package attendance.domain;

import static attendance.constant.ErrorMessage.UNREGISTERED_NICKNAME;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final Map<String, Attendances> crewAttendances;

    public AttendanceBook(final Map<String, Attendances> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public void attend(final String nickname, final Attendance attendance) {
        validateNickname(nickname);
        findByNickname(nickname).addIfAbsent(attendance);
    }

    public Attendance updateAttendance(final String nickname, final LocalDateTime dateTime) {
        return findByNickname(nickname).updateAttendance(dateTime);
    }

    public Attendances findByNickname(final String nickname) {
        validateNickname(nickname);
        return crewAttendances.get(nickname);
    }

    public Attendance findByNicknameAndDate(final String nickname, final LocalDateTime dateTime) {
        return findByNickname(nickname).findByDate(dateTime.toLocalDate());
    }

    public Map<String, Attendances> findPenaltyCrews() {
        return crewAttendances.entrySet()
                .stream()
                .filter(this::hasPenalty)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private boolean hasPenalty(Entry<String, Attendances> entry) {
        return !AttendancePenalty.NONE.equals(entry.getValue().calculatePenalty());
    }

    private void validateNickname(final String nickname) {
        if (crewAttendances.containsKey(nickname)) {
            return;
        }
        throw new IllegalArgumentException(UNREGISTERED_NICKNAME.getMessage());
    }
}
