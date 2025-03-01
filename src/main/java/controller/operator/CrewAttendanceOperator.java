package controller.operator;

import domain.AttendanceBook;
import domain.Crew;
import java.time.LocalDate;
import util.InputProcessor;

public class CrewAttendanceOperator implements OptionOperator {

    @Override
    public void process(AttendanceBook attendanceBook, LocalDate attendanceDate) {
        Crew crew = InputProcessor.processInputUntilSuccess(() -> {
            String nameInput = inputView.getNameInput();
            return attendanceBook.findCrewByName(nameInput);
        });

        outputView.printAttendances(crew);
    }
}
