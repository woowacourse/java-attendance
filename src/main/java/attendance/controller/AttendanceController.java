package attendance.controller;

import attendance.constant.Holiday;
import attendance.constant.Option;
import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.Nickname;
import attendance.domain.StatusStatistics;
import attendance.infrastructure.Initializer;
import attendance.util.DateUtil;
import attendance.util.FormattedErrorMessage;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class AttendanceController {

    private final AttendanceBook attendanceBook;
    private final LocalDate systemDate;
    private final Map<Option, Runnable> function = Map.of(
            Option.RECORD, this::recordAttendance,
            Option.EDIT, this::editAttendance,
            Option.CHECK_RECORD, this::checkRecords,
            Option.CHECK_PENALTY, this::checkPenalty
    );

    public AttendanceController(Initializer initializer) {
        this.attendanceBook = initializer.initAttendanceBook();
        this.systemDate = initializer.initSystemDate();
    }

    public void run() {
        while (true) {
            String inputFunction = InputView.readFunction(systemDate);
            if (Option.isQuit(inputFunction)) {
                break;
            }
            try {
                execute(inputFunction);
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void execute(String inputFunction) {
        Option selectedOption = Option.select(inputFunction);
        Runnable runnable = function.get(selectedOption);
        runnable.run();
    }

    private void recordAttendance() {
        validateDate(systemDate);
        Crew crew = createCrew(InputView.readNickname());

        LocalTime inputAttendTime = InputView.readAttendTimeForRecord();
        Attendance attendance = Attendance.of(systemDate, inputAttendTime);

        attendanceBook.add(crew, attendance);
        OutputView.printRecordAttendanceResult(attendance);
    }

    private void validateDate(LocalDate inputDate) {
        if (DateUtil.isWeekend(inputDate) || Holiday.isHoliday(inputDate)) {
            throw new IllegalArgumentException(FormattedErrorMessage.INVALID_ATTEND_DATE_ERROR.getDateFormatMessage(systemDate));
        }
    }

    private Crew createCrew(String inputNickname) {
        Nickname nickname = new Nickname(inputNickname);
        Crew crew = new Crew(nickname);
        attendanceBook.validateContainsCrew(crew);
        return crew;
    }

    private void editAttendance() {
        Crew crew = createCrew(InputView.readNicknameForEdit());
        int inputDay = InputView.readAttendDay();
        LocalDate attendDate = LocalDate.of(systemDate.getYear(), systemDate.getMonthValue(), inputDay);
        validateDate(attendDate);
        LocalTime inputTime = InputView.readAttendTimeForEdit();

        Attendance newAttendance = Attendance.of(attendDate, inputTime);
        Attendance oldAttendance = attendanceBook.findAttendanceByCrew(crew, attendDate);
        attendanceBook.update(crew, oldAttendance, newAttendance);
        OutputView.printEditAttendanceResult(oldAttendance, newAttendance);
    }

    private void checkRecords() {
        Crew crew = createCrew(InputView.readNickname());

        Attendances attendances = attendanceBook.getRecordOfCrew(systemDate, crew);
        OutputView.printAttendanceRecordsUntilYesterday(crew, systemDate, attendances);

        StatusStatistics statusStatistics = new StatusStatistics(attendances, systemDate);
        OutputView.printStatusStatistics(statusStatistics);
    }

    public void checkPenalty() {
        Map<Crew, StatusStatistics> crewsAndStatistics = attendanceBook.getSortedCrewsAndStatistics(systemDate);
        OutputView.printPenaltyCrews(crewsAndStatistics);
    }
}
