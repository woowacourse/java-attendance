package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Attendances;
import domain.Crew;
import domain.CustomDayOfWeek;
import domain.Day;
import domain.Holiday;
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

    public AttendanceController(AttendanceBook attendanceBook, InputView inputView, OutputView outputView) {
        this.attendanceBook = attendanceBook;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        attendanceBook.recordAllAbsences();
        LocalDate today = LocalDate.now();
        String option = "";
        while (!QUIT.equals(option)) {
            option = inputView.getOption(today);
            Command.getCommand(option).excute(this);
        }
    }

    protected void attend() {
        validateToday();
        LocalDate today = LocalDate.now();
        String nickname = inputView.getNickname();
        Crew crew = new Crew(nickname);
        attendanceBook.isAlreadyAttended(crew, today);

        LocalTime attendanceTime = inputView.getAttendanceTime();
        Attendance attendance = new Attendance(new Day(today), attendanceTime);
        attendanceBook.recordAttendance(crew, attendance);

        outputView.printAttendanceDetail(attendance);
    }

    private void validateToday() {
        LocalDate today = LocalDate.now();
        if (Holiday.isHoliday(today) || !CustomDayOfWeek.isWeekDay(today)) {
            throw new IllegalArgumentException(
                    "[ERROR] " + today.getMonth().getValue() + "월 " + today.getDayOfMonth() + "일 "
                            + CustomDayOfWeek.getInstance(today).getName() + "은 등교일이 아닙니다.");
        }
    }

    protected void modifyAttendance() {
        String nickname = inputView.getNicknameForModification();
        Crew crew = new Crew(nickname);
        Attendances attendances = attendanceBook.getAttendances(crew);

        Integer datOfMonth = inputView.getDayOfMonth();
        Day day = Day.of(datOfMonth);
        Attendance attendance = attendances.findByDay(day);
        Attendance originAttendance = new Attendance(day, attendance.getTime());

        LocalTime attendanceTime = inputView.getModifiedAttendanceTime();
        attendance.modifyTimeTo(attendanceTime);
        outputView.printModifiedAttendanceDetail(originAttendance, attendance);
    }

    protected void readAttendanceHistory() {
        String nickname = inputView.getNickname();
        Crew crew = new Crew(nickname);
        Attendances attendances = attendanceBook.getAttendances(crew);
        outputView.printAttendanceHistory(nickname, attendances);
    }

    protected void readPenaltyHistory() {
        Map<Crew, Attendances> penaltyCrews = attendanceBook.getPenaltyHistory();
        outputView.printPenaltyCrews(penaltyCrews);
    }

    protected void quit() {
    }
}
