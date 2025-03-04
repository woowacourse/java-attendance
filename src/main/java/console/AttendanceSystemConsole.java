package console;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceSystemConsole {
    public LocalDate getDateOfToday() {
        return LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth());
    }

    public LocalDate getDateOfRequestedDate(int date) {
        return LocalDate.of(2024, 12, date);
    }

    public LocalTime readAttendanceTime() {
        String rawAttendanceTime = InputView.readAttendanceTime();
        return Parser.parseTime(rawAttendanceTime);
    }

    public LocalTime readAttendanceTimeToUpdate() {
        String rawNewAttendanceTime = InputView.readNewAttendanceTime();
        return Parser.parseTime(rawNewAttendanceTime);
    }

    public LocalDate readAttendanceDateToUpdate() {
        String rawRequestDate = InputView.readUpdateRequestDate();
        int requestDate = Parser.parseInteger(rawRequestDate);
        return getDateOfRequestedDate(requestDate);
    }

    public FunctionOption getFunctionOption() {
        String optionSign = InputView.readOption();
        return FunctionOption.findBySign(optionSign);
    }
}
