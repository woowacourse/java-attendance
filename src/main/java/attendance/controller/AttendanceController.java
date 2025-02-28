package attendance.controller;

import static attendance.controller.MainOption.CHECK_ATTENDANCE;
import static attendance.controller.MainOption.CHECK_WARNING;
import static attendance.controller.MainOption.MODIFY_ATTENDANCE;
import static attendance.controller.MainOption.QUIT;
import static attendance.controller.MainOption.VIEW_CREW_HISTORY;

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
import java.util.Optional;

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
                CHECK_ATTENDANCE, this::processCheckAttendance,
                MODIFY_ATTENDANCE, this::processModifyRecord,
                VIEW_CREW_HISTORY, this::processViewCrewHistory,
                CHECK_WARNING, this::processCheckWarning,
                QUIT, () -> System.exit(0)
        );

        MainOption option = getOption();
        Runnable action = commands.get(option);
        action.run();
        run();
    }

    private MainOption getOption() {
        while (true) {
            try {
                return MainOption.from(inputView.readOption());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void processCheckAttendance() {
        process(() -> {
            WoowaDate woowaDate = new WoowaDate(clock.nowDate(), policy);

            Crew crew = crews.findByName(inputView.readName());
            LocalTime entryTime = inputView.readEntryTime();

            AttendanceRecord record = new AttendanceRecord(woowaDate, entryTime);
            attendanceBook.add(crew, record);

            outputView.displayAttendanceResult(record);
        });
    }

    private void processModifyRecord() {
        process(() -> {
            Crew crew = crews.findByName(inputView.readModifyName());
            WoowaDate targetDate = createTargetDate();

            Optional<AttendanceRecord> findOldRecord = attendanceBook.findRecordBy(crew, targetDate);
            AttendanceRecord oldRecord = findOldRecord.map(AttendanceRecord::copy).orElse(null);

            LocalTime modifyTime = inputView.readModifyTime();
            attendanceBook.modify(crew, targetDate, modifyTime);
            AttendanceRecord newRecord = attendanceBook.findRecordBy(crew, targetDate).get();

            outputView.displayModifyResult(oldRecord, newRecord);
        });
    }

    private WoowaDate createTargetDate() {
        int modifyDay = inputView.readModifyDay();
        return new WoowaDate(clock.createDateFromDay(modifyDay), policy);
    }

    private void processViewCrewHistory() {
        process(() -> {
            Crew crew = crews.findByName(inputView.readName());

            AttendanceHistory history = attendanceBook.getHistoryByCrew(crew);

            AttendanceReport report = history.toReport(clock.getMonthStartDay(), clock.nowDate(), policy);
            outputView.displayCrewHistory(CrewHistoryDto.of(crew, report));
        });
    }

    private void processCheckWarning() {
        List<WarningResultDto> warningDtos = crews.getAllCrews().values().stream()
                .map(crew -> {
                    AttendanceHistory history = attendanceBook.getHistoryByCrew(crew);
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
