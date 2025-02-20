package controller;

import domain.*;
import util.Converter;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceController {
    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(Crews crews, InputView inputView, OutputView outputView) {
        this.crews = crews;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        String option = "";
        crews.recordAllAbsence();
        while (!option.equals("Q")) {
            outputView.printOptionMessage();
            option = inputView.getOption();

            processAttendanceTasks(option);
        }
    }

    private void processAttendanceTasks(String option) {
        processAttendance(option);
        processAttendanceEdit(option);
        processAttendanceHistory(option);
        processPenaltyCheck(option);
    }

    public void processAttendance(String option) {
        if (!option.equals("1")) return;
        checkHoliday(LocalDate.now());
        Crew crew = crews.findByNickname(inputView.getNickname());

        if (crew.isAlreadyAttend(LocalDate.now())) {
            System.out.println("이미 출석 완료되었습니다. 수정 기능을 이용해주세요.");
            return;
        }

        registerAttendance(crew);
    }

    private void registerAttendance(Crew crew) {
        LocalTime attendanceTime = Converter.convertStringToLocalTime(inputView.getAttendanceTime());
        Attendance attendance = new Attendance(new Day(LocalDate.now()), attendanceTime);
        crew.addAttendance(attendance);
        outputView.printAttendanceInformation(attendance.toDto());
    }

    public void processAttendanceEdit(String option) {
        if (!option.equals("2")) return;

        Crew crew = crews.findByNickname(inputView.getEditNickname());
        Attendance attendance = crew.findByDate(Converter.convertStringToInteger(inputView.getEditDayOfMonth()));
        AttendanceDto originalAttendanceDto = attendance.toDto();

        attendance.updateAttendanceTime(Converter.convertStringToLocalTime(inputView.getNewTime()));
        AttendanceDto editedAttendanceDto = attendance.toDto();

        outputView.printUpdatedAttendanceHistory(originalAttendanceDto, editedAttendanceDto);
    }

    public void processAttendanceHistory(String option) {
        if (!option.equals("3")) return;

        String nickname = inputView.getNickname();
        Crew crew = crews.findByNickname(nickname);

        outputView.printCrewAttendanceHistoryMessage(nickname);
        outputView.printAttendanceHistoryWithCrew(crew);
    }

    public void processPenaltyCheck(String option) {
        if (!option.equals("4")) return;

        List<CrewDto> crewDtos = crews.createCrewDtos();
        List<CrewDto> penaltyCrewDtos = crewDtos.stream()
                .filter(crewDto -> crewDto.getPenaltyStatus() != PenaltyStatus.NONE)
                .collect(Collectors.toCollection(ArrayList::new));

        outputView.printPenaltyCrews(new CrewDtos(penaltyCrewDtos));

    }

    private void checkHoliday(LocalDate todayDate) {
        Day today = new Day(todayDate);
        int month = todayDate.getMonth().getValue();
        int dayOfMonth = todayDate.getDayOfMonth();
        String dayOfWeekName = DayOfWeek.getNameById(todayDate.getDayOfWeek().getValue());
        
        if (today.checkHoliday()) {
            throw new IllegalArgumentException("[ERROR] " + month + "월 " + dayOfMonth + "일 " + dayOfWeekName + "은 등교일이 아닙니다.");
        }
    }
}



