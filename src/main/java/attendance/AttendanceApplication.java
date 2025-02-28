package attendance;

import attendance.controller.AttendanceController;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;

public class AttendanceApplication {

    public static void main(String[] args) {
        LocalDate baseDate = LocalDate.of(2024, 12, 13);
        AttendanceController attendanceController = new AttendanceController(new InputView(), new OutputView());
        attendanceController.start(baseDate);
    }
}

// TODO: 예외 메시지 처리, 예외 상황 검토, 요구사항 검토, 효율적인 자료 구조 고민