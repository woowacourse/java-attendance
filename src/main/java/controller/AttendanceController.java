package controller;

import domain.*;
import domain.constant.StandardDate;
import util.Converter;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceController {
    private static final String EXIT_OPTION = "Q";

    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Command, Runnable> options;

    public AttendanceController(Crews crews, InputView inputView, OutputView outputView) {
        this.crews = crews;
        this.inputView = inputView;
        this.outputView = outputView;
        this.options = Map.of(
                Command.ATTEND, this::processAttendance,
                Command.UPDATE, this::processAttendanceUpdate,
                Command.HISTORY, this::processAttendanceHistory,
                Command.PENALTY, this::processPenaltyCheck
        );
    }

    public void run() {
        crews.recordAllAbsence(StandardDate.DATE);

        String inputOption = "";

        while (!inputOption.equals(EXIT_OPTION)) {
            outputView.printOptionMessage(StandardDate.DATE);
            inputOption = inputView.getOption();

            selectCommandAndRun(inputOption);
        }
    }

    public void selectCommandAndRun(String inputOption) {
        Command command = Command.check(inputOption);
        if (options.containsKey(command)) {
            options.get(command).run();
        }
    }

    public void processAttendance() {
        checkHoliday(StandardDate.DATE);
        Crew crew = crews.findByNickname(inputView.getNickname());

        if (crew.isAlreadyAttend(StandardDate.DATE)) {
            System.out.println("이미 출석 완료되었습니다. 수정 기능을 이용해주세요.");
            return;
        }

        registerAttendance(crew);
    }

    private void registerAttendance(Crew crew) {
        LocalTime attendanceTime = Converter.convertStringToLocalTime(inputView.getAttendanceTime());
        Attendance attendance = new Attendance(new Day(StandardDate.DATE), attendanceTime);
        crew.addAttendance(attendance);
        outputView.printAttendanceInformation(attendance.toDto());
    }

    private void checkHoliday(LocalDate todayDate) {
        Day today = new Day(todayDate);
        int month = todayDate.getMonth().getValue();
        int dayOfMonth = todayDate.getDayOfMonth();
        String dayOfWeekName = AttendanceStandard.getNameByDayOfWeek(todayDate.getDayOfWeek());

        if (today.checkHoliday()) {
            throw new IllegalArgumentException("[ERROR] " + month + "월 " + dayOfMonth + "일 " + dayOfWeekName + "은 등교일이 아닙니다.");
        }
    }

    public void processAttendanceUpdate() {
        Crew crew = crews.findByNickname(inputView.getEditNickname());
        Attendance attendance = crew.findByDate(Converter.convertStringToInteger(inputView.getEditDayOfMonth()));
        AttendanceDto originalAttendanceDto = attendance.toDto();
        attendance.updateAttendanceTime(Converter.convertStringToLocalTime(inputView.getNewTime()));
        AttendanceDto editedAttendanceDto = attendance.toDto();

        outputView.printUpdatedAttendanceHistory(originalAttendanceDto, editedAttendanceDto);
    }

    public void processAttendanceHistory() {
        String nickname = inputView.getNickname();

        outputView.printCrewAttendanceHistoryMessage(nickname);
        outputView.printAttendanceHistoryWithCrew(crews.findByNickname(nickname));
    }

    public void processPenaltyCheck() {
        List<CrewDto> crewDtos = crews.createCrewDtos();
        List<CrewDto> penaltyCrewDtos = crewDtos.stream()
                .filter(crewDto -> crewDto.getPenaltyStatus() != PenaltyStatus.NONE)
                .collect(Collectors.toCollection(ArrayList::new));

        outputView.printPenaltyCrews(penaltyCrewDtos);
    }
}

