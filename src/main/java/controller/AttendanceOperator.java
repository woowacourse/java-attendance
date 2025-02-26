package controller;

import domain.AttendanceBook;
import domain.AttendanceDate;
import domain.AttendanceTime;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceOperator implements OptionOperator {

    @Override
    public void process(AttendanceBook attendanceBook, LocalDate nowDate) {
        AttendanceDate attendanceDate = new AttendanceDate(nowDate);

        String name = inputView.getAttendNameInput();
        Crew crew = attendanceBook.findCrewByName(name);
        attendanceBook.checkAlreadyAttended(crew, attendanceDate);

        LocalTime attendTime = inputView.getAttendTimeInput();
        AttendanceTime attendanceTime = new AttendanceTime(attendTime);

        attendanceBook.attend(crew, attendanceDate, attendanceTime);
    }
}
