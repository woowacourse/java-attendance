package controller.operator;

import domain.AttendanceBook;
import domain.Crew;
import java.time.LocalDate;
import java.util.List;

public class ExpelledCrewOperator implements OptionOperator {

    @Override
    public void process(AttendanceBook attendanceBook, LocalDate attendanceDate) {
        List<Crew> crews = attendanceBook.findRiskOfExpulsionCrew(attendanceDate);
        outputView.printExpelledCrews(crews);
    }
}
