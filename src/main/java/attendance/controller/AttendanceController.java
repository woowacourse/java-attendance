package attendance.controller;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceRecord;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceBook attendanceBook;
    private final Crews crews;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceBook attendanceBook,
                                Crews crews) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceBook = attendanceBook;
        this.crews = crews;
    }

    public void run() {
        Map<MainOption, Runnable> commands = Map.of(
                MainOption.CHECK_ATTENDANCE, this::processCheckAttendance,
                MainOption.QUIT, () -> System.exit(0)
        );

        Runnable action = commands.getOrDefault(MainOption.from(inputView.readOption()), this::run);
        action.run();
        run();
    }

    private void processCheckAttendance() {
        process(() -> {
            Crew crew = crews.findByName(inputView.readName());
            LocalTime entryTime = inputView.readEntryTime();

            AttendanceRecord record = new AttendanceRecord(LocalDateTime.of(LocalDate.of(2024, 12, 16), entryTime));
            attendanceBook.add(crew.getName(),
                    record);

            outputView.displayAttendanceResult(record);
        });
    }

    private void process(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
    }
}
