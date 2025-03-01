package controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import model.AbsentPenalty;
import model.Attendance;
import model.AttendanceBook;
import model.CrewAttendances;
import view.InputView;

public class AttendanceController {

    public static final String ATTENDANCE_FILE_PATH = "src/main/resources/attendance.csv";

    private final LocalDate today;
    private final InputView inputView;

    public AttendanceController(LocalDate today, InputView inputView) {
        this.today = today;
        this.inputView = inputView;
    }

    public void run() {
        AttendanceBook book = new AttendanceBook(List.of(
                new CrewAttendances("율무", List.of(
                        new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 2)),
                        new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(8, 57))

                ))
        ));

        while (true) {
            String select = inputView.inputMenu(today);

            if (select.equals("1")) {
                attend(book);
                continue;
            }

            if (select.equals("2")) {
                update(book);
                continue;
            }

            if (select.equals("3")) {
                printAttendanceSheetsByCrew(book);
                continue;
            }

//            if (select.equals("4")) {
//                printRiskOfExpulsion(attendanceSheets);
//                continue;
//            }

            if (select.equals("Q")) {
                return;
            }
        }
    }

    // 1번 기능
    private void attend(AttendanceBook book) {
        System.out.print(System.lineSeparator());
        String nickname = inputView.inputNickname();
        String time = inputView.inputTime();
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        Attendance attendance = book.check(nickname, today, LocalTime.of(hour, minute));
        printAddInformation(attendance);
    }

    // OutputView
    private void printAddInformation(Attendance attendance) {
        System.out.print(System.lineSeparator());
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s)%n",
                attendance.getMonth(), attendance.getDay(), getKoreanWeek(attendance.getDayOfWeek()),
                attendance.getHour(), attendance.getMinute(), getAttendStatus(attendance));
        System.out.print(System.lineSeparator());
    }

    private String getAttendStatus(Attendance attendance) {
        if (attendance.isAbsent()) {
            return "결석";
        }

        if (attendance.isLate()) {
            return "지각";
        }

        return "출석";
    }

    private String getKoreanWeek(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    // 2번 기능
    private void update(AttendanceBook book) {
        System.out.print(System.lineSeparator());
        String updateNickname = inputView.inputUpdateNickname();
        LocalDate updateDate = today.withDayOfMonth(Integer.parseInt(inputView.inputUpdateDate()));
        LocalTime updateTime = LocalTime.parse(inputView.inputTime());

        Attendance attendance = book.findAttendance(updateNickname, updateDate);
        Attendance updateAttendance = book.update(updateNickname, updateDate, updateTime);

        printUpdateInformation(attendance, updateAttendance);
    }

    // OutputView
    private void printUpdateInformation(Attendance attendance, Attendance updateAttendance) {
        System.out.print(System.lineSeparator());
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s) -> ",
                attendance.getMonth(), attendance.getDay(), getKoreanWeek(attendance.getDayOfWeek()),
                attendance.getHour(), attendance.getMinute(), getAttendStatus(attendance));
        System.out.printf("%02d:%02d (%s) 수정 완료!%n",
                updateAttendance.getHour(), updateAttendance.getMinute(), getAttendStatus(updateAttendance));
        System.out.print(System.lineSeparator());
    }

    // 3번 기능
    private void printAttendanceSheetsByCrew(AttendanceBook book) {
        System.out.print(System.lineSeparator());
        String nickname = inputView.inputNickname();

        CrewAttendances crewAttendances = book.findCrewAttendance(nickname);
        printCrewAttendanceRecords(crewAttendances);
    }

    private void printCrewAttendanceRecords(CrewAttendances crewAttendances) {
        System.out.print(System.lineSeparator());
        System.out.printf("이번 달 %s의 출석 기록입니다.%n", crewAttendances.getNickname());
        System.out.print(System.lineSeparator());

        for (int day = 1; day < today.getDayOfMonth(); day++) {
            LocalDate date = today.withDayOfMonth(day);
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY
            || date.isEqual(LocalDate.of(2024, 12, 25))) continue;

            System.out.printf("%02d월 %02d일 %s ", date.getMonth().getValue(), day, getKoreanWeek(date.getDayOfWeek()));
            if (crewAttendances.isAlreadyAttend(date)) {
                Attendance attendance = crewAttendances.findAttendance(date);
                System.out.printf("%02d:%02d (%s)%n", attendance.getHour(), attendance.getMinute(), getAttendStatus(attendance));
                continue;
            }

            System.out.printf("--:-- (결석)%n");
        }
        System.out.print(System.lineSeparator());

        System.out.printf("출석: %d회%n", crewAttendances.calculateAttendCountUntilDate(today));
        System.out.printf("지각: %d회%n", crewAttendances.calculateLateCountUntilDate(today));
        System.out.printf("결석: %d회%n", crewAttendances.calculateAbsentCountUntilDate(today));
        System.out.print(System.lineSeparator());
        AbsentPenalty absentPenalty = crewAttendances.determineAttendPenalty(today);
        if (absentPenalty != AbsentPenalty.NONE) {
            System.out.printf("%s 대상자입니다.%n", getAbsentPenalty(absentPenalty));
        }
        System.out.print(System.lineSeparator());
    }

    private String getAbsentPenalty(AbsentPenalty absentPenalty) {
        if (absentPenalty == AbsentPenalty.DISMISSAL) {
            return "제적";
        }

        if (absentPenalty == AbsentPenalty.INTERVIEW) {
            return "면담";
        }

        if (absentPenalty == AbsentPenalty.WARNING) {
            return "경고";
        }

        return "";
    }


    // 4번 기능
    private void printRiskOfExpulsion(AttendanceBook book) {
        List<String> names = book.allNames();

        for (String name : names) {

        }
    }

}
