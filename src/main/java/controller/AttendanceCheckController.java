package controller;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import domain.AttendanceTimes;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import util.RepeatExecutor;

public class AttendanceCheckController implements AttendanceController {

    @Override
    public void process(Attendance attendance, LocalDate nowDate) {
        String nickName = RepeatExecutor.repeatUntilSuccess(this::processNickNameInput, outputView::printErrorMessage, attendance);
        outputView.printCrewAttendanceHeader(nickName);

        AttendanceTimes crewAttendances = attendance.getAttendanceTimes(nickName);
        List<AttendanceTime> attendanceTimes = crewAttendances.getAttendanceTimes();
        attendanceTimes.sort(Comparator.comparing(AttendanceTime::getAttendanceDateTime));
        printCrewAttendances(attendance, attendanceTimes, nickName);
    }

    private String processNickNameInput(Attendance attendance) {
        String nickName = inputView.readNickname();
        attendance.validateNickName(nickName);
        return nickName;
    }

    private void printCrewAttendances(Attendance attendance, List<AttendanceTime> attendanceTimes, String nickName) {
        for (AttendanceTime crewAttendance : attendanceTimes) {
            outputView.printCheckAttendanceMessage(crewAttendance);
        }

        Map<AttendanceStatus, Integer> attendStatuses = attendance.getCrewAttendanceStatus(nickName);
        outputView.printCrewStatuses(attendStatuses);
    }
}
