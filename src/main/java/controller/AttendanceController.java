package controller;

import static util.ExceptionHandler.runInputCommand;

import domain.AbsentPolicy;
import domain.AttendanceDateTime;
import domain.AttendanceSheet;
import domain.AttendanceSheets;
import domain.AttendanceSheetsFactory;
import domain.AttendanceState;

import parser.InputParser;
import util.FileReaderUtil;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceController {

    public static final String CREATE_ATTENDANCE = "1";
    public static final String UPDATE_ATTENDANCE = "2";
    public static final String ATTENDANCE_SHEET = "3";
    public static final String RISK_OF_EXPLUSTION = "4";
    public static final String QUIT = "Q";

    public static final LocalDate DATE = LocalDate.of(2024, 12, 13);

    private final InputView inputView;

    public AttendanceController(InputView inputView) {
        this.inputView = inputView;
    }

    public void start() {
        AttendanceSheetsFactory attendanceSheetsFactory = new AttendanceSheetsFactory(new FileReaderUtil());
        AttendanceSheets attendanceSheets = attendanceSheetsFactory.create();

        runInputCommand(() -> inputCommand(attendanceSheets));
    }

    private String inputCommand(AttendanceSheets attendanceSheets) {
        String select = inputView.inputMenu(DATE);
        switch (select) {
            case CREATE_ATTENDANCE -> attend(attendanceSheets);
            case UPDATE_ATTENDANCE -> updateAttendance(attendanceSheets);
            case ATTENDANCE_SHEET -> printAttendance(attendanceSheets);
            case RISK_OF_EXPLUSTION -> printRiskOfExpulsion(attendanceSheets);
            case QUIT -> { return QUIT; }
        }
        return select;
    }

    private void attend(AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputNickname();
        LocalTime localTime = InputParser.timeParser(inputView.inputTime());

        LocalDateTime localDateTime = LocalDateTime.of(DATE.getYear(), DATE.getMonth(), DATE.getDayOfMonth(),
                localTime.getHour(), localTime.getMinute());

        attendanceSheets.add(new AttendanceSheet(nickname, AttendanceDateTime.from(localDateTime)));
    }

    private void updateAttendance(AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputUpdateNickname();
        List<AttendanceSheet> attendanceByNickname = attendanceSheets.findAttendanceByNickname(nickname);
        int day = InputParser.dayParser(inputView.inputUpdateDate());

        AttendanceSheet attendanceSheetByNicknameAndDay = attendanceByNickname.stream()
                .filter(attendanceSheet -> attendanceSheet.getAttendanceDateTime().getAttendanceDateTime().getDayOfMonth() == day)
                .findFirst()
                .orElseThrow();

        LocalTime localTime = InputParser.timeParser(inputView.inputUpdateTime());
        attendanceSheetByNicknameAndDay.getAttendanceDateTime().update(localTime);
    }

    private void printAttendance(AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputNickname();
        OutputView.printAttendanceSheetIntro(nickname);

        List<AttendanceSheet> attendancesByNickname = attendanceSheets.findAttendanceByNickname(nickname);
        printAttendanceSheets(attendancesByNickname);

        int attendCount = attendanceSheets.getStateCount(nickname, AttendanceState.ATTEND);
        int lateCount = attendanceSheets.getStateCount(nickname, AttendanceState.LATE);
        int absentCount = attendanceSheets.getStateCount(nickname, AttendanceState.ABSENT);

        OutputView.printAttendanceStatistics(attendCount, lateCount, absentCount);
        OutputView.printAbsentPolicy(AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount));
    }

    private void printRiskOfExpulsion(AttendanceSheets attendanceSheets) {
        OutputView.printRiskOfExpulsionBanner();

        List<String> allNames = attendanceSheets.findAllNames();

        for (String name : allNames) {
            printRiskOfExpulsionByName(attendanceSheets, name);
        }
    }

    private static void printRiskOfExpulsionByName(AttendanceSheets attendanceSheets, String name) {
        int lateCount = attendanceSheets.getStateCount(name, AttendanceState.LATE);
        int absentCount = attendanceSheets.getStateCount(name, AttendanceState.ABSENT);

        AbsentPolicy absentPolicy = AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount);
        if (AbsentPolicy.isNotRiskOfExpulsion(absentPolicy)) {
            return;
        }

        OutputView.printRiskOfExpulsion(name, lateCount, absentCount, absentPolicy);
    }

    private static void printAttendanceSheets(List<AttendanceSheet> attendancesByNickname) {
        Map<Integer, AttendanceDateTime> dayToAttendanceDateTime = new HashMap<>();
        attendancesByNickname.forEach(attendance ->
                dayToAttendanceDateTime.put(
                        attendance.getAttendanceDateTime().getAttendanceDateTime().getDayOfMonth(),
                        attendance.getAttendanceDateTime()));

        OutputView.printAttendanceSheets(dayToAttendanceDateTime, DATE.getDayOfMonth());
    }
}
