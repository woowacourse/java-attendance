package presentation;

import domain.Crew;
import domain.CrewGroup;
import domain.attendance.Attendance;
import domain.attendance.AttendanceState;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import presentation.view.FileInputView;
import presentation.view.InputView;
import presentation.view.OutputView;
import service.AttendanceService;
import util.DateTimeUtil;

public class AttendanceController {
    private final FileInputView fileInputView;
    private final AttendanceService attendanceService;

    public AttendanceController(FileInputView fileInputView, AttendanceService attendanceService) {
        this.fileInputView = fileInputView;
        this.attendanceService = attendanceService;
    }

    public void run() {
        Map<String, List<String>> attendanceFileInfo = fileInputView.getFileInput();
        Map<String, List<LocalDateTime>> crewInitAttendanceDates = InputParser.getFileAttendanceInfo(
                attendanceFileInfo);

        CrewGroup crewGroup = attendanceService.createCrewGroup(crewInitAttendanceDates);

        repeatCommand(crewGroup);
    }

    private boolean repeatCommand(CrewGroup crewGroup) {
        while (true) {
            if (controlCommand(crewGroup)) {
                return true;
            }
        }
    }

    private boolean controlCommand(CrewGroup crewGroup) {
        try {
            String command = InputView.inputCommand();
            InputValidator.commandValidate(command);
            if (command.equals("1")) {
                // 출석 확인
                String nickname = InputView.inputNickname();
                String textAttendanceTime = InputView.inputAttendanceTime();
                LocalDateTime attendanceDateTime = DateTimeUtil.convertStringToLocalDateTime(LocalDate.now(),
                        textAttendanceTime);

                Crew crew = crewGroup.findCrew(nickname);
                Attendance attendance = crew.getAttendance();
                AttendanceState attendanceState = attendance.attend(attendanceDateTime);

                OutputView.printAttend(DateTimeUtil.convertLocalDateTimeToString(attendanceDateTime), attendanceState);
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
