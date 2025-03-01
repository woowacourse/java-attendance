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
            OutPutView.requestNickName();
            String nickName = InputView.input();
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
    private void functionAttendanceModify(AttendanceBook attendanceBook){
        try{
            OutPutView.requestModifyNickName();
            String nickName = InputView.input();
            OutPutView.requestModifyDate();
            int modifyDate = InputView.validateDateFormat(InputView.input());
            Student student = attendanceBook.findStudentByNickName(nickName);
            String beforeRecord = AttendanceRecordFormatter.attendanceRecordFormatter(student, LocalDate.of(2024,12,modifyDate));
            OutPutView.requestModifyTime();
            LocalTime modifyTime = InputView.validateTimeFormat(InputView.input());
            student.modifyAttendanceRecord(modifyDate, modifyTime);
            String afterRecord = AttendanceRecordFormatter.attendanceRecordFormatter(student, LocalDate.of(2024,12,modifyDate));
            OutPutView.displayModifyAttendanceRecord(beforeRecord, afterRecord);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            functionAttendanceModify(attendanceBook);
        }
    }
}
