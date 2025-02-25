package attendance.domain;

import attendance.exception.AttendanceArgumentException;
import attendance.view.dto.RequestModifyAttendanceDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceManager {

    private static final LocalTime SCHOOL_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime SCHOOL_CLOSE_TIME = LocalTime.of(23, 0);
    private static final String OUT_OF_SCHOOL_SCHEDULE = "등교시간에만 출석 가능합니다.";
    private static final LocalDate SYSTEM_AVAILABLE_START_DATE = LocalDate.of(2024, 12, 1);
    private static final LocalDate SYSTEM_AVAILABLE_END_DATE = LocalDate.of(2024, 12, 31);
    private static final String CREW_NOT_EXISTS = "출석 정보가 존재하지 않습니다.";
    private static final String ATTENDANCE_NOT_AVAILABLE = "출석 시스템은 2024년 12월 동안만 유효합니다";
    private static final int WEEKEND_NUMBER = 6;

    private final HashMap<CrewName, Attendances> attendanceManager = new HashMap<>();
    private final int XMAS_MONTH = 12;
    private final int XMAS_DAY = 25;

    public AttendanceManager() {
    }

    public AttendanceManager(List<AttendanceRequest> attendanceRequests) {
        loadAttendances(attendanceRequests);
    }

    private void loadAttendances(List<AttendanceRequest> attendanceRequests) {
        for (AttendanceRequest attendanceRequest : attendanceRequests) {
            addAttendance(attendanceRequest.crewName(), attendanceRequest.attendanceDateTime());
        }
    }

    public void addAttendance(String crewName, LocalDateTime time) {
        addAttendance(createCrewName(crewName), time);
    }

    public void addAttendance(CrewName crewName, LocalDateTime time) {
        LocalTime currentTime = time.toLocalTime();
        LocalDate currentDate = time.toLocalDate();
        validateIsSchoolOpen(currentTime);
        validateIsAttendanceAvailable(currentDate);
        Attendances attendances = findAttendancesByCrewName(crewName);
        attendanceManager.put(crewName, attendances);
        attendances.addAttendance(currentTime, currentDate);
    }

    private Attendances findAttendancesByCrewName(CrewName crewName) {
        return attendanceManager.getOrDefault(crewName, new Attendances());
    }

    private static CrewName createCrewName(String nickname) {
        CrewName crewName = CrewName.from(nickname);
        return crewName;
    }

    public Attendance findAttendance(String nickname, LocalDate date) {
        Attendances attendances = attendanceManager.getOrDefault(CrewName.from(nickname), new Attendances());
        return attendances.getAttendance(date);
    }

    public void validateIsSchoolOpen(LocalTime currentTime) {
        if (currentTime.isBefore(SCHOOL_OPEN_TIME) || currentTime.isAfter(SCHOOL_CLOSE_TIME)) {
            throw new AttendanceArgumentException(OUT_OF_SCHOOL_SCHEDULE);
        }
    }

    public void modifyAttendance(String nickname, LocalDate modifyDate, LocalTime afterModifyTime) {
        CrewName crewName = createCrewName(nickname);
        validateAttendanceExist(crewName);
        Attendances attendances = findAttendancesByCrewName(crewName);
        attendances.modifyAttendance(modifyDate, afterModifyTime);
    }

    public void validateAttendanceExist(String nickname) {
        validateAttendanceExist(CrewName.from(nickname));
    }


    public void validateAttendanceExist(CrewName crewName) {
        Attendances attendances = attendanceManager.get(crewName);
        if (attendances == null) {
            throw new AttendanceArgumentException(CREW_NOT_EXISTS);
        }
    }

    public void validateIsAttendanceAvailable(LocalDate currentDate) {
        validateIsAttendanceWeekend(currentDate);
        if (SYSTEM_AVAILABLE_START_DATE.isAfter(currentDate) || SYSTEM_AVAILABLE_END_DATE.isBefore(
                currentDate)) {
            throw new AttendanceArgumentException(ATTENDANCE_NOT_AVAILABLE);
        }
    }

    private void validateIsAttendanceWeekend(LocalDate currentDate) {
        String CANNOT_ATTENDANCE_ON_HOLIDAY = DateTimeFormatterWrapper.formattingAttendanceDateError(currentDate);
        if (isHoliday(currentDate)) {
            throw new AttendanceArgumentException(CANNOT_ATTENDANCE_ON_HOLIDAY);
        }
    }

    public boolean isHoliday(LocalDate currentDate) {
        int month = currentDate.getMonth().getValue();
        int dayOfMonth = currentDate.getDayOfMonth();
        int dayOfWeekend = currentDate.getDayOfWeek().getValue();
        if (month == XMAS_MONTH && dayOfMonth == XMAS_DAY) {
            return true;
        }
        if (dayOfWeekend >= WEEKEND_NUMBER) {
            return true;
        }
        return false;
    }

    public CrewAttendanceHistory crewAttendanceHistory(String nickname) {
        validateCrewNameExist(nickname);
        LocalDate startDate = SYSTEM_AVAILABLE_START_DATE;
        LocalDate endDate = SYSTEM_AVAILABLE_END_DATE;
        AttendanceStatuses attendanceStatuses = new AttendanceStatuses();
        AttendanceHistories attendanceHistories = new AttendanceHistories();
        Attendances attendances = attendanceManager.get(CrewName.from(nickname));
        for (LocalDate currentDate = startDate; !currentDate.isAfter(endDate); currentDate = currentDate.plusDays(1)) {
            appendAttendanceHistories(attendances, currentDate, attendanceStatuses, attendanceHistories);
        }
        return new CrewAttendanceHistory(nickname, attendanceHistories, attendanceStatuses);
    }

    private void validateCrewNameExist(String nickname) {
        CrewName crewName = CrewName.from(nickname);
        validateAttendanceExist(crewName);
    }

    private void appendAttendanceHistories(Attendances attendances, LocalDate currentDate,
                                           AttendanceStatuses attendanceHistories,
                                           AttendanceHistories attendanceStatusMap) {
        if (isHoliday(currentDate)) {
            return;
        }
        if (attendances.isAttendanceExist(currentDate)) {
            addAttendanceHistory(attendances, currentDate, attendanceHistories, attendanceStatusMap);
            return;
        }
        addAbsenceHistory(attendanceHistories, attendanceStatusMap, currentDate);
    }

    private void addAbsenceHistory(AttendanceStatuses attendanceStatues,
                                   AttendanceHistories attendanceHistories, LocalDate currentDate) {
        attendanceHistories.add(LocalDateTime.of(currentDate, LocalTime.MIN), AttendanceStatus.ABSENCE);
        attendanceStatues.mergeStatus(AttendanceStatus.ABSENCE);
    }

    private void addAttendanceHistory(Attendances attendances, LocalDate currentDate,
                                      AttendanceStatuses attendanceStatues,
                                      AttendanceHistories attendanceHistories) {
        LocalTime attendanceTime = attendances.getAttendanceTime(currentDate);
        AttendanceStatus attendanceStatus = attendances.getAttendanceStatus(currentDate);
        attendanceHistories.add(LocalDateTime.of(currentDate, attendanceTime), attendanceStatus);
        attendanceStatues.mergeStatus(attendanceStatus);
    }

    public List<String> attendancesNicknames() {
        return attendanceManager.keySet()
                .stream()
                .map((crewName) -> crewName.getName())
                .toList();
    }

    public List<CrewAttendanceHistory> crewDismissHistory() {
        return attendancesNicknames()
                .stream()
                .map(this::crewAttendanceHistory)
                .collect(Collectors.toList());
    }

    public void modifyAttendance(RequestModifyAttendanceDto requestModifyAttendanceDto) {
        CrewName crewName = createCrewName(requestModifyAttendanceDto.nickname());
        validateAttendanceExist(crewName);
        Attendances attendances = attendanceManager.get(crewName);
        attendances.modifyAttendance(requestModifyAttendanceDto.date(), requestModifyAttendanceDto.time());
    }
}


