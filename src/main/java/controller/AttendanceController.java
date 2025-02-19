package controller;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import domain.ExpelStatus;
import domain.MenuOption;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import util.AttendancesFileHandler;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() throws IOException {
        LocalDate nowDate = LocalDate.now();
        Attendance attendance = new Attendance(AttendancesFileHandler.generateAttendances(), nowDate);

        outputView.printMenuHeader(nowDate);
        String option = inputView.readOption(Arrays.asList(MenuOption.values()));

        if (option.equals(MenuOption.ATTENDANCE_CHECK.getCommand())) {
            // 1. 출석 확인
            String nickName = inputView.readNickname();
            LocalTime arrivalTime = inputView.readArrivalTime();
            attendance.attend(nickName, LocalDateTime.of(nowDate, arrivalTime));

            LocalDateTime attendanceDateTime = attendance.getAttendanceDateTime(nickName, nowDate);
            AttendanceStatus attendanceStatus = attendance.getAttendanceStatus(nickName, nowDate);

            outputView.printCheckAttendanceMessage(attendanceDateTime, attendanceStatus);
        } else if (option.equals(MenuOption.ATTENDANCE_CORRECTION.getCommand())) {
            // 2. 출석 수정
            String nickName = inputView.readEditNickname();
            int editArrivalDate = inputView.readEditArrivalDate();
            LocalTime editArrivalTime = inputView.readEditArrivalTime();

            LocalDate editDate = LocalDate.of(2024, 12, editArrivalDate);
            LocalDateTime oldAttendanceDateTime = attendance.getAttendanceDateTime(nickName, editDate);
            AttendanceStatus oldAttendanceStatus = attendance.getAttendanceStatus(nickName, editDate);
            attendance.edit(nickName, editArrivalDate, editArrivalTime);
            LocalDateTime newAttendanceDateTime = attendance.getAttendanceDateTime(nickName, editDate);
            AttendanceStatus newAttendanceStatus = attendance.getAttendanceStatus(nickName, editDate);

            outputView.printEditAttendanceMessage(oldAttendanceDateTime, oldAttendanceStatus, newAttendanceDateTime, newAttendanceStatus);
        } else if (option.equals(MenuOption.CREW_ATTENDANCE_CHECK.getCommand())) {
            // 3. 크루별 출석 기록 확인
            String nickName = inputView.readNickname();
            outputView.printCrewAttendanceHeader(nickName);

            List<AttendanceTime> crewAttendances = attendance.getAttendanceTimes(nickName);

            for (AttendanceTime crewAttendance : crewAttendances) {
                outputView.printCheckAttendanceMessage(crewAttendance.getAttendanceDateTime(),
                        crewAttendance.getAttendanceStatus());
            }

            Map<AttendanceStatus, Integer> attendStatuses = attendance.countAttendanceStatus(nickName);
            outputView.printCrewStatuses(attendStatuses);
        } 


    }
}
