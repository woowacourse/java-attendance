package controller;

import dto.AttendanceHistoryDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.BiConsumer;
import model.AttendanceBook;
import model.AttendanceDate;
import model.AttendanceHistory;
import model.AttendanceTime;
import model.AttendanceType;
import util.InputParser;
import view.InputView;
import view.ResultView;

public class AttendanceCheckConsumer implements BiConsumer<AttendanceBook, LocalDate> {

    private final InputView inputView;
    private final ResultView resultView;

    public AttendanceCheckConsumer(final InputView inputView, final ResultView resultView) {
        this.inputView = inputView;
        this.resultView = resultView;
    }

    @Override
    public void accept(final AttendanceBook attendanceBook, final LocalDate todayDate) {
        AttendanceDate attendanceDate = new AttendanceDate(todayDate);
        AttendanceHistory attendanceHistory = inputNicknameAndGetAttendanceHistory(attendanceBook);
        AttendanceTime attendanceTime = inputAndGetAttendanceTime();

        attendanceHistory.add(attendanceDate, attendanceTime);

        printResult(attendanceDate, attendanceTime);
    }

    private AttendanceHistory inputNicknameAndGetAttendanceHistory(final AttendanceBook attendanceBook) {
        String nickname = inputView.getAttendanceCheckCrewNickname();
        return attendanceBook.getAttendanceHistoryByNickname(nickname);
    }

    private AttendanceTime inputAndGetAttendanceTime() {
        LocalTime time = InputParser.parseTime(inputView.getAttendanceCheckTime());
        return new AttendanceTime(time);
    }

    private void printResult(final AttendanceDate attendanceDate, final AttendanceTime attendanceTime) {
        LocalDate date = attendanceDate.getDate();
        LocalTime time = attendanceTime.getTime();
        AttendanceType type = AttendanceType.from(attendanceDate, attendanceTime);
        resultView.printAttendanceCheckResult(
                new AttendanceHistoryDto(date, time, type)
        );
    }
}
