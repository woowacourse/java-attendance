package controller;

import java.time.LocalDate;
import java.util.function.BiConsumer;
import model.AttendanceBook;
import view.ResultView;

public class DismissalCrewCheckConsumer implements BiConsumer<AttendanceBook, LocalDate> {

    private final ResultView resultView;

    public DismissalCrewCheckConsumer(final ResultView resultView) {
        this.resultView = resultView;
    }

    @Override
    public void accept(final AttendanceBook attendanceBook, final LocalDate localDate) {
        resultView.printAllDismissalCrew(attendanceBook.getAllDismissalCrewByOrder(localDate));
    }
}
