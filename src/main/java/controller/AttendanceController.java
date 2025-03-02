package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Attendances;
import domain.Crew;
import domain.CustomDayOfWeek;
import domain.Day;
import domain.Holiday;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private static final String QUIT = "Q";
    private final AttendanceBook attendanceBook;
    private final InputView inputView;
    private final OutputView outputView;
    private final Clock clock;

    public AttendanceController(AttendanceBook attendanceBook, InputView inputView, OutputView outputView,
                                Clock systemClock) {
        this.attendanceBook = attendanceBook;
        this.inputView = inputView;
        this.outputView = outputView;
        this.clock = systemClock;
    }

    public void run() {
        attendanceBook.recordAllAbsences(clock);
        String option = "";
        while (!QUIT.equals(option)) {
            option = inputView.getOption(LocalDate.now(clock));
            Command.getCommand(option).excute(this);
        }
    }

    void attend() {
        validateToday();
        LocalDate today = LocalDate.now(clock);
        String nickname = inputView.getNickname();
        Crew crew = new Crew(nickname);
        attendanceBook.isAlreadyAttended(crew, today);

        LocalTime attendanceTime = inputView.getAttendanceTime();
        Attendance attendance = new Attendance(new Day(today), attendanceTime);
        attendanceBook.recordAttendance(crew, attendance);

        outputView.printAttendanceDetail(attendance);
    }

    private void validateToday() {
        LocalDate today = LocalDate.now(clock);
        if (Holiday.isHoliday(today) || !CustomDayOfWeek.isWeekDay(today)) {
            throw new IllegalArgumentException(
                    "[ERROR] " + today.getMonth().getValue() + "월 " + today.getDayOfMonth() + "일 "
                            + CustomDayOfWeek.getInstance(today).getName() + "은 등교일이 아닙니다.");
        }
    }

    void modifyAttendance() {
        Attendances attendances = getAttendancesForModification();

        Integer datOfMonth = inputView.getDayOfMonth();
        Day day = Day.of(datOfMonth);
        Attendance attendance = attendances.findByDay(day);
        Attendance originAttendance = new Attendance(day, attendance.getTime());

        LocalTime attendanceTime = inputView.getModifiedAttendanceTime();
        attendance.modifyTimeTo(attendanceTime);
        outputView.printModifiedAttendanceDetail(originAttendance, attendance);
    }

    private Attendances getAttendancesForModification() {
        String nickname = inputView.getNicknameForModification();
        Crew crew = new Crew(nickname);
        return attendanceBook.getAttendances(crew);
    }

    void readAttendanceHistory() {
        String nickname = inputView.getNickname();
        Crew crew = new Crew(nickname);
        Attendances attendances = attendanceBook.getAttendances(crew);
        outputView.printAttendanceHistory(nickname, attendances, clock);
    }

    void readPenaltyHistory() {
        Map<Crew, Attendances> penaltyCrews = attendanceBook.getPenaltyHistory(clock);
        outputView.printPenaltyCrews(penaltyCrews, clock);
    }

    void quit() {
    }
}
