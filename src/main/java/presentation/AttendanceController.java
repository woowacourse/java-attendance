package presentation;

import domain.Crew;
import domain.CrewGroup;
import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceState;
import domain.attendance.AttendanceWarning;
import dto.ResponseAttendanceEditStateDto;
import dto.ResponseCrewAttendanceStateDto;
import dto.ResponseWarningCrewDto;
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
    private final static String ATTEND_COMMAND = "1";
    private final static String EDIT_COMMAND = "2";
    private final static String CREW_QUERY_COMMAND = "3";
    private final static String CREWS_WARNING_COMMAND = "4";
    private final static String EXIT_COMMAND = "Q";

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

    private void repeatCommand(CrewGroup crewGroup) {
        while (true) {
            if (controlCommand(crewGroup)) {
                break;
            }
        }
    }

    private boolean controlCommand(CrewGroup crewGroup) {
        try {
            String command = InputView.inputCommand();
            InputValidator.commandValidate(command);
            if (command.equals(ATTEND_COMMAND)) {
                attendCommand(crewGroup);
            }
            if (command.equals(EDIT_COMMAND)) {
                attendanceEditCommand(crewGroup);
            }
            if (command.equals(CREW_QUERY_COMMAND)) {
                crewQueryCommand(crewGroup);
            }
            if (command.equals(CREWS_WARNING_COMMAND)) {
                attendanceWarningCommand(crewGroup);
            }
            return command.equalsIgnoreCase(EXIT_COMMAND);
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
            return false;
        }
    }

    private void attendanceWarningCommand(CrewGroup crewGroup) {
        List<Crew> warningCrews = crewGroup.sortedAttendanceWarning();
        List<ResponseWarningCrewDto> warningCrewDtos = warningCrews.stream()
                .map(warningCrew -> new ResponseWarningCrewDto(
                        warningCrew.getName(),
                        warningCrew.getAttendance().countAbsence(),
                        warningCrew.getAttendance().countTardy(),
                        AttendanceWarning.determineAttendanceWarning(
                                warningCrew.getAttendance().countAbsenceIncludingTardy())))
                .toList();
        OutputView.printAttendanceWarningCrews(warningCrewDtos);
    }

    private void crewQueryCommand(CrewGroup crewGroup) {
        String nickname = InputView.inputNickname();
        Crew findCrew = crewGroup.findCrew(nickname);

        OutputView.printAttendanceStatusCrew(
                ResponseCrewAttendanceStateDto.of(findCrew.getName(), findCrew.getAttendance()));
    }

    private void attendanceEditCommand(CrewGroup crewGroup) {
        String nickname = InputView.inputNickname();
        String textAttendanceDay = InputView.inputAttendanceDay();
        String textAttendanceTime = InputView.inputAttendanceTime();

        int attendanceDay = InputParser.parseInt(textAttendanceDay);
        Crew crew = crewGroup.findCrew(nickname);

        LocalDate findLocalDate = LocalDate.of(LocalDate.now().getYear(),
                LocalDateTime.now().getMonthValue(), attendanceDay);

        // beforeDate
        Attendance attendance = crew.getAttendance();
        AttendanceDate attendanceDate = attendance.findAttendanceDate(findLocalDate);
        String beforeEditDate = DateTimeUtil.convertLocalDateTimeToString(
                attendanceDate.checkAttendanceTime());
        AttendanceState beforeState = attendanceDate.calculateAttendanceState();

        // afterDate
        LocalDateTime afterEditDateTime = DateTimeUtil.convertStringToLocalDateTime(findLocalDate,
                textAttendanceTime);
        String afterEditTime = DateTimeUtil.convertLocalDateTimeToTimeString(afterEditDateTime);
        attendanceDate.editDateTime(afterEditDateTime);
        AttendanceState afterState = attendanceDate.calculateAttendanceState();

        OutputView.printEditState(
                new ResponseAttendanceEditStateDto(beforeEditDate, beforeState, afterEditTime, afterState));
    }

    private void attendCommand(CrewGroup crewGroup) {
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
}
