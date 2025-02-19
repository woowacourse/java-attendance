package presentation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import presentation.view.FileInputView;
import presentation.view.InputView;
import presentation.view.OutputView;
import util.DateTimeUtil;

public class AttendanceController {
    private final FileInputView fileInputView;

    public AttendanceController(FileInputView fileInputView) {
        this.fileInputView = fileInputView;
    }

    public void run() {
        Map<String, List<String>> crewTextInitAttendanceDates = fileInputView.getFileInput();
        Map<String, List<LocalDateTime>> crewInitAttendanceDates = new HashMap<>();

         for(String key :crewTextInitAttendanceDates.keySet()){

            crewInitAttendanceDates.put(key,
                    crewTextInitAttendanceDates.get(key).stream()
                    .map(DateTimeUtil::convertStringToLocalDateTime)
                    .toList());
        }
         
        while (true) {
            if (controlCommand()) {
                break;
            }
        }
    }

    private boolean controlCommand() {
        try {
            String command = InputView.inputCommand();
            InputValidator.commandValidate(command);
            if (command.equals("1")) {
                // 출석 확인
                String nickname = InputView.inputNickname();
                String textAttendanceTime = InputView.inputAttendanceTime();
                LocalDateTime attendanceDateTime = DateTimeUtil.convertStringToLocalDateTime(LocalDate.now(),
                        textAttendanceTime);
            }
            if (command.equals("2")) {
                // 출석 수정
                String nickname = InputView.inputNickname();
                String textAttendanceDay = InputView.inputAttendanceDay();
                String textAttendanceTime = InputView.inputAttendanceTime();

            }
            if (command.equals("3")) {
                // 크루 별 출석 기록 확인
                String nickname = InputView.inputNickname();

            }
            if (command.equals("4")) {
                // 제적 위험자 확인

            }
            if (command.equals("Q") || command.equals("q")) {
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
            return false;
        }
    }
}
