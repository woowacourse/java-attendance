package attendance.controller;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceTime;
import attendance.exception.CustomException;
import attendance.utils.AttendanceBookParser;
import attendance.utils.FileLoader;
import attendance.view.InputView;
import attendance.view.OutputView;
import attendance.view.constant.CommandOption;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() throws IOException {
        LocalDateTime currentDateTime = LocalDateTime.now().withYear(2024).withMonth(12).withDayOfMonth(31);
        AttendanceBookParser parser = new AttendanceBookParser(FileLoader.fileReadLine("attendances.csv"));
        AttendanceBook attendanceBook = new AttendanceBook(parser.getCrews(), currentDateTime);
        initializeFileData(parser, attendanceBook);

        CommandOption commandOption = inputView.readCommandOption(currentDateTime);
        while (!commandOption.equals(CommandOption.QUIT)) {
            if (commandOption.equals(CommandOption.ATTENDANCE_CHECK)) {
                registerAttendance(attendanceBook, currentDateTime);
            }
            if (commandOption.equals(CommandOption.ATTENDANCE_MODIFY)) {
                modifyAttendance(attendanceBook, currentDateTime);
            }
            commandOption = inputView.readCommandOption(currentDateTime);
        }
    }

    private void initializeFileData(AttendanceBookParser parser, AttendanceBook attendanceBook) {
        parser.getOriginalAttendanceBook().forEach((crew, newAttendanceTimes) -> {
            for (LocalDateTime newTime : newAttendanceTimes) {
                attendanceBook.modifyAttendance(crew.getName(), newTime);
            }
        });
    }

    //TODO : now가 주말이면 출석확인 버튼 누르면 처리해줘야함 예외

    private void registerAttendance(AttendanceBook attendanceBook, LocalDateTime currentDateTime) {
        String inputCrewName = readCrewName();
        LocalDateTime attendanceTime = readAttendanceTime(currentDateTime);
        AttendanceTime registerdAttendanceTime = attendanceBook.registerAttendance(inputCrewName, attendanceTime);
        outputView.writeAttendanceRegister(registerdAttendanceTime);
    }

    private void modifyAttendance(AttendanceBook attendanceBook, LocalDateTime currentDateTime) {
        String inputCrewName = readModifyCrewName();
        LocalDateTime inputModifyDay = readModifyDay(currentDateTime);
        LocalDateTime inputModifyDayTime = readModifyTime(inputModifyDay);

        AttendanceTime beforeTime = attendanceBook.findBeforeAttendanceRecord(inputCrewName, inputModifyDayTime);
        AttendanceTime updateTime = attendanceBook.modifyAttendance(inputCrewName, inputModifyDayTime);
        outputView.writeAttendanceModify(beforeTime, updateTime);
    }

    private String readModifyCrewName() {
        return retryInput(inputView::readModifyCrewName);
    }

    private LocalDateTime readModifyDay(LocalDateTime currentDateTime) {
        return retryInput(() -> inputView.readModifyDay(currentDateTime));
    }

    private LocalDateTime readModifyTime(LocalDateTime modifyDayTime) {
        return retryInput(() -> inputView.readModifyTime(modifyDayTime));
    }

    private String readCrewName() {
        return retryInput(inputView::readCrewName);
    }

    private LocalDateTime readAttendanceTime(LocalDateTime currentDateTime) {
        return retryInput(() -> inputView.readAttendanceTime(currentDateTime));
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
