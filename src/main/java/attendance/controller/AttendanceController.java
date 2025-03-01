package attendance.controller;

import attendance.Initializer;
import attendance.constant.Holiday;
import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.Crew;
import attendance.domain.Nickname;
import attendance.domain.StatusStatistics;
import attendance.util.DateUtil;
import attendance.util.FormattedErrorMessage;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AttendanceController {

    private static final String QUIT = "Q";
    private static final String RECORD = "1";
    private static final String EDIT = "2";
    private static final String CHECK_RECORD = "3";
    private static final String CHECK_PENALTY = "4";

    private final AttendanceBook attendanceBook;
    private final LocalDate systemDate;

    public AttendanceController(Initializer initializer) {
        this.attendanceBook = initializer.initAttendanceBook();
        this.systemDate = initializer.initSystemDate();
    }

    public void run() {
        while (true) {
            String function = InputView.readFunction(systemDate);
            if (QUIT.equals(function)) {
                break;
            }

            try {
                execute(function);
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void execute(String function) {
        if (RECORD.equals(function)) {
            recordAttendance();
        }

        if (EDIT.equals(function)) {
            editAttendance();
        }

        if (CHECK_RECORD.equals(function)) {
            checkRecords();
        }

        if (CHECK_PENALTY.equals(function)) {
            checkPenalty();
        }
    }

    private void recordAttendance() {
        validateSystemDate();
        Crew crew = getCrew(InputView.readNickname());

        String inputAttendTime = InputView.readAttendTimeForRecord();
        Attendance attendance = new Attendance(systemDate, LocalTime.parse(inputAttendTime));

        attendanceBook.add(crew, attendance);
        OutputView.printRecordAttendanceResult(attendance);
    }

    private void validateSystemDate() {
        if (DateUtil.isWeekend(systemDate) || Holiday.isHoliday(systemDate)) {
            throw new IllegalArgumentException(FormattedErrorMessage.INVALID_ATTEND_DATE_ERROR.getDateFormatMessage(systemDate));
        }
    }

    private Crew getCrew(String inputNickname) {
        Crew crew = new Crew(new Nickname(inputNickname));
        attendanceBook.validateCrew(crew);
        return crew;
    }

    private void editAttendance() {
        Crew crew = getCrew(InputView.readNicknameForEdit());
        int inputDay = InputView.readAttendDay();
        validateSystemDate();
        LocalDate attendDate = LocalDate.of(systemDate.getYear(), systemDate.getMonthValue(), inputDay);
        LocalTime inputTime = InputView.readAttendTimeForEdit();

        Attendance newAttendance = new Attendance(attendDate, inputTime);
        Attendance oldAttendance = attendanceBook.findAttendanceByCrew(crew, attendDate);
        attendanceBook.update(crew, oldAttendance, newAttendance);
        OutputView.printEditAttendanceResult(oldAttendance, newAttendance);
    }

    private void checkRecords() {
        Crew crew = getCrew(InputView.readNickname());

        List<Attendance> attendances = attendanceBook.getRecordOfCrew(systemDate, crew);
        OutputView.printAttendanceRecordsUntilYesterday(crew, systemDate, attendances);

        StatusStatistics statusStatistics = new StatusStatistics(attendances, systemDate);
        OutputView.printStatusStatistics(statusStatistics);
    }

    public void checkPenalty() {
        Map<Crew, StatusStatistics> crewsAndStatistics = attendanceBook.getSortedCrewsAndStatistics(systemDate);
        OutputView.printPenaltyCrews(crewsAndStatistics);
    }
}
