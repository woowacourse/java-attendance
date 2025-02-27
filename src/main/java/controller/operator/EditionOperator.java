package controller.operator;

import constant.Constants;
import domain.Attendance;
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
        Crew crew = processCrewInput(attendanceBook);
        AttendanceDate editDate = processEditDateInput(attendanceBook, crew);
        Attendance oldAttendance = crew.findAttendanceByDate(editDate);
        AttendanceTime editTime = processEditTimeInput();

        attendanceBook.editAttendance(crew, editDate, editTime);

        Attendance newAttendance = crew.findAttendanceByDate(editDate);
        outputView.printEditMessage(oldAttendance, newAttendance);
    }

    private Crew processCrewInput(AttendanceBook attendanceBook) {
        return InputProcessor.processInputUntilSuccess(() -> {
            String name = inputView.getEditNameInput();
            return attendanceBook.findCrewByName(name);
        });
    }

    private AttendanceDate processEditDateInput(AttendanceBook attendanceBook, Crew crew) {
        return InputProcessor.processInputUntilSuccess(() -> {
            int editDateInput = inputView.getEditDayInput();
            AttendanceDate date = new AttendanceDate(LocalDate.of(Constants.TARGET_YEAR, Constants.TARGET_MONTH, editDateInput));
            attendanceBook.checkAttendanceExist(crew, date);
            return date;
        });
    }

    private AttendanceTime processEditTimeInput() {
        return InputProcessor.processInputUntilSuccess(() -> {
            LocalTime editTimeInput = inputView.getEditTimeInput();
            return new AttendanceTime(editTimeInput);
        });
    }
}
