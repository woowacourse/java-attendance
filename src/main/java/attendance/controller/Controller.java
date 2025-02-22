package attendance.controller;

import static attendance.util.DateFormatUtil.NOT_ATTENDABLE_FORMATTER;

import attendance.dto.AttendanceDto;
import attendance.dto.AttendanceDto.AttendanceDetailDto;
import attendance.dto.WarningCrewsDto;
import attendance.model.AttendanceDetail;
import attendance.model.Crew;
import attendance.model.CrewDataLoader;
import attendance.model.Crews;
import attendance.model.CustomLocalDateTime;
import attendance.util.DateFormatUtil;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDateTime;
import java.util.Map;

public class Controller {
    public static final String FILE_NAME = "attendances.csv";

    private final InputView inputView;
    private final OutputView outputView;
    private static final Crews crews = new Crews();

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void loadDate() {
        CrewDataLoader crewDataLoader = new CrewDataLoader(crews, CustomLocalDateTime.now());
        crewDataLoader.load(FILE_NAME);
    }

    public void run() {
        Map<String, Runnable> commands = Map.of(
                "1", this::processAddAttendance,
                "2", this::processModifyAttendance,
                "3", this::processDisplayAttendanceHistory,
                "4", this::processDisplayWarningCrew,
                "Q", () -> System.exit(0)
        );

        Runnable action = commands.getOrDefault(inputView.inputCommand(), this::run);
        action.run();
    }

    private void processAddAttendance() {
        process(() -> {
            if (CustomLocalDateTime.isWeekendOrHoliday(CustomLocalDateTime.nowDate())) {
                throw new IllegalArgumentException(CustomLocalDateTime.nowDate().format(NOT_ATTENDABLE_FORMATTER));
            }
            Crew crew = crews.findCrew(inputView.inputCrewName());
            AttendanceDetail attendanceDetail = new AttendanceDetail(LocalDateTime.of(
                    CustomLocalDateTime.nowDate(),
                    DateFormatUtil.parseTime(inputView.inputEntryTime())
            ));
            crew.attend(attendanceDetail);
            outputView.printAttendanceDetail(AttendanceDetailDto.from(attendanceDetail));
        });
    }

    private void processModifyAttendance() {
        process(() -> {
            Crew crew = crews.findCrew(inputView.inputModifyAttendanceCrewName());
            AttendanceDetail attendanceDetail = crew.findAttendanceDetail(
                    CustomLocalDateTime.parseDate(inputView.inputModifyAttendanceDate())
            );
            AttendanceDetail beforeModify = new AttendanceDetail(attendanceDetail.getAttendanceDateTime());
            attendanceDetail.modify(DateFormatUtil.parseTime(inputView.inputModifyAttendanceTime()));
            outputView.printModifyResult(
                    AttendanceDetailDto.from(beforeModify),
                    AttendanceDetailDto.from(attendanceDetail)
            );
        });
    }

    private void processDisplayAttendanceHistory() {
        process(() -> outputView.printAttendanceHistory(
                AttendanceDto.from(crews.findCrew(inputView.inputCrewName())))
        );
    }

    private void processDisplayWarningCrew() {
        process(() -> outputView.printWarningCrews(WarningCrewsDto.from(crews)));
    }

    private void process(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
    }
}
