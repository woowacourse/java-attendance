package controller;

import controller.dto.AttendanceRecodeDto;
import controller.dto.AttendanceResultDto;
import controller.dto.PenaltyCrewDto;
import domain.AttendanceStatus;
import domain.Crew;
import domain.CrewAttendance;
import domain.CrewAttendanceRepository;
import domain.Date;
import domain.DateTime;
import domain.MenuOption;
import domain.Time;
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

            return menuOption.isExit();
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
        CrewAttendance crewAttendance = getCrewAttendanceByNickName(inputView.readNickName());
        DateTime dateTime = createDateTime(currentDate, inputView.readArriveTime());

        crewAttendance.addAttendance(dateTime);
        AttendanceStatus attendanceStatus = crewAttendance.calculateAttendanceStatus(dateTime.getDate());

        outputView.printArriveResult(dateTime, attendanceStatus.getName());
    }

    private void handleEditAttendance(LocalDate currentDate) {
        CrewAttendance crewAttendance = getCrewAttendanceByNickName(inputView.readUpdateNickName());
        DateTime afterDateTime = getUpdatedDateTime(currentDate);

        DateTime beforeDateTime = getBeforeDateTime(crewAttendance, afterDateTime);
        AttendanceStatus beforeStatus = crewAttendance.calculateAttendanceStatus(beforeDateTime.getDate());

        crewAttendance.updateAttendance(afterDateTime);
        AttendanceStatus afterStatus = crewAttendance.calculateAttendanceStatus(afterDateTime.getDate());

        outputView.printUpdateResult(beforeDateTime, beforeStatus.getName(), afterDateTime, afterStatus.getName());
    }

    private DateTime getUpdatedDateTime(LocalDate currentDate) {
        int updateDate = inputView.readUpdateDate();
        LocalTime updateArriveTime = inputView.readUpdateArriveTime();
        return createDateTime(
                LocalDate.of(currentDate.getYear(), currentDate.getMonth(), updateDate), updateArriveTime);
    }

    private DateTime getBeforeDateTime(CrewAttendance crewAttendance, DateTime afterDateTime) {
        return crewAttendance.retrieveDateTime(afterDateTime.getDate());
    }


    private void handleRecordAttendance() {
        CrewAttendance crewAttendance = getCrewAttendanceByNickName(inputView.readNickName());

        List<AttendanceRecodeDto> attendanceRecords = crewAttendance.retrieveDateTimesOrderByDate()
                .stream().map(AttendanceRecodeDto::from).toList();
        AttendanceResultDto attendanceResult = AttendanceResultDto.from(crewAttendance);

        outputView.printTotalAttendanceStatus(attendanceRecords, attendanceResult);
    }

    private void handleRiskAttendance() {
        List<PenaltyCrewDto> penaltyCrews = crewAttendanceRepository.findAll().stream()
                .filter(CrewAttendance::isPenalty)
                .map(PenaltyCrewDto::from)
                .toList();

        outputView.printPenaltyCrews(penaltyCrews);
    }

    private CrewAttendance getCrewAttendanceByNickName(String nickName) {
        return crewAttendanceRepository.findByEqualsCrew(new Crew(nickName));
    }

    private DateTime createDateTime(LocalDate localDate, LocalTime arriveTime) {
        return new DateTime(new Date(localDate), new Time(arriveTime.getHour(), arriveTime.getMinute()));
    }
}
