package attendance.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import attendance.domain.*;
import attendance.dto.AttendanceHistoryDto;
import attendance.dto.ConfirmAttendanceDto;
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
            OperationCommand operationCommand = requestOperationCommand();
            operationMapper.get(operationCommand).run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private OperationCommand requestOperationCommand() {
        outputView.printOperations();
        OperationCommand operationCommand = inputView.readOperationCommand();
        return operationCommand;
    }

    private void confirmAttendance() {
        Crew crew = createCrewByNickname(inputView.readCrewNickname());
        LocalTime attendanceTime = inputView.readAttendanceTime();
        try {
            ConfirmAttendanceDto confirmAttendanceDto = crewAttendances.saveTodayAttendance(crew, attendanceTime);
            Attendance attendance = confirmAttendanceDto.attendance();
            AttendanceStatus attendanceStatus = AttendanceStatus.findByAttendanceDateAndTime(
                    attendance.getAttendanceDate(), attendance.getAttendanceTime());
            outputView.printAttendance(attendance.getAttendanceDateTime(), attendanceStatus.getText());
        } catch (IllegalStateException exception) {
            outputView.printUsingAttendanceModification();
        }
    }

    private void modifyAttendance() {
        Crew crew = createCrewByNickname(inputView.readModificationCrewNickname());
        LocalDate modificationDate = inputView.readModificationDay(LocalDate.now());
        LocalTime modificationTime = inputView.readModificationTime();
        Attendances attendances = crewAttendances.get(crew);
        exchangeOldAttendanceToNew(attendances, modificationDate, modificationTime);
    }

    private void exchangeOldAttendanceToNew(Attendances attendances, LocalDate modificationDate, LocalTime modificationTime) {
        Attendance originAttendance = attendances.findAttendanceByLocalDate(modificationDate);
        Attendance newAttendance = originAttendance.changeAttendanceTime(modificationTime);
        attendances.remove(originAttendance);
        attendances.addAttendance(newAttendance);
        String originAttendanceStatus = AttendanceStatus.findByAttendanceDateAndTime(
                originAttendance.getAttendanceDate(), originAttendance.getAttendanceTime()).getText();
        String newAttendanceStatus = AttendanceStatus.findByAttendanceDateAndTime(new AttendanceDate(modificationDate),
                new AttendanceTime(modificationTime)).getText();
        outputView.printModificationResult(originAttendance.getAttendanceDateTime(), originAttendanceStatus,
                newAttendance.getAttendanceDateTime(), newAttendanceStatus);
    }

    private void checkCrewAttendances() {
        Crew crew = createCrewByNickname(inputView.readCrewNickname());
        Attendances attendances = crewAttendances.get(crew);
        checkAttendanceRecords(attendances, crew);
        checkAttendanceStatus(attendances);
        checkExpulsionStatus(attendances);
    }

    private void checkAttendanceRecords(Attendances attendances, Crew crew) {
        List<LocalDateTime> attendanceTimes = attendances.getAttendances().stream()
                .map(Attendance::getAttendanceDateTime)
                .toList();
        List<AttendanceStatus> attendanceStatus = attendanceTimes.stream()
                .map(dateTime -> AttendanceStatus.findByAttendanceDateAndTime(new AttendanceDate(dateTime.toLocalDate()),
                        new AttendanceTime(dateTime.toLocalTime())))
                .toList();
        List<String> attendanceStatusTexts = attendanceStatus.stream()
                .map(AttendanceStatus::getText)
                .toList();
        outputView.printAttendances(crew.getNickname(), attendanceTimes, attendanceStatusTexts);
    }

    private void checkAttendanceStatus(Attendances attendances) {
        Map<String, Integer> statusCount = attendances.calculateStatusCount();
        outputView.printStatusCounts(statusCount);
    }

    private void checkExpulsionStatus(Attendances attendances) {
        ExpulsionStatus expulsionStatus = attendances.calculateExpulsionStatus();
        outputView.printExpulsionStatus(expulsionStatus.getText());
    }

    private void checkExpulsionCrews() {
        Map<String, AttendanceHistoryDto> attendanceHistories = new HashMap<>();
        for (Map.Entry<Crew, Attendances> entry : crewAttendances.entrySet()) {
            Attendances attendances = entry.getValue();
            Map<String, Integer> attendanceStatusCounts = attendances.calculateStatusCount();
            ExpulsionStatus expulsionStatus = attendances.calculateExpulsionStatus();
            AttendanceHistoryDto attendanceHistoryDto = new AttendanceHistoryDto(attendanceStatusCounts.get(AttendanceStatus.ABSENT.getText()),
                    attendanceStatusCounts.get(AttendanceStatus.LATE.getText()), expulsionStatus.getText());
            attendanceHistories.put(entry.getKey().getNickname(), attendanceHistoryDto);
        }
        outputView.printExpulsionCrews(attendanceHistories);
    }

    private Crew createCrewByNickname(String nickname) {
        Crew crew = new Crew(nickname);
        validateCrewExistence(crew);
        return crew;
    }


    private void validateCrewExistence(final Crew crew) {
        if (!crewAttendances.containsKey(crew)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

}
