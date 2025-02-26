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

        Crew crew = InputProcessor.processInputUntilSuccess(() -> {
            String name = inputView.getNameInput();
            Crew attendCrew = attendanceBook.findCrewByName(name);
            attendanceBook.checkAlreadyAttended(attendCrew, attendanceDate);
            return attendCrew;
        });

        AttendanceTime attendanceTime = InputProcessor.processInputUntilSuccess(() -> {
            LocalTime attendTime = inputView.getAttendTimeInput();
            return new AttendanceTime(attendTime);
        });

        attendanceBook.attend(crew, attendanceDate, attendanceTime);
        Attendance attendance = crew.findAttendanceByDate(attendanceDate);

        outputView.printAttendanceMessage(attendance);
    }
}
