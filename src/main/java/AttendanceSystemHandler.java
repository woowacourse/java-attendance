import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class AttendanceSystemHandler {
    private final AttendanceSystemManager attendanceSystemManager;
    private final Crews crews;
    private final Map<FunctionOption, Runnable> ACTION_FOR_OPTION = Map.of(
            FunctionOption.REGISTER_ATTENDANCE, this::registerAttendance,
            FunctionOption.UPDATE_ATTENDANCE, this::updateAttendance
    );

    public AttendanceSystemHandler(AttendanceSystemManager attendanceSystemManager, Crews crews) {
        this.attendanceSystemManager = attendanceSystemManager;
        this.crews = crews;
    }

    public void run() {
        while (true) {
            String optionSign = InputView.readOption();
            FunctionOption functionOption = FunctionOption.findBySign(optionSign);

            if (functionOption == FunctionOption.QUIT) {
                break;
            }

            ACTION_FOR_OPTION.get(functionOption).run();
        }
    }

    private void registerAttendance() {
        String name = InputView.readName();
        Crew crew = crews.findCrewByName(name);

        String rawAttendanceTime = InputView.readAttendanceTime();
        LocalTime attendanceTime = InputParser.parseTime(rawAttendanceTime);

        LocalDate date = LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth());

        LocalDateTime requestedAt = LocalDateTime.of(date, attendanceTime);

        AttendanceHistory attendanceHistory = attendanceSystemManager.registerNewAttendance(crew, requestedAt);
        OutputView.printAttendanceHistory(attendanceHistory);
    }

    private void updateAttendance() {
        String name = InputView.readNameToUpdate();
        Crew crew = crews.findCrewByName(name);

        String rawRequestDate = InputView.readUpdateRequestDate();
        int requestDate = InputParser.parseInteger(rawRequestDate);

        String rawNewAttendanceTime = InputView.readNewAttendanceTime();
        LocalTime newAttendanceTime = InputParser.parseTime(rawNewAttendanceTime);

        LocalDateTime newAttendanceAt = LocalDateTime.of(LocalDate.of(2024, 12, requestDate), newAttendanceTime);

        // TODO: 출력 고민해보기
        attendanceSystemManager.updateRegisteredAttendance(crew, newAttendanceAt);
    }
}
