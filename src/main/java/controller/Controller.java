package controller;

import constant.MenuOption;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.AttendanceBook;
import model.Student;
import util.AttendanceRecordFormatter;
import util.FileInformationProvider;
import view.InputView;
import view.OutPutView;

public class Controller {
    private static final LocalDate TODAY = LocalDate.of(2024, 12, 13);

    public void run() throws IOException {
        AttendanceBook attendanceBook = new AttendanceBook(FileInformationProvider.loadStudentAttendance());
        attendanceBook.updateNonExistentAttendanceRecords(TODAY);
        while (true) {
            if (handleMenuChoice(attendanceBook)) {
                return;
            }
        }
    }

    private boolean handleMenuChoice(AttendanceBook attendanceBook) {
        OutPutView.displayAttendanceMenu(TODAY);
        MenuOption menuOption = chooseMenuOption();
        if (menuOption.equals(MenuOption.ATTENDANCE_REGISTER)) {
            registerAttendance(attendanceBook);
        }
        if (menuOption.equals(MenuOption.ATTENDANCE_MODIFY)) {
            modifyAttendance(attendanceBook);
        }
        if (menuOption.equals(MenuOption.CREW_ATTENDANCE_CHECK)) {
            checkCrewAttendance(attendanceBook);
        }
        if (menuOption.equals(MenuOption.EXPULSION_RISK)) {
            checkExpulsionRisk(attendanceBook);
        }
        return menuOption.equals(MenuOption.QUIT);
    }

    private MenuOption chooseMenuOption() {
        try {
            return InputView.inputChooseFunctionOption();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return chooseMenuOption();
        }
    }

    private void registerAttendance(AttendanceBook attendanceBook) {
        try {
            String nickName = requestNickName();
            LocalTime attendanceTime = requestAttendanceTime();
            Student student = attendanceBook.findStudentByNickName(nickName);
            student.registerAttendanceRecord(TODAY, attendanceTime);
            OutPutView.displayRegisterAttendanceRecord(AttendanceRecordFormatter.attendanceRecordFormatter(
                    student, TODAY));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            registerAttendance(attendanceBook);
        }
    }

    private static LocalTime requestAttendanceTime() {
        OutPutView.requestAttendanceTime();
        return InputView.inputAttendanceTime();
    }

    private void modifyAttendance(AttendanceBook attendanceBook) {
        try {
            String nickName = requestModifyNickName();
            int modifyDate = requestModifyDate();
            Student student = attendanceBook.findStudentByNickName(nickName);
            modifyAttendanceRecord(student, modifyDate);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            modifyAttendance(attendanceBook);
        }
    }

    private void checkCrewAttendance(AttendanceBook attendanceBook) {
        try {
            String nickName = requestNickName();
            Student student = attendanceBook.findStudentByNickName(nickName);
            OutPutView.displayTotalAttendanceRecord(student);
            OutPutView.displayTotalAttendanceCount(student);
            OutPutView.displayCounselingCandidate(student);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            checkCrewAttendance(attendanceBook);
        }
    }

    private void checkExpulsionRisk(AttendanceBook attendanceBook) {
        List<Student> expulsionRiskStudents = attendanceBook.findExpulsionRiskStudents();
        OutPutView.displayExpulsionRiskStudents(expulsionRiskStudents);
    }

    private void modifyAttendanceRecord(Student student, int modifyDate) {
        LocalDate date = LocalDate.of(2024, 12, modifyDate);
        String beforeRecord = AttendanceRecordFormatter.attendanceRecordFormatter(student, date);

        LocalTime modifyTime = requestModifyTime();
        student.modifyAttendanceRecord(modifyDate, modifyTime);

        String afterRecord = AttendanceRecordFormatter.attendanceRecordFormatter(student, date);
        OutPutView.displayModifyAttendanceRecord(beforeRecord, afterRecord);
    }

    private static String requestNickName() {
        OutPutView.requestNickName();
        return InputView.input();
    }

    private String requestModifyNickName() {
        OutPutView.requestModifyNickName();
        return InputView.input();
    }

    private int requestModifyDate() {
        OutPutView.requestModifyDate();
        return InputView.validateDateFormat(InputView.input());
    }

    private LocalTime requestModifyTime() {
        OutPutView.requestModifyTime();
        return InputView.validateTimeFormat(InputView.input());
    }
}
