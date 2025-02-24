package attendance.validation;

import attendance.domain.DateTimeFormatterWrapper;
import attendance.exception.AttendanceArgumentException;
import java.time.format.DateTimeParseException;

public class AttendanceInputValidator {
    static final String CANNOT_BE_EMPTY_NICKNAME = "닉네임은 공백일 수 없습니다.";

    public void validateAttendanceNickname(String nickname) {
        if (nickname == null || nickname.isBlank() || nickname.isEmpty()) {
            throw new AttendanceArgumentException(CANNOT_BE_EMPTY_NICKNAME);
        }
    }

    public void validateAttendanceTime(String time) {
        try {
            DateTimeFormatterWrapper.parsingAttendanceTime(time);
        } catch (DateTimeParseException e) {
            throw new AttendanceArgumentException(e.getMessage());
        }
    }
}
