package attendance.controller;

import attendance.controller.constant.CommandOption;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceTime;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.RiskCrew;
import attendance.exception.CustomException;
import attendance.utils.AttendanceBookParser;
import attendance.utils.FileLoader;
import attendance.utils.Parser;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.IOException;
import java.time.LocalDate;
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
        final LocalDate currentDate = LocalDate.of(2024, 12, 31);
        final AttendanceBookParser parser = new AttendanceBookParser(FileLoader.fileReadLine("attendances.csv"));
        final Crews crews = parser.getCrews();
        AttendanceBook attendanceBook = new AttendanceBook(crews, currentDate);
        initializeFileData(parser, attendanceBook);

        CommandOption commandOption = readCommandOption(currentDate);
        while (!commandOption.equals(CommandOption.QUIT)) {
            handleAttendanceCommand(commandOption, attendanceBook, currentDate, crews);
            commandOption = readCommandOption(currentDate);
        }
    }

    private void handleAttendanceCommand(CommandOption commandOption, AttendanceBook attendanceBook,
                                         LocalDate currentDate, Crews crews) {
        if (commandOption.equals(CommandOption.ATTENDANCE_CHECK)) {
            registerAttendance(attendanceBook, currentDate, crews);
            return;
        }
        if (commandOption.equals(CommandOption.ATTENDANCE_MODIFY)) {
            modifyAttendance(attendanceBook, currentDate, crews);
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

    private void registerAttendance(AttendanceBook attendanceBook, LocalDate currentDate, Crews crews) {
        Crew inputCrewName = readCrewName(crews);
        LocalDateTime attendanceTime = readAttendanceTime(currentDate);
        registeredAttendance(attendanceBook, inputCrewName, attendanceTime);
    }

    private void registeredAttendance(AttendanceBook attendanceBook, Crew inputCrewName, LocalDateTime attendanceTime) {
        try {
            AttendanceTime registeredAttendanceTime = attendanceBook.registerAttendance(inputCrewName, attendanceTime);
            outputView.writeAttendanceRegister(registeredAttendanceTime);
        } catch (CustomException customException) {
            outputView.writeErrorMessage(customException.getMessage());
        }
    }

    private void modifyAttendance(AttendanceBook attendanceBook, LocalDate currentDate, Crews crews) {
        Crew inputCrewName = readModifyCrewName(crews);
        LocalDate inputModifyDay = readModifyDay(currentDate);
        LocalDateTime inputModifyDayTime = readModifyTime(inputModifyDay);

        AttendanceTime beforeTime = attendanceBook.findAttendanceRecord(inputCrewName, inputModifyDayTime);
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

    private LocalDate readModifyDay(LocalDate currentDate) {
        return retryInput(() -> Parser.toDateTime(inputView.readModifyDay(), currentDate));
    }

    private LocalDateTime readModifyTime(LocalDate modifyDay) {
        return retryInput(() -> Parser.toTime(inputView.readModifyTime(), modifyDay));
    }

    private Crew readCrewName(Crews crews) {
        return retryInput(() -> crews.findCrew(inputView.readCrewName()));
    }

    private LocalDateTime readAttendanceTime(LocalDate currentDate) {
        return retryInput(() -> Parser.toTime(inputView.readAttendanceTime(), currentDate));
    }

    private CommandOption readCommandOption(LocalDate currentDate) {
        return retryInput(() -> CommandOption.from(inputView.readCommandOption(currentDate)));
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
