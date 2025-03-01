package attendance.controller;

import attendance.dto.AttendanceDto;
import attendance.dto.AttendanceDto.AttendanceDetailDto;
import attendance.dto.WarningCrewsDto;
import attendance.model.AttendanceDate;
import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceRecord;
import attendance.model.AttendanceRegister;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceRegister attendanceRegister;
    private final Map<String, Runnable> commands = Map.of(
            "1", this::addAttendance,
            "2", this::modifyAttendance,
            "3", this::displayAttendanceHistory,
            "4", this::displayWarningCrew,
            "Q", () -> System.exit(0)
    );

    public Controller(InputView inputView, OutputView outputView, AttendanceRegister attendanceRegister) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceRegister = attendanceRegister;
    }

    public void run() {
        process(commands.getOrDefault(inputView.inputCommand(), () -> {
            throw new IllegalArgumentException("명령어를 확인해주세요.");
        }));
    }

    private void addAttendance() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.now());
        AttendanceRecord attendanceRecord = attendanceRegister.findAttendanceRecordByName(inputView.inputCrewName());
        LocalTime time = LocalTime.now();
        attendanceRecord.attend(attendanceDate, time);
        outputView.printAttendanceDetail(AttendanceDetailDto.fromArriveAttendance(attendanceDate, time));
    }

    private void modifyAttendance() {
        AttendanceRecord attendanceRecord = attendanceRegister.findAttendanceRecordByName(inputView.inputCrewName());
        AttendanceDate modifyDate = new AttendanceDate(parseLocalDateByDay(inputView.inputModifyAttendanceDate()));
        AttendanceDateTime attendanceDateTime = attendanceRecord.findAttendanceByDate(modifyDate);
        AttendanceDetailDto beforeModifyDto = AttendanceDetailDto.fromArriveAttendance(attendanceDateTime);
        attendanceDateTime.modifyAttendanceTime(LocalTime.parse(inputView.inputModifyAttendanceTime()));
        outputView.printModifyResult(beforeModifyDto, AttendanceDetailDto.fromArriveAttendance(attendanceDateTime));
    }

    private void displayAttendanceHistory() {
        String crewName = inputView.inputCrewName();
        AttendanceRecord attendanceRecord = attendanceRegister.findAttendanceRecordByName(crewName);
        outputView.printAttendanceHistory(AttendanceDto.from(crewName, attendanceRecord));
    }

    private void displayWarningCrew() {
        outputView.printWarningCrews(WarningCrewsDto.from(attendanceRegister));
    }

    private LocalDate parseLocalDateByDay(String day) {
        return LocalDate.parse(String.format("2024-12-%s", day), DateTimeFormatter.ofPattern("yyyy-MM-d"));
    }

    private void process(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
            run();
        }
    }
}
