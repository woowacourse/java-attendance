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
        return InputParser.parseTime(rawAttendanceTime);
    }

    public LocalTime readAttendanceTimeToUpdate() {
        String rawNewAttendanceTime = InputView.readNewAttendanceTime();
        return InputParser.parseTime(rawNewAttendanceTime);
    }

    public LocalDate readAttendanceDateToUpdate() {
        String rawRequestDate = InputView.readUpdateRequestDate();
        int requestDate = InputParser.parseInteger(rawRequestDate);
        return getDateOfRequestedDate(requestDate);
    }

    public FunctionOption getFunctionOption() {
        String optionSign = InputView.readOption();
        return FunctionOption.findBySign(optionSign);
    }
}
