package controller;

import controller.dto.AttendanceRecodeDto;
import controller.dto.AttendanceResultDto;
import controller.dto.PenaltyCrewDto;
import domain.AttendanceStatus;
import domain.CrewAttendance;
import domain.CrewAttendanceRepository;
import domain.MenuOption;
import domain.WorkDate;
import domain.WorkDateTime;
import domain.WorkTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final CrewAttendanceRepository crewAttendanceRepository;

    public AttendanceController(InputView inputView, OutputView outputView,
                                CrewAttendanceRepository crewAttendanceRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.crewAttendanceRepository = crewAttendanceRepository;
    }

    public void run(LocalDate currentDate) {
        boolean isRunning = true;

        while (isRunning) {
            isRunning = processMenu(currentDate);
        }
    }

    private boolean processMenu(LocalDate currentDate) {
        try {
            MenuOption menuOption = inputView.readMenuOption(currentDate);
            executeMenu(menuOption, currentDate);

            return !menuOption.isExit();
        } catch (IllegalArgumentException e) {
            outputView.printMessage(e.getMessage());

            return true;
        }
    }

    private void executeMenu(MenuOption menuOption, LocalDate currentDate) {
        if (menuOption.isCheck()) {
            handleCheckAttendance(currentDate);
            return;
        }

        if (menuOption.isEdit()) {
            handleEditAttendance(currentDate);
            return;
        }

        if (menuOption.isRecord()) {
            handleRecordAttendance();
            return;
        }

        if (menuOption.isRisk()) {
            handleRiskAttendance();
        }
    }

    private void handleCheckAttendance(LocalDate currentDate) {
        CrewAttendance crewAttendance = getCrewAttendance(inputView.readNickName());
        WorkDate currnetWorkDate = WorkDate.from(currentDate);
        WorkDateTime attendance = createAttendance(currnetWorkDate, inputView.readArriveTime());

        crewAttendance.addAttendance(attendance);
        AttendanceStatus attendanceStatus = crewAttendance.calculateAttendanceStatus(attendance.getDate());

        outputView.printArriveResult(attendance, attendanceStatus.getName());
    }

    private void handleEditAttendance(LocalDate currentDate) {
        CrewAttendance crewAttendance = getCrewAttendance(inputView.readUpdateNickName());
        WorkDateTime afterAttendance = getAfterAttendance(currentDate);

        WorkDateTime beforeAttendance = getBeforeAttendance(crewAttendance, afterAttendance);
        AttendanceStatus beforeStatus = crewAttendance.calculateAttendanceStatus(beforeAttendance.getDate());

        crewAttendance.updateAttendance(afterAttendance);
        AttendanceStatus afterStatus = crewAttendance.calculateAttendanceStatus(afterAttendance.getDate());

        outputView.printUpdateResult(beforeAttendance, beforeStatus.getName(), afterAttendance,
                afterStatus.getName());
    }

    private WorkDateTime getAfterAttendance(LocalDate currentDate) {
        int updateDayValue = inputView.readUpdateDate();
        WorkDate updateWorkDate = WorkDate.from(
                LocalDate.of(currentDate.getYear(), currentDate.getMonth(), updateDayValue));
        LocalTime updateArriveTime = inputView.readUpdateArriveTime();

        return createAttendance(updateWorkDate, updateArriveTime);
    }

    private WorkDateTime getBeforeAttendance(CrewAttendance crewAttendance, WorkDateTime afterAttendance) {
        return crewAttendance.retrieveAttendance(afterAttendance.getDate());
    }

    private void handleRecordAttendance() {
        CrewAttendance crewAttendance = getCrewAttendance(inputView.readNickName());

        List<AttendanceRecodeDto> attendanceRecords = crewAttendance.retrieveAttendanceOrderByDate().stream()
                .map(AttendanceRecodeDto::from)
                .toList();
        AttendanceResultDto attendanceResult = AttendanceResultDto.from(crewAttendance);

        outputView.printTotalAttendanceStatus(attendanceRecords, attendanceResult);
    }

    private CrewAttendance getCrewAttendance(String nickName) {
        return crewAttendanceRepository.findByName(nickName).orElseThrow(
                () -> new IllegalArgumentException("해당 이름의 크루가 존재하지 않습니다."));
    }

    private void handleRiskAttendance() {
        List<PenaltyCrewDto> penaltyCrews = crewAttendanceRepository.findAll().stream()
                .filter(CrewAttendance::isPenalty)
                .map(PenaltyCrewDto::from)
                .toList();

        outputView.printPenaltyCrews(penaltyCrews);
    }

    private WorkDateTime createAttendance(WorkDate workDate, LocalTime localTime) {
        return new WorkDateTime(workDate, new WorkTime(localTime.getHour(), localTime.getMinute()));
    }
}
