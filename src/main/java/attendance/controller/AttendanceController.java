package attendance.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import attendance.domain.*;
import attendance.dto.*;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final CrewAttendances crewAttendances;
    private final Map<OperationCommand, Runnable> operationMapper = Map.of(
            OperationCommand.ATTENDANCE_CONFIRMATION, this::confirmAttendance
            , OperationCommand.ATTENDANCE_MODIFICATION, this::modifyAttendance
            , OperationCommand.CREW_ATTENDANCES_CHECK, this::checkCrewAttendances
            , OperationCommand.EXPULSION_CHECK, this::checkExpulsionCrews
            , OperationCommand.QUIT, () -> System.exit(0));

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.crewAttendances = new CrewAttendances();
    }

    public void run() {
        while(true) {
            branchByOperationCommand();
        }
    }

    private void branchByOperationCommand() {
        try {
            outputView.printOperations();
            OperationCommand operationCommand = inputView.readOperationCommand();
            operationMapper.get(operationCommand).run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void confirmAttendance() {
        Crew crew = crewAttendances.createRegisteredCrew(inputView.readCrewNickname());
        LocalTime attendanceTime = inputView.readAttendanceTime();
        try {
            ConfirmAttendanceDto confirmAttendanceDto = crewAttendances.saveTodayAttendance(crew, attendanceTime);
            Attendance attendance = confirmAttendanceDto.attendance();
            AttendanceStatus attendanceStatus = AttendanceStatus.findByAttendance(attendance);
            outputView.printAttendance(attendance.getAttendanceDateTime(), attendanceStatus.getText());
        } catch (IllegalStateException exception) {
            outputView.printUsingAttendanceModification();
        }
    }

    private void modifyAttendance() {
        Crew crew = crewAttendances.createRegisteredCrew(inputView.readModificationCrewNickname());
        LocalDate modificationDate = inputView.readModificationDay(LocalDate.now());
        LocalTime modificationTime = inputView.readModificationTime();
        ChangeAttendanceDto changeAttendanceDto = crewAttendances.changeAttendanceTime(crew, modificationDate, modificationTime);
        Attendance originAttendance = changeAttendanceDto.originAttendance();
        Attendance newAttendance = changeAttendanceDto.newAttendance();
        AttendanceStatus originAttendanceStatus = AttendanceStatus.findByAttendance(originAttendance);
        AttendanceStatus newAttendanceStatus = AttendanceStatus.findByAttendance(newAttendance);
        outputView.printModificationResult(originAttendance.getAttendanceDateTime(), originAttendanceStatus.getText(),
                newAttendance.getAttendanceDateTime(), newAttendanceStatus.getText());
    }

    private void checkCrewAttendances() {
        Crew crew = crewAttendances.createRegisteredCrew(inputView.readCrewNickname());
        CheckCrewAttendanceRecordsDto checkCrewAttendanceRecordsDto =
                crewAttendances.checkCrewAttendanceRecords(crew);
        outputView.printAttendances(crew, checkCrewAttendanceRecordsDto.attendanceDateTimes(),
                checkCrewAttendanceRecordsDto.attendanceStatuses());
        CheckAttendanceStatusDto checkAttendanceStatusDto =
                crewAttendances.checkAttendanceStatus(crew);
        outputView.printStatusCounts(checkAttendanceStatusDto.statusCount());
        CheckExpulsionStatusDto checkExpulsionStatusDto = crewAttendances.checkExpulsionStatus(crew);
        outputView.printExpulsionStatus(checkExpulsionStatusDto.expulsionStatus().getText());
    }

    private void checkExpulsionCrews() {
        Map<String, AttendanceHistoryDto> attendanceHistories = crewAttendances.calculateAllCrewAttendanceHistories();
        outputView.printExpulsionCrews(attendanceHistories);
    }

}
