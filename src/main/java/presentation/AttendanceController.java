package presentation;

import domain.Crew;
import domain.CrewGroup;
import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceState;
import dto.ResponseAttendanceEditStateDto;
import dto.ResponseCrewAttendanceStateDto;
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

                OutputView.printAttendanceState(
                        DateTimeUtil.convertLocalDateToString(attendanceDateTime.toLocalDate()),
                        DateTimeUtil.convertLocalDateTimeToTimeString(attendanceDateTime),
                        attendanceState);
            }
            if (command.equals("2")) {
                // 출석 수정
                String nickname = InputView.inputNickname();
                String textAttendanceDay = InputView.inputAttendanceDay();
                String textAttendanceTime = InputView.inputAttendanceTime();

                int attendanceDay = InputParser.parseInt(textAttendanceDay);

                Crew crew = crewGroup.findCrew(nickname);

                LocalDate findLocalDate = LocalDate.of(LocalDate.now().getYear(),
                        LocalDateTime.now().getMonthValue(), attendanceDay);

                // before
                Attendance attendance = crew.getAttendance();
                AttendanceDate attendanceDate = attendance.findAttendanceDate(findLocalDate);
                String beforeEditDate = DateTimeUtil.convertLocalDateTimeToString(
                        attendanceDate.checkAttendanceTime());
                AttendanceState beforeState = attendanceDate.calculateAttendanceState();

                // after
                LocalDateTime afterEditDateTime = DateTimeUtil.convertStringToLocalDateTime(findLocalDate,
                        textAttendanceTime);
                String afterEditTime = DateTimeUtil.convertLocalDateTimeToTimeString(afterEditDateTime);

                attendanceDate.editDateTime(afterEditDateTime);
                AttendanceState afterState = attendanceDate.calculateAttendanceState();

                OutputView.printEditState(
                        new ResponseAttendanceEditStateDto(beforeEditDate, beforeState, afterEditTime, afterState));
            }
            if (command.equals("3")) {
                String nickname = InputView.inputNickname();
                Crew findCrew = crewGroup.findCrew(nickname);

                OutputView.printAttendanceStatusCrew(
                        ResponseCrewAttendanceStateDto.of(findCrew.getName(), findCrew.getAttendance()));
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
