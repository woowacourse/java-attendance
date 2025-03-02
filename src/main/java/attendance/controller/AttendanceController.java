package attendance.controller;

import attendance.Initializer;
import attendance.constant.Holiday;
import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.Crew;
import attendance.domain.Nickname;
import attendance.domain.StatusStatistics;
import attendance.util.DateUtil;
import attendance.util.ErrorMessage;
import attendance.util.FormattedErrorMessage;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceController {

    private static final Map<String, Runnable> option = new HashMap<>();
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
        initializeOption();
        while (true) {
            String inputFunction = InputView.readFunction(systemDate);
            if (QUIT.equals(inputFunction)) {
                break;
            }
            try {
                execute(inputFunction);
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void initializeOption() {
        option.put(RECORD, this::recordAttendance);
        option.put(EDIT, this::editAttendance);
        option.put(CHECK_RECORD, this::checkRecords);
        option.put(CHECK_PENALTY, this::checkPenalty);
    }

    private void execute(String inputFunction) {
        Runnable runnable = option.getOrDefault(inputFunction, null);
        if (runnable == null) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_OPTION_ERROR.getMessage());
        }
        runnable.run();
    }

    private void recordAttendance() {
        validateDate(systemDate);
        Crew crew = getCrew(InputView.readNickname());

        String inputAttendTime = InputView.readAttendTimeForRecord();
        Attendance attendance = new Attendance(systemDate, LocalTime.parse(inputAttendTime));

        attendanceBook.add(crew, attendance);
        OutputView.printRecordAttendanceResult(attendance);
    }

    private void validateDate(LocalDate inputDate) {
        if (DateUtil.isWeekend(inputDate) || Holiday.isHoliday(inputDate)) {
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
        LocalDate attendDate = LocalDate.of(systemDate.getYear(), systemDate.getMonthValue(), inputDay);
        validateDate(attendDate);
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
