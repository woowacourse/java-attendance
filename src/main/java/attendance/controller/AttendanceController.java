package attendance.controller;

import attendance.controller.constant.CommandOption;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceTime;
import attendance.domain.CampusOperationTime;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.PublicHolidays;
import attendance.domain.RiskCrew;
import attendance.exception.CustomException;
import attendance.utils.AttendanceBookParser;
import attendance.utils.FileLoader;
import attendance.utils.Parser;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() throws IOException {
        final LocalDateTime currentDateTime = LocalDateTime.now().withYear(2024).withMonth(12).withDayOfMonth(31);
        final AttendanceBookParser parser = new AttendanceBookParser(FileLoader.fileReadLine("attendances.csv"));
        final Crews crews = parser.getCrews();
        AttendanceBook attendanceBook = new AttendanceBook(crews, currentDateTime);
        initializeFileData(parser, attendanceBook);

        CommandOption commandOption = readCommandOption(currentDateTime);
        while (!commandOption.equals(CommandOption.QUIT)) {
            handleAttendanceCommand(commandOption, attendanceBook, currentDateTime, crews);
            commandOption = readCommandOption(currentDateTime);
        }
    }

    private void handleAttendanceCommand(CommandOption commandOption, AttendanceBook attendanceBook,
                                         LocalDateTime currentDateTime, Crews crews) {
        if (commandOption.equals(CommandOption.ATTENDANCE_CHECK)) {
            registerAttendance(attendanceBook, currentDateTime, crews);
            return;
        }
        if (commandOption.equals(CommandOption.ATTENDANCE_MODIFY)) {
            modifyAttendance(attendanceBook, currentDateTime, crews);
            return;
        }
        if (commandOption.equals(CommandOption.ATTENDANCE_RECORD_CHECK)) {
            checkAttendance(attendanceBook, crews);
            return;
        }
        if (commandOption.equals(CommandOption.PENALTY_CREWS_CHECK)) {
            checkPenaltyCrews(attendanceBook);
        }
    }

    private void initializeFileData(AttendanceBookParser parser, AttendanceBook attendanceBook) {
        parser.getOriginalAttendanceBook().forEach((crew, newAttendanceTimes) -> {
            for (LocalDateTime newTime : newAttendanceTimes) {
                attendanceBook.modifyAttendance(crew, newTime);
            }
        });
    }

    //TODO : 시간:분 형식 처리
    //TODO : 시간 숫자로 입력 안한거 처리
    //TODO : 분은 숫자로 입력 안한거 처리
    private void registerAttendance(AttendanceBook attendanceBook, LocalDateTime currentDateTime, Crews crews) {
        if (checkPublicHolidays(currentDateTime) || checkCampusOperationTime(currentDateTime)) {
            return;
        }
        Crew inputCrewName = readCrewName(crews);
        LocalDateTime attendanceTime = readAttendanceTime(currentDateTime);
        AttendanceTime registerdAttendanceTime = attendanceBook.registerAttendance(inputCrewName, attendanceTime);
        outputView.writeAttendanceRegister(registerdAttendanceTime);
    }

    private boolean checkCampusOperationTime(LocalDateTime currentDateTime) {
        try {
            CampusOperationTime.isOperation(currentDateTime.getHour());
        } catch (CustomException customException) {
            outputView.writeErrorMessage(customException.getMessage());
            return true;
        }
        return false;
    }

    private boolean checkPublicHolidays(LocalDateTime currentDateTime) {
        try {
            PublicHolidays.checkPublicHolidays(currentDateTime.toLocalDate());
        } catch (CustomException customException) {
            outputView.writeErrorMessage(customException.getMessage());
            return true;
        }
        return false;
    }

    private void modifyAttendance(AttendanceBook attendanceBook, LocalDateTime currentDateTime, Crews crews) {
        Crew inputCrewName = readModifyCrewName(crews);
        LocalDateTime inputModifyDay = readModifyDay(currentDateTime);
        LocalDateTime inputModifyDayTime = readModifyTime(inputModifyDay);

        AttendanceTime beforeTime = attendanceBook.findBeforeAttendanceRecord(inputCrewName, inputModifyDayTime);
        AttendanceTime updateTime = attendanceBook.modifyAttendance(inputCrewName, inputModifyDayTime);
        outputView.writeAttendanceModify(beforeTime, updateTime);
    }

    private void checkAttendance(AttendanceBook attendanceBook, Crews crews) {
        Crew inputCrewName = readCrewName(crews);
        AttendanceRecord attendanceRecord = attendanceBook.findAttendanceRecord(inputCrewName);
        outputView.writeAttendanceCheck(inputCrewName, attendanceRecord);
    }

    private void checkPenaltyCrews(AttendanceBook attendanceBook) {
        List<RiskCrew> riskCrews = attendanceBook.findPenaltyCrews();
        outputView.writeRiskCrews(riskCrews);
    }

    private Crew readModifyCrewName(Crews crews) {
        return retryInput(() -> crews.findCrew(inputView.readModifyCrewName()));
    }

    private LocalDateTime readModifyDay(LocalDateTime currentDateTime) {
        return retryInput(() -> Parser.toDateTime(inputView.readModifyDay(), currentDateTime));
    }

    private LocalDateTime readModifyTime(LocalDateTime modifyDayTime) {
        return retryInput(() -> inputView.readModifyTime(modifyDayTime));
    }

    private Crew readCrewName(Crews crews) {
        return retryInput(() -> crews.findCrew(inputView.readCrewName()));
    }

    private LocalDateTime readAttendanceTime(LocalDateTime currentDateTime) {
        return retryInput(() -> inputView.readAttendanceTime(currentDateTime));
    }

    private CommandOption readCommandOption(LocalDateTime currentDateTime) {
        return retryInput(() -> CommandOption.from(inputView.readCommandOption(currentDateTime)));
    }

    private <T> T retryInput(final Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (CustomException customException) {
                outputView.writeErrorMessage(customException.getMessage());
            }
        }
    }

}
