import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceSystemHandler {
    private final AttendanceSystemManager attendanceSystemManager;
    private final Crews crews;

    public AttendanceSystemHandler(AttendanceSystemManager attendanceSystemManager, Crews crews) {
        this.attendanceSystemManager = attendanceSystemManager;
        this.crews = crews;
    }

    public void run() {
        String optionSign = InputView.readOption();
        FunctionOption functionOption = FunctionOption.findBySign(optionSign);

        String name = InputView.readName();
        Crew crew = crews.findCrewByName(name);

        String rawAttendanceTime = InputView.readAttendanceTime();
        LocalTime attendanceTime = InputParser.parseTime(rawAttendanceTime);

        LocalDate date = LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth());

        LocalDateTime requestedAt = LocalDateTime.of(date, attendanceTime);

        AttendanceHistory attendanceHistory = attendanceSystemManager.registerNewAttendance(crew, requestedAt);
        OutputView.printAttendanceHistory(attendanceHistory);
    }
}
