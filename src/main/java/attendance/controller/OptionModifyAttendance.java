package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceType;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.MenuCommand;
import attendance.dto.AttendanceModifyDto;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OptionModifyAttendance extends MenuOption {
    private static final String DATE_FORMAT_ERROR_MESSAGE = "[ERROR] 올바른 날짜 형식을 입력해주세요.";
    private static final String DATE_PATTERN = "^([1-2][0-8])|([1-9])$";
    private static final int YEAR_VALUE = 2025;
    private static final int MONTH_VALUE = 2;

    public OptionModifyAttendance(InputView inputView, OutputView outputView, Crews crews, Attendances attendances) {
        super(inputView, outputView, crews, attendances);
    }

    @Override
    public void executeMenuOption(MenuCommand command) {
        executeModifyAttendance();
    }

    private void executeModifyAttendance() {
        Crew crew = findCrewByCrewName();
        LocalDate localDate = validateModifyDate();
        String originalTime = attendances.findOriginalTime(crew, localDate);
        AttendanceType originalType = attendances.findOriginalType(crew, localDate);
        String modifyTime = inputView.readModifyTime();
        LocalDateTime localDateTime = createLocalDateTime(localDate, modifyTime);
        attendances.modifyAttendances(crew, localDateTime);
        Attendance newAttendance = attendances.findMatchCrewDate(crew, localDate);
        AttendanceModifyDto modifyAttendanceInfo = AttendanceModifyDto.fromModifiedAttendance(originalTime,
                originalType, newAttendance);
        outputView.printModifiedAttendance(modifyAttendanceInfo);
    }

    private LocalDate validateModifyDate() {
        String date = inputView.readModifyDate();
        validateDateFormat(date);
        return LocalDate.of(YEAR_VALUE, MONTH_VALUE, Integer.parseInt(date));
    }

    private void validateDateFormat(final String date) {
        if (!date.matches(DATE_PATTERN)) {
            throw new IllegalArgumentException(DATE_FORMAT_ERROR_MESSAGE);
        }
    }
}
