package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceType;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.MenuCommand;
import attendance.dto.AttendanceCheckDto;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDateTime;

public class OptionCheckAttendance extends MenuOption {
    private static final String WEEKEND_ERROR_MESSAGE = "[ERROR] 주말에는 출석을 할 수 없습니다.";
    private static final String TIME_FORMAT_ERROR_MESSAGE = "[ERROR] 올바르지 않은 시간 형식을 입력했습니다.";
    private static final String TIME_PATTERN = "(2[0-3]|[01][0-9]):[0-5][0-9]";
    private static final int WEEKEND_VALUE = 5;

    public OptionCheckAttendance(InputView inputView, OutputView outputView, Crews crews, Attendances attendances) {
        super(inputView, outputView, crews, attendances);
    }

    @Override
    public void executeMenuOption(MenuCommand command) {
        executeCheckAttendance();
    }

    private void executeCheckAttendance() {
        validateWeekend();
        Crew crew = findCrewByCrewName();
        attendances.hasCheckedAttendance(crew, LOCAL_DATE_TODAY);
        LocalDateTime localDateTime = validatePresentTime();
        AttendanceType status = AttendanceType.of(localDateTime);
        Attendance todayAttendance = new Attendance(crew, localDateTime, status);
        attendances.add(todayAttendance);
        AttendanceCheckDto attendanceInfo = AttendanceCheckDto.fromAttendance(todayAttendance);
        outputView.printTodayAttendance(attendanceInfo);
    }

    private void validateWeekend() {
        if (LOCAL_DATE_TODAY.getDayOfWeek().getValue() > WEEKEND_VALUE) {
            throw new IllegalArgumentException(WEEKEND_ERROR_MESSAGE);
        }
    }

    private LocalDateTime validatePresentTime() {
        String presentTime = inputView.readPresentTime();
        validateTimeFormat(presentTime);
        return createLocalDateTime(LOCAL_DATE_TODAY, presentTime);
    }

    private void validateTimeFormat(final String presentTime) {
        if (!presentTime.matches(TIME_PATTERN)) {
            throw new IllegalArgumentException((TIME_FORMAT_ERROR_MESSAGE));
        }
    }
}
