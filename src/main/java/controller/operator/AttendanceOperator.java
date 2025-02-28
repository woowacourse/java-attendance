package controller.operator;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendanceDate;
import domain.AttendanceTime;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalTime;
import util.InputProcessor;

public class AttendanceOperator implements OptionOperator {

    @Override
    public void process(AttendanceBook attendanceBook, LocalDate nowDate) {
        AttendanceDate attendanceDate = new AttendanceDate(nowDate);
        Crew crew = processCrewInput(attendanceBook, attendanceDate);
        AttendanceTime attendanceTime = processTimeInput();

        attendanceBook.attend(crew, attendanceDate, attendanceTime);
        Attendance attendance = crew.findAttendanceByDate(attendanceDate);

        outputView.printAttendanceMessage(attendance);
    }

    private Crew processCrewInput(AttendanceBook attendanceBook, AttendanceDate attendanceDate) {
        return InputProcessor.processInputUntilSuccess(() -> {
            String name = inputView.getNameInput();
            Crew attendCrew = attendanceBook.findCrewByName(name);
            attendanceBook.checkAlreadyAttended(attendCrew, attendanceDate);
            return attendCrew;
        });
    }

    private AttendanceTime processTimeInput() {
        return InputProcessor.processInputUntilSuccess(() -> {
            LocalTime attendTime = inputView.getAttendTimeInput();
            return new AttendanceTime(attendTime);
        });
    }
}
