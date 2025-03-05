package controller;

import dto.AttendanceHistoryDto;
import java.time.LocalDate;
import java.util.List;
import java.util.function.BiConsumer;
import model.AttendanceBook;
import model.AttendanceHistory;
import model.AttendanceHistoryByDate;
import model.AttendanceTypeCountResult;
import model.DismissalType;
import view.InputView;
import view.ResultView;

public class AttendanceHistoryConsumer implements BiConsumer<AttendanceBook, LocalDate> {


    private final InputView inputView;
    private final ResultView resultView;

    public AttendanceHistoryConsumer(final InputView inputView, final ResultView resultView) {
        this.inputView = inputView;
        this.resultView = resultView;
    }

    @Override
    public void accept(final AttendanceBook attendanceBook, final LocalDate todayDate) {
        String nickname = inputView.getAttendanceHistoryCrewNickname();
        AttendanceHistory attendanceHistory = attendanceBook.getAttendanceHistoryByNickname(nickname);

        List<AttendanceHistoryByDate> historyWithType = getAndPrintAttendanceHistory(nickname, attendanceHistory,
                todayDate);
        AttendanceTypeCountResult attendanceTypeCountResult = getAndPrintAttendanceTypeCountResult(historyWithType);
        determineAndPrintDismissalType(attendanceTypeCountResult);
    }


    private List<AttendanceHistoryByDate> getAndPrintAttendanceHistory(
            final String nickname,
            final AttendanceHistory attendanceHistory,
            final LocalDate todayDate
    ) {
        List<AttendanceHistoryByDate> historyWithType = attendanceHistory.getHistoryWithAttendanceType(todayDate);
        List<AttendanceHistoryDto> dtos = historyWithType.stream()
                .map(value -> new AttendanceHistoryDto(
                        value.date(), value.time(), value.type()))
                .toList();

        resultView.printAttendanceHistory(nickname, dtos);
        return historyWithType;
    }

    private AttendanceTypeCountResult getAndPrintAttendanceTypeCountResult(
            final List<AttendanceHistoryByDate> historyWithType) {
        AttendanceTypeCountResult attendanceTypeCountResult = AttendanceTypeCountResult.from(historyWithType);
        resultView.printAttendanceTypeCountResult(attendanceTypeCountResult);
        return attendanceTypeCountResult;
    }

    private void determineAndPrintDismissalType(final AttendanceTypeCountResult attendanceTypeCountResult) {
        DismissalType dismissalType = DismissalType.from(attendanceTypeCountResult.typeResult());
        resultView.printDismissalType(dismissalType);
    }
}
