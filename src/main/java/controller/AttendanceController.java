package controller;

import domain.Attendance;
import domain.Crew;
import domain.Crews;
import domain.Day;
import domain.DayOfWeek;
import domain.Holiday;
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
        outputView.printAttendanceInformation(attendance);
    }

    public void processAttendanceEdit() {

        Crew crew = crews.findByNickname(inputView.getEditNickname());
        Attendance attendance = crew.findByDate(inputView.getEditDayOfMonth());
        Attendance originalAttendance = new Attendance(attendance.getDay(), attendance.getAttendanceTime());

        attendance.updateAttendanceTime(inputView.getNewTime());

        outputView.printUpdatedAttendanceHistory(originalAttendance, attendance);
    }

    public void processAttendanceHistory() {

        String nickname = inputView.getNickname();
        Crew crew = crews.findByNickname(nickname);

        outputView.printCrewAttendanceHistoryMessage(nickname);
        outputView.printAttendanceHistoryWithCrew(crew.getAttendances());
    }

    public void processPenaltyCheck() {
        List<Crew> penaltyCrews = crews.getCrews().stream()
                .filter(crew -> crew.getPenaltyStatus() != PenaltyStatus.NONE)
                .collect(Collectors.toCollection(ArrayList::new));

        outputView.printPenaltyCrews(penaltyCrews);

    }

    private void exitApplication() {
        System.exit(0);
    }

    private void checkHoliday(LocalDate todayDate) {
        int month = todayDate.getMonth().getValue();
        int dayOfMonth = todayDate.getDayOfMonth();
        String dayOfWeekName = DayOfWeek.getNameById(todayDate.getDayOfWeek().getValue());

        if (Holiday.isHoliday(todayDate)) {
            throw new IllegalArgumentException(
                    "[ERROR] " + month + "월 " + dayOfMonth + "일 " + dayOfWeekName + "은 등교일이 아닙니다.");
        }
    }
}



