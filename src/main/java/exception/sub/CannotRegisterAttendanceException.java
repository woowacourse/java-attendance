package exception.sub;

import exception.parent.CustomException;
import java.time.LocalDateTime;
import view.format.CustomDateTimeFormatter;

public class CannotRegisterAttendanceException extends CustomException {
    public CannotRegisterAttendanceException(LocalDateTime date) {
        super(String.format("%s은 등교일이 아닙니다.", CustomDateTimeFormatter.formatDateAndDay(date)));
    }
}
