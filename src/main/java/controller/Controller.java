package controller;

import constant.MenuOption;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import model.AttendanceBook;
import model.Student;
import util.AttendanceRecordFormatter;
import util.FileInformationProvider;
import view.InputView;
import view.OutPutView;

public class Controller {
    private static final LocalDate TODAY = LocalDate.of(2024,12,13);


    public void run() throws IOException {
        AttendanceBook attendanceBook = new AttendanceBook(FileInformationProvider.loadStudentAttendance());
        OutPutView.displayAttendanceMenu(TODAY);
        MenuOption menuOption = chooseMenuOption();
        if (menuOption.equals(MenuOption.ATTENDANCE_REGISTER)){
            functionAttendanceRegister(attendanceBook);
        }
        if (menuOption.equals(MenuOption.ATTENDANCE_MODIFY)){
            functionAttendanceModify(attendanceBook);
        }
        if (menuOption.equals(MenuOption.CREW_ATTENDANCE_CHECK)){

        }
        if (menuOption.equals(MenuOption.EXPULSION_RISK)){

        }
        if (menuOption.equals(MenuOption.QUIT)){
            return;
        }
    }
    private MenuOption chooseMenuOption(){
        try {
            return InputView.inputChooseFunctionOption();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return chooseMenuOption();
        }
    }
    private void functionAttendanceRegister(AttendanceBook attendanceBook){
        try{
            String nickName = requestNickName();
            LocalTime attendanceTime = InputView.inputAttendanceTime();
            Student student = attendanceBook.findStudentByNickName(nickName);
            student.registerAttendanceRecord(TODAY, attendanceTime);
            OutPutView.displayRegisterAttendanceRecord(AttendanceRecordFormatter.attendanceRecordFormatter(
                    student, TODAY));
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            functionAttendanceRegister(attendanceBook);
        }
    }

    private static String requestNickName() {
        OutPutView.requestNickName();
        return InputView.input();
    }

    private void functionAttendanceModify(AttendanceBook attendanceBook) {
        try {
            String nickName = requestModifyNickName();
            int modifyDate = requestModifyDate();
            Student student = attendanceBook.findStudentByNickName(nickName);
            modifyAttendanceRecord(student, modifyDate);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            functionAttendanceModify(attendanceBook); // 재귀 호출
        }
    }

    private void modifyAttendanceRecord(Student student, int modifyDate) {
        LocalDate date = LocalDate.of(2024, 12, modifyDate);
        String beforeRecord = AttendanceRecordFormatter.attendanceRecordFormatter(student, date);

        LocalTime modifyTime = requestModifyTime();
        student.modifyAttendanceRecord(modifyDate, modifyTime);

        String afterRecord = AttendanceRecordFormatter.attendanceRecordFormatter(student, date);
        OutPutView.displayModifyAttendanceRecord(beforeRecord, afterRecord);
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
