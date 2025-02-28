package view.input;

import static view.input.InputPrompt.GUIDE_FUNCTION_INPUT;
import static view.input.InputPrompt.GUIDE_INPUT_DATE_TO_MODIFY_ATTENDANCE;
import static view.input.InputPrompt.GUIDE_INPUT_NAME_TO_CHECK_ATTENDANCE;
import static view.input.InputPrompt.GUIDE_INPUT_NAME_TO_CHECK_ATTENDANCE_RECORD;
import static view.input.InputPrompt.GUIDE_INPUT_NAME_TO_MODIFY_ATTENDANCE;
import static view.input.InputPrompt.GUIDE_INPUT_TIME_TO_CHECK_ATTENDANCE;
import static view.input.InputPrompt.GUIDE_INPUT_TIME_TO_MODIFY_ATTENDANCE;

import java.util.Scanner;
import view.output.OutputView;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String askNameToCheckAttendance() {
        System.out.println(GUIDE_INPUT_NAME_TO_CHECK_ATTENDANCE.getFormat());
        return getUserInput();
    }

    public String askTimeToCheckAttendance() {
        System.out.println(GUIDE_INPUT_TIME_TO_CHECK_ATTENDANCE.getFormat());
        return getUserInput();
    }

    public String askNameToModifyAttendance() {
        System.out.println(GUIDE_INPUT_NAME_TO_MODIFY_ATTENDANCE.getFormat());
        return getUserInput();
    }

    public String askDateToModifyAttendance() {
        System.out.println(GUIDE_INPUT_DATE_TO_MODIFY_ATTENDANCE.getFormat());
        return getUserInput();
    }

    public String askTimeToModifyAttendance() {
        System.out.println(GUIDE_INPUT_TIME_TO_MODIFY_ATTENDANCE.getFormat());
        return getUserInput();
    }

    public String askNameToCheckAttendanceRecord() {
        System.out.println(GUIDE_INPUT_NAME_TO_CHECK_ATTENDANCE_RECORD.getFormat());
        return getUserInput();
    }

    public String askFunctionSelection() {
        System.out.println(GUIDE_FUNCTION_INPUT.getFormat());
        return getUserInput();
    }

    public String getUserInput() {
        String response = scanner.nextLine();
        OutputView.displaySpacing();
        return response;
    }
}