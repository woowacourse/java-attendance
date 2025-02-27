package attendance.controller;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceReport;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.CustomClock;
import attendance.domain.EducationDayPolicy;
import attendance.domain.WoowaDate;
import attendance.dto.CrewHistoryDto;
import attendance.dto.WarningResultDto;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceBook attendanceBook;
    private final Crews crews;
    private final CustomClock clock;
    private final EducationDayPolicy policy;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceBook attendanceBook,
                                Crews crews, CustomClock clock, EducationDayPolicy policy) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceBook = attendanceBook;
        this.crews = crews;
        this.clock = clock;
        this.policy = policy;
    }

    public void run() {

        Map<MainOption, Runnable> commands = Map.of(
                MainOption.CHECK_ATTENDANCE, this::processCheckAttendance,
                MainOption.MODIFY_ATTENDANCE, this::processModifyRecord,
                MainOption.VIEW_CREW_HISTORY, this::processViewCrewHistory,
                MainOption.CHECK_WARNING, this::processCheckWarning,
                MainOption.QUIT, () -> System.exit(0)
        );

        Runnable action = commands.getOrDefault(MainOption.from(inputView.readOption()), this::run);
        action.run();
        run();
    }

    private void processCheckAttendance() {
        process(() -> {
            WoowaDate woowaDate = new WoowaDate(clock.nowDate(), policy);

            Crew crew = crews.findByName(inputView.readName());
            LocalTime entryTime = inputView.readEntryTime();

            AttendanceRecord record = new AttendanceRecord(woowaDate, entryTime);
            attendanceBook.add(crew.getName(), record);

            outputView.displayAttendanceResult(record);
        });
    }

    private void processModifyRecord() {
        process(() -> {
            Crew crew = crews.findByName(inputView.readModifyName());
            int modifyDay = inputView.readModifyDay();
            WoowaDate targetDate = new WoowaDate(clock.createDateFromDay(modifyDay), policy);
            LocalTime modifyTime = inputView.readModifyTime();

            AttendanceRecord oldRecord = attendanceBook.getRecordBy(crew.getName(), targetDate).copy();
            attendanceBook.modify(crew.getName(), targetDate, modifyTime);
            AttendanceRecord newRecord = attendanceBook.getRecordBy(crew.getName(), targetDate);

            outputView.displayModifyResult(oldRecord, newRecord);
        });
    }

    private void processViewCrewHistory() {
        process(() -> {
            Crew crew = crews.findByName(inputView.readName());

            AttendanceHistory history = attendanceBook.getHistoryByName(crew.getName());

            AttendanceReport report = history.toReport(clock.getMonthStartDay(), clock.nowDate(), policy);
            outputView.displayCrewHistory(CrewHistoryDto.of(crew, report));
        });
    }

    private void processCheckWarning() {
        List<WarningResultDto> warningDtos = crews.getAllCrews().values().stream()
                .map(crew -> {
                    AttendanceHistory history = attendanceBook.getHistoryByName(crew.getName());
                    AttendanceReport report = history.toReport(clock.getMonthStartDay(), clock.nowDate(), policy);
                    return WarningResultDto.of(crew, report);
                })
                .toList();

        outputView.displayWarningCrews(warningDtos);
    }

    private void process(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
    }
}
