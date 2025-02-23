package attendance.domain;

public class AttendanceManager {

    public boolean findAttendance(String nickname) {
        return false;
    }

    public void addAttendance(String nickname, Attendance attendance) {
    }

    // private static AttendanceManager instance = null;
    //
    // private AttendanceManager() {
    // }
    //
    // public static AttendanceManager getInstance() {
    //     if (instance == null) {
    //         instance = new AttendanceManager();
    //     }
    //     return instance;
    // }
    //
    // public static void initiateInstance() {
    //     instance = null;
    // }
    //
    // private static final String ATTENDANCE_RESULT_FORMAT = "%s (%s)";
    //
    // private HashMap<String, Attendances> attendanceManager = new HashMap<>();
    //
    // public void addAttendance(String nickname, LocalDateTime time) {
    //     validateNickname(nickname);
    //     LocalTime currentTime = time.toLocalTime();
    //     LocalDate currentDate = time.toLocalDate();
    //     validateIsSchoolOpen(currentTime);
    //     validateAttendanceAvailable(currentDate);
    //     Attendances attendances = attendanceManager.getOrDefault(nickname, new Attendances());
    //     attendanceManager.put(nickname, attendances);
    //     attendances.addAttendance(currentTime, currentDate);
    // }
    //
    // private void validateAttendanceAvailable(LocalDate currentDate) {
    //     if (currentDate.getDayOfWeek().getValue() >= AttendanceManagerHelper.WEEKEND_NUMBER) {
    //         throw new AttendanceArgumentException(
    //             DateTimeFormatterWrapper.formattingAttendanceWeekendError(currentDate));
    //     }
    //     LocalDate attendanceAvailableStartDate = AttendanceManagerHelper.ATTENDANCE_AVAILABLE_START_DATE;
    //     LocalDate attendanceAvailableEndDate = AttendanceManagerHelper.ATTENDANCE_AVAILABLE_END_DATE;
    //     if (attendanceAvailableEndDate.isAfter(currentDate) || attendanceAvailableStartDate.isBefore(currentDate)) {
    //         return;
    //     }
    //     throw new AttendanceArgumentException(AttendanceManagerHelper.ATTENDANCE_NOT_AVAILABLE);
    // }
    //
    // public void validateIsSchoolOpen(LocalTime currentTime) {
    //     if (currentTime.isBefore(AttendanceManagerHelper.SCHOOL_OPEN_TIME) || currentTime.isAfter(
    //         AttendanceManagerHelper.SCHOOL_CLOSE_TIME)) {
    //         throw new AttendanceArgumentException(AttendanceManagerHelper.OUT_OF_SCHOOL_SCHEDULE);
    //     }
    // }
    //
    // public Attendances findAttendances(String nickname) {
    //     Attendances attendances = attendanceManager.get(nickname);
    //     if (attendances == null) {
    //         throw new AttendanceArgumentException(AttendanceManagerHelper.NICKNAME_NOT_EXISTS);
    //     }
    //     return attendances;
    // }
    //
    // public void validateNickname(String nickname) {
    //     if (StringUtility.isEmpty(nickname)) {
    //         throw new AttendanceArgumentException(AttendanceManagerHelper.CANNOT_BE_EMPTY_NICKNAME);
    //     }
    // }
    //
    // public void modifyAttendance(String nickname, LocalDate modifyDate, LocalTime afterModifyTime) {
    //     validateAttendanceExist(nickname);
    //     Attendances attendances = attendanceManager.get(nickname);
    //     attendances.modifyAttendance(modifyDate, afterModifyTime);
    // }
    //
    // private void validateAttendanceExist(String nickname) {
    //     Attendances attendances = attendanceManager.get(nickname);
    //     if (attendances == null) {
    //         throw new AttendanceArgumentException(AttendanceManagerHelper.NICKNAME_NOT_EXISTS);
    //     }
    // }
    //
    // public void validateIsAttendanceAvailable(LocalDate currentDate) {
    //     String ATTENDANCE_WEEKEND_ERROR = DateTimeFormatterWrapper.formattingAttendanceWeekendError(currentDate);
    //     if (currentDate.getDayOfWeek().getValue() >= AttendanceManagerHelper.WEEKEND_NUMBER) {
    //         throw new AttendanceArgumentException(ATTENDANCE_WEEKEND_ERROR);
    //     }
    //     if (currentDate.getMonth().getValue() == 12 && currentDate.getDayOfMonth() == 25) {
    //         throw new AttendanceArgumentException(ATTENDANCE_WEEKEND_ERROR);
    //     }
    // }
    //
    // public AttendanceHistory crewAttendanceHistory(String nickname) {
    //     validateCrewNameExist(nickname);
    //     LocalDate startDate = AttendanceManagerHelper.ATTENDANCE_AVAILABLE_START_DATE;
    //     LocalDate endDate = AttendanceManagerHelper.ATTENDANCE_AVAILABLE_END_DATE;
    //     List<String> attendanceHistories = new ArrayList<>();
    //     Map<AttendanceStatus, Integer> attendanceStatusMap = new HashMap<>();
    //     Attendances attendances = attendanceManager.get(nickname);
    //     for (LocalDate currentDate = startDate; !currentDate.isAfter(endDate); currentDate = currentDate.plusDays(1)) {
    //         appendAttendanceHistories(attendances, currentDate, attendanceHistories, attendanceStatusMap);
    //     }
    //     return new AttendanceHistory(attendanceHistories, attendanceStatusMap);
    // }
    //
    // private void validateCrewNameExist(String nickname) {
    //     validateNickname(nickname);
    //     validateAttendanceExist(nickname);
    // }
    //
    // private boolean isAttendanceAvailable(LocalDate currentDate) {
    //     try {
    //         validateIsAttendanceAvailable(currentDate);
    //         return true;
    //     } catch (AttendanceArgumentException e) {
    //         return false;
    //     }
    // }
    //
    // private void appendAttendanceHistories(Attendances attendances, LocalDate currentDate,
    //     List<String> attendanceHistories,
    //     Map<AttendanceStatus, Integer> attendanceStatusMap) {
    //     if (!isAttendanceAvailable(currentDate)) {
    //         return;
    //     }
    //     if (!isAttendanceExistInDate(attendances, currentDate)) {
    //         addAbsenceHistory(attendanceHistories, attendanceStatusMap, currentDate);
    //         return;
    //     }
    //     addAttendanceHistory(attendances, currentDate, attendanceHistories, attendanceStatusMap);
    // }
    //
    // private boolean isAttendanceExistInDate(Attendances attendances, LocalDate currentDate) {
    //     try {
    //         attendances.validateIsExistAttendanceHistory(currentDate);
    //         return true;
    //     } catch (AttendanceArgumentException e) {
    //         return false;
    //     }
    // }
    //
    // private void addAbsenceHistory(List<String> attendanceHistories,
    //     Map<AttendanceStatus, Integer> attendanceStatusMap, LocalDate currentDate) {
    //     String absenceHistory = DateTimeFormatterWrapper.formattingAttendanceAbsenceHistory(currentDate);
    //     attendanceHistories.add(absenceHistory);
    //     attendanceStatusMap.merge(AttendanceStatus.ABSENCE, 1, Integer::sum);
    // }
    //
    // private void addAttendanceHistory(Attendances attendances, LocalDate currentDate,
    //     List<String> attendanceHistories,
    //     Map<AttendanceStatus, Integer> attendanceStatusMap) {
    //     LocalTime attendanceTime = attendances.getAttendanceTime(currentDate);
    //     AttendanceStatus attendanceStatus = attendances.getAttendanceStatus(currentDate);
    //
    //     attendanceStatusMap.merge(attendanceStatus, 1, Integer::sum);
    //
    //     String dateTimeFormatResult = DateTimeFormatterWrapper.parsingAttendanceResult(
    //         LocalDateTime.of(currentDate, attendanceTime));
    //     attendanceHistories.add(
    //         String.format(ATTENDANCE_RESULT_FORMAT, dateTimeFormatResult, attendanceStatus.getStatus()));
    // }
    //
    // public List<String> currentAttendancesNicknames() {
    //     return attendanceManager.keySet().stream().toList();
    // }
}
