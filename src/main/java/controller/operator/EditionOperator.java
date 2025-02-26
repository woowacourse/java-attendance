package controller;

import domain.AttendanceBook;
import domain.AttendanceDate;
import domain.AttendanceTime;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalTime;
import util.InputProcessor;

public class EditionOperator implements OptionOperator {

    @Override
    public void process(AttendanceBook attendanceBook, LocalDate attendanceDate) {
        Crew crew = InputProcessor.processInputUntilSuccess(() -> {
            String name = inputView.getEditNameInput();
            return attendanceBook.findCrewByName(name);});

        AttendanceDate editDate = InputProcessor.processInputUntilSuccess(() -> {
            int editDateInput = inputView.getEditDayInput();
            return new AttendanceDate(LocalDate.of(2024, 12, editDateInput));
        });

        AttendanceTime editTime = InputProcessor.processInputUntilSuccess(() -> {
            LocalTime editTimeInput = inputView.getEditTimeInput();
            return new AttendanceTime(editTimeInput);
        });

        attendanceBook.editAttendance(crew, editDate, editTime);
    }
}
