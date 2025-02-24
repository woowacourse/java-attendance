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
import presentation.view.AttendanceFileInputView;
import presentation.view.InputView;
import presentation.view.OutputView;
import service.CrewService;
import util.DateTimeUtil;

public class AttendanceController {
    private final AttendanceFileInputView fileInputView;
    private final CrewService attendanceService;

    public AttendanceController(AttendanceFileInputView fileInputView, CrewService attendanceService) {
        this.fileInputView = fileInputView;
        this.attendanceService = attendanceService;
    }

    public void run() {
        CrewGroup crewGroup = initCrewData();
        repeatCommand(crewGroup);
    }

    private CrewGroup initCrewData() {
        Map<String, List<String>> attendanceFileInfo = fileInputView.getAttendanceFileInput();
        Map<String, List<LocalDateTime>> crewInitAttendanceDates = InputParser.getFileAttendanceInfo(
                attendanceFileInfo);

        return attendanceService.createCrewGroup(crewInitAttendanceDates);
    }

    private void repeatCommand(CrewGroup crewGroup) {
        while (!controlCommand(crewGroup).equalsIgnoreCase(AttendanceCommand.EXIT_COMMAND.getCommand())) {
        }
    }

    private String controlCommand(CrewGroup crewGroup) {
        try {
            String command = InputView.inputCommand();
            InputValidator.commandValidate(command);
            if (command.equals(AttendanceCommand.ATTENDANCE_COMMAND.getCommand())) {
                attendCommand(crewGroup);
            }
            if (command.equals(AttendanceCommand.EDIT_COMMAND.getCommand())) {
                attendanceEditCommand(crewGroup);
            }
            if (command.equals(AttendanceCommand.GET_CREW_INFO_COMMAND.getCommand())) {
                getCrewInfoCommand(crewGroup);
            }
            if (command.equals(AttendanceCommand.CREWS_WARNING_COMMAND.getCommand())) {
                attendanceWarningCommand(crewGroup);
            }
            return command;
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
            return AttendanceCommand.NONE_COMMAND.getCommand();
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

    private void getCrewInfoCommand(CrewGroup crewGroup) {
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
