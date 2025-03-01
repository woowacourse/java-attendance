package controller;

import java.util.Map;
import model.Students;
import view.InputView;

public class AttendanceManagementController {
    public void attendanceManagementStart() {

        String userInput = "";

        while (!userInput.equals(MenuOption.QUIT.getOption())){
            userInput = InputView.getUserWantMenuUntilValidate();
            if (userInput.equals(MenuOption.ATTENDANCE_CHECK.getOption())) {

            }
            if (userInput.equals(MenuOption.ATTENDANCE_MODIFY.getOption())) {
            }
            if (userInput.equals(MenuOption.STUDENT_RECORD_CHECK.getOption())) {
            }
            if (userInput.equals(MenuOption.DISMISSAL_SUBJECT_CHECK.getOption())) {
            }
        }
    }



}
