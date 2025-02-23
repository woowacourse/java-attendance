package controller;

import domain.Attendance;
import domain.AttendanceDto;
import domain.Crew;
import domain.CrewDto;
import domain.CrewDtos;
import domain.Crews;
import domain.Day;
import domain.DayOfWeek;
import domain.PenaltyStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private static final String EXIT_OPTION = "Q";

    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Option, Runnable> options = Map.of(
            Option.ATTEND, this::processAttendance,
            Option.EDIT_ATTENDANCE, this::processAttendanceEdit,
            Option.SHOW_ATTENDANCE_HISTORY, this::processAttendanceHistory,
            Option.SHOW_PENALTY_CREWS, this::processPenaltyCheck,
            Option.EXIT, this::exitApplication
    );


    public AttendanceController(Crews crews, InputView inputView, OutputView outputView) {
        this.crews = crews;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        crews.recordAllAbsence();
        while (true) {
            outputView.printOptionMessage();
            processAttendanceTasks(inputView.getOption());
        }
    }

    private void processAttendanceTasks(String optionChoice) {
        options.get(Option.validateValue(optionChoice)).run();
    }

    public void processAttendance() {
        checkHoliday(LocalDate.now());
        Crew crew = crews.findByNickname(inputView.getNickname());

        if (crew.isAlreadyAttend(LocalDate.now())) {
            System.out.println("이미 출석 완료되었습니다. 수정 기능을 이용해주세요.");
            return;
        }

        registerAttendance(crew);
    }

    private void registerAttendance(Crew crew) {
        LocalTime attendanceTime = inputView.getAttendanceTime();
        Attendance attendance = new Attendance(new Day(LocalDate.now()), attendanceTime);
        crew.addAttendance(attendance);
        outputView.printAttendanceInformation(attendance.toDto());
    }

    public void processAttendanceEdit() {

        Crew crew = crews.findByNickname(inputView.getEditNickname());
        Attendance attendance = crew.findByDate(inputView.getEditDayOfMonth());
        AttendanceDto originalAttendanceDto = attendance.toDto();

        attendance.updateAttendanceTime(inputView.getNewTime());
        AttendanceDto editedAttendanceDto = attendance.toDto();

        outputView.printUpdatedAttendanceHistory(originalAttendanceDto, editedAttendanceDto);
    }

    public void processAttendanceHistory() {

        String nickname = inputView.getNickname();
        Crew crew = crews.findByNickname(nickname);

        outputView.printCrewAttendanceHistoryMessage(nickname);
        outputView.printAttendanceHistoryWithCrew(crew);
    }

    public void processPenaltyCheck() {

        List<CrewDto> crewDtos = crews.createCrewDtos();
        List<CrewDto> penaltyCrewDtos = crewDtos.stream()
                .filter(crewDto -> crewDto.getPenaltyStatus() != PenaltyStatus.NONE)
                .collect(Collectors.toCollection(ArrayList::new));

        outputView.printPenaltyCrews(new CrewDtos(penaltyCrewDtos));

    }

    private void exitApplication() {
        System.exit(0);
    }

    private void checkHoliday(LocalDate todayDate) {
        Day today = new Day(todayDate);
        int month = todayDate.getMonth().getValue();
        int dayOfMonth = todayDate.getDayOfMonth();
        String dayOfWeekName = DayOfWeek.getNameById(todayDate.getDayOfWeek().getValue());

        if (today.checkHoliday()) {
            throw new IllegalArgumentException(
                    "[ERROR] " + month + "월 " + dayOfMonth + "일 " + dayOfWeekName + "은 등교일이 아닙니다.");
        }
    }
}



