package presentation;

import domain.Crew;
import domain.CrewGroup;
import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceState;
import domain.attendance.AttendanceWarning;
import dto.AttendanceEditInputDto;
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
import service.CrewService;
import util.DateTimeUtil;

public class AttendanceController {
    private final static String ATTEND_COMMAND = "1";
    private final static String EDIT_COMMAND = "2";
    private final static String CREW_QUERY_COMMAND = "3";
    private final static String CREWS_WARNING_COMMAND = "4";
    private final static String EXIT_COMMAND = "Q";

    private final FileInputView fileInputView;
    private final CrewService attendanceService;

    public AttendanceController(FileInputView fileInputView, CrewService attendanceService) {
        this.fileInputView = fileInputView;
        this.attendanceService = attendanceService;
    }

    public void run() {
        CrewGroup crewGroup = initCrewData();
        repeatCommand(crewGroup);
    }

    private CrewGroup initCrewData() {
        Map<String, List<String>> attendanceFileInfo = fileInputView.getFileInput();
        Map<String, List<LocalDateTime>> crewInitAttendanceDates = InputParser.getFileAttendanceInfo(
                attendanceFileInfo);

        return attendanceService.createCrewGroup(crewInitAttendanceDates);
    }

    private void repeatCommand(CrewGroup crewGroup) {
        while (true) {
            if (controlCommand(crewGroup)) {
                return;
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
                        warningCrew.countAbsence(),
                        warningCrew.countTardy(),
                        AttendanceWarning.determineAttendanceWarning(
                                warningCrew.countAbsenceIncludingTardy())))
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
        AttendanceEditInputDto editInput = getEditInput();
        Crew crew = crewGroup.findCrew(editInput.nickname());

        ResponseAttendanceEditStateDto beforeEditState = getBeforeEditState(crew, editInput.attendanceDay());
        ResponseAttendanceEditStateDto afterEditState = getAfterEditState(crew, editInput.attendanceDay(),
                editInput.attendanceTime());

        applyAttendanceEdit(beforeEditState, afterEditState);
    }

    private AttendanceEditInputDto getEditInput() {
        String nickname = InputView.inputNickname();
        String textAttendanceDay = InputView.inputAttendanceDay();
        String textAttendanceTime = InputView.inputAttendanceTime();
        int attendanceDay = InputParser.parseInt(textAttendanceDay);

        return new AttendanceEditInputDto(nickname, attendanceDay, textAttendanceTime);
    }

    private ResponseAttendanceEditStateDto getBeforeEditState(Crew crew, int attendanceDay) {
        LocalDate findLocalDate = LocalDate.of(LocalDate.now().getYear(), LocalDateTime.now().getMonthValue(),
                attendanceDay);
        AttendanceDate attendanceDate = crew.findAttendanceDate(findLocalDate);

        String beforeEditDate = DateTimeUtil.convertLocalDateTimeToString(attendanceDate.checkAttendanceTime());
        AttendanceState beforeState = attendanceDate.calculateAttendanceState();

        return new ResponseAttendanceEditStateDto(beforeEditDate, beforeState, null, null);
    }

    private ResponseAttendanceEditStateDto getAfterEditState(Crew crew, int attendanceDay, String textAttendanceTime) {
        LocalDate findLocalDate = LocalDate.of(LocalDate.now().getYear(), LocalDateTime.now().getMonthValue(),
                attendanceDay);
        AttendanceDate attendanceDate = crew.findAttendanceDate(findLocalDate);

        LocalDateTime afterEditDateTime = DateTimeUtil.convertStringToLocalDateTime(findLocalDate, textAttendanceTime);
        String afterEditTime = DateTimeUtil.convertLocalDateTimeToTimeString(afterEditDateTime);

        attendanceDate.editDateTime(afterEditDateTime);
        AttendanceState afterState = attendanceDate.calculateAttendanceState();

        return new ResponseAttendanceEditStateDto(null, null, afterEditTime, afterState);
    }

    private void applyAttendanceEdit(ResponseAttendanceEditStateDto before, ResponseAttendanceEditStateDto after) {
        OutputView.printEditState(new ResponseAttendanceEditStateDto(
                before.beforeDateTime(), before.beforeState(), after.afterDateTime(), after.afterState()
        ));
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
