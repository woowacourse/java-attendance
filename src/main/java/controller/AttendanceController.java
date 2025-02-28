package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Attendances;
import domain.CustomDayOfWeek;
import domain.Day;
import domain.Holiday;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class AttendanceController {
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
        String option;
        option = inputView.getOption(today);
        switch (option) {
            case "1":
                if (!Holiday.isHoliday(today) && CustomDayOfWeek.isWeekDay(today)) {
                    String nickname = inputView.getNickname();
                    LocalTime attendanceTime = inputView.getAttendanceTime();
                    Attendance attendance = new Attendance(new Day(today), attendanceTime);
                    attendanceBook.recordAttendance(nickname, attendance);
                    outputView.printAttendanceDetail(attendance);
                    break;
                }
                throw new IllegalArgumentException(
                        "[ERROR] " + today.getMonth().getValue() + "월 " + today.getDayOfMonth() + "일 "
                                + CustomDayOfWeek.getInstance(today).getName() + "은 등교일이 아닙니다.");
            case "2":
                String nickname = inputView.getNicknameForModification();
                Attendances attendances = attendanceBook.getAttendances(nickname);

                Integer datOfMonth = inputView.getDayOfMonth();
                Day day = Day.of(datOfMonth);
                Attendance attendance = attendances.findByDay(day);

                Attendance originAttendance = new Attendance(day, attendance.getTime());

                LocalTime attendanceTime = inputView.getModifiedAttendanceTime();
                attendance.modifyTimeTo(attendanceTime);

                outputView.printModifiedAttendanceDetail(originAttendance, attendance);

                break;

            case "3":
                nickname = inputView.getNickname();
                attendances = attendanceBook.getAttendances(nickname);
                outputView.printAttendanceHistory(nickname, attendances);
                break;
                
            case "4":
                Map<String, Attendances> penaltyCrews = attendanceBook.getPenaltyHistory();
                outputView.printPenaltyCrews(penaltyCrews);
                break;

            case "Q":
                break;
            default:
                break;
        }
    }
}
