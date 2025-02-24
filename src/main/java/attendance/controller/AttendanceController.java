package attendance.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import attendance.domain.*;
import attendance.dto.*;
import attendance.service.CrewAttendanceService;
import attendance.view.FileLineReader;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceController {

    private static final String attendanceFilePath = "src/main/resources/";
    private static final String attendanceFileName = "attendances.csv";
    private final InputView inputView;
    private final OutputView outputView;
    private final CrewAttendances crewAttendances;
    private final CrewAttendanceService crewAttendanceService;
    private final Map<String, Runnable> operationMapper = Map.of(
            "1", this::confirmAttendance,
            "2", this::modifyAttendance,
            "3", this::checkCrewAttendances,
            "4", this::checkExpulsionCrews,
            "Q", () -> System.exit(0));

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.crewAttendances = new CrewAttendances();
        List<String> firstSkippedLines = readAttendanceFileLinesWithoutFirstLine();
        crewAttendances.initializeCrewAttendances(firstSkippedLines);
        this.crewAttendanceService = new CrewAttendanceService(crewAttendances);
    }

    private List<String> readAttendanceFileLinesWithoutFirstLine() {
        FileLineReader fileLineReader = new FileLineReader();
        List<String> lines = fileLineReader.readAllLines(attendanceFilePath, attendanceFileName);
        return lines.stream()
                .skip(1L)
                .toList();
    }

    public void run() {
        while(true) {
            try {
                branchByOperationCommand();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void branchByOperationCommand() {
        String operationCommand = inputView.readOperationCommand().toUpperCase();
        try {
            operationMapper.get(operationCommand).run();
        } catch (NullPointerException exception) {
            throw new IllegalArgumentException("제공되지 않는 기능입니다.");
        }
    }

    private void confirmAttendance() {
        Crew crew = crewAttendanceService.findRegisteredCrew(inputView.readCrewNickname());
        LocalTime attendanceTime = inputView.readAttendanceTime();
        try {
            ConfirmAttendanceDto confirmAttendanceDto = crewAttendanceService.saveCrewTodayAttendance(crew, attendanceTime);
            Attendance attendance = confirmAttendanceDto.attendance();
            AttendanceStatus attendanceStatus = attendance.calculateStatus();
            outputView.printAttendance(attendance.getAttendanceDateTime(), attendanceStatus.getText());
        } catch (IllegalStateException exception) {
            outputView.printUsingAttendanceModification();
        }
    }

    private void modifyAttendance() {
        Crew crew = crewAttendanceService.findRegisteredCrew(inputView.readModificationCrewNickname());
        LocalDate modificationDate = inputView.readModificationDay(LocalDate.now());
        LocalTime modificationTime = inputView.readModificationTime();
        ChangeAttendanceDto changeAttendanceDto = crewAttendanceService.changeAttendanceTime(crew, modificationDate, modificationTime);
        Attendance originAttendance = changeAttendanceDto.originAttendance();
        Attendance newAttendance = changeAttendanceDto.newAttendance();
        AttendanceStatus originAttendanceStatus = originAttendance.calculateStatus();
        AttendanceStatus newAttendanceStatus = newAttendance.calculateStatus();
        outputView.printModificationResult(originAttendance.getAttendanceDateTime(), originAttendanceStatus.getText(),
                newAttendance.getAttendanceDateTime(), newAttendanceStatus.getText());
    }

    private void checkCrewAttendances() {
        Crew crew = crewAttendanceService.findRegisteredCrew(inputView.readCrewNickname());
        CheckCrewAttendanceRecordsDto checkCrewAttendanceRecordsDto =
                crewAttendanceService.checkCrewAttendanceRecords(crew);
        outputView.printAttendances(crew, checkCrewAttendanceRecordsDto.attendanceDateTimes(),
                checkCrewAttendanceRecordsDto.attendanceStatuses());
        Map<String, Integer> statusCount = crewAttendances.getAttendancesByCrew(crew).calculateStatusCount();
        outputView.printStatusCounts(statusCount);
        ExpulsionStatus expulsionStatus = crewAttendances.getAttendancesByCrew(crew).calculateExpulsionStatus();
        outputView.printExpulsionStatus(expulsionStatus.getText());
    }

    private void checkExpulsionCrews() {
        Map<String, AttendanceHistoryDto> attendanceHistories =
                crewAttendanceService.calculateAllCrewAttendanceHistories();
        outputView.printExpulsionCrews(attendanceHistories);
    }

}
