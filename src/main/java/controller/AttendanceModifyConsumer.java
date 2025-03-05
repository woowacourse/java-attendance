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

public class AttendanceModifyConsumer implements BiConsumer<AttendanceBook, LocalDate> {

    private final InputView inputView;
    private final ResultView resultView;

    public AttendanceModifyConsumer(final InputView inputView, final ResultView resultView) {
        this.inputView = inputView;
        this.resultView = resultView;
    }

    @Override
    public void accept(final AttendanceBook attendanceBook, final LocalDate todayDate) {
        AttendanceHistory attendanceHistory = inputNicknameAndGetAttendanceHistory(attendanceBook);
        AttendanceDate attendanceDate = inputAndGetModifyDate(todayDate);

        AttendanceTime attendanceTime = inputAndGetModifyTime();

        AttendanceTime oldAttendanceTime = attendanceHistory.modify(attendanceDate, attendanceTime);

        printResult(attendanceDate, oldAttendanceTime, attendanceTime);
    }

    private AttendanceHistory inputNicknameAndGetAttendanceHistory(final AttendanceBook attendanceBook) {
        String nickname = inputView.getAttendanceModifyCrewNickname();
        return attendanceBook.getAttendanceHistoryByNickname(nickname);
    }

    private AttendanceDate inputAndGetModifyDate(final LocalDate todayDate) {
        LocalDate modifyDate = InputParser.parseDayOfMonth(inputView.getAttendanceModifyDayOfMonth());
        validateModifyDate(todayDate, modifyDate);
        return new AttendanceDate(modifyDate);
    }

    private void validateModifyDate(final LocalDate todayDate, final LocalDate modifyDate) {
        if (todayDate.isBefore(modifyDate)) {
            throw new IllegalArgumentException("[ERROR] 과거의 출석 기록만 수정할 수 있습니다.");
        }
    }

    private AttendanceTime inputAndGetModifyTime() {
        LocalTime modifyTime = InputParser.parseTime(inputView.getAttendanceModifyTime());
        return new AttendanceTime(modifyTime);
    }

    private void printResult(
            final AttendanceDate attendanceDate,
            final AttendanceTime oldAttendanceTime,
            final AttendanceTime attendanceTime
    ) {
        resultView.printAttendanceModifyResult(
                new AttendanceHistoryDto(
                        attendanceDate.getDate(), oldAttendanceTime.getTime(),
                        AttendanceType.from(attendanceDate, oldAttendanceTime)),
                new AttendanceHistoryDto(
                        attendanceDate.getDate(), attendanceTime.getTime(),
                        AttendanceType.from(attendanceDate, attendanceTime))
        );
    }
}
