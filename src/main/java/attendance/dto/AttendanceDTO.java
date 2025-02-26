//package attendance.dto;
//
//import attendance.model.AttendanceDate;
//import attendance.model.AttendanceDateTime;
//import attendance.model.AttendanceHistory;
//import attendance.model.WoowaDurationTime;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.stream.IntStream;
//
//public record AttendanceDTO(
//        String crewName,
//        List<AttendanceDetailDTO> attendanceDetailDTOs,
//        String warningType,
//        long attendanceCount,
//        long lateCount,
//        long absenceCount
//) {
//
//    public static final int DURING_YEAR = 2024;
//    public static final int LAST_DAY = 31;
//
//    public static AttendanceDTO from(String crewName, AttendanceHistory attendanceHistory) {
//        return new AttendanceDTO(
//                crewName,
//                IntStream.range(1, computeLastAttendanceDate())
//                        .filter(AttendanceDTO::isDurationDay)
//                        .mapToObj(day -> new AttendanceDate(LocalDate.of(2024, 12, day)))
//                        .map(attendanceDate -> generateAttendanceDetailDTO(attendanceHistory, attendanceDate))
//                        .toList(),
//                attendanceHistory.getAttendanceWarning().name(),
//                attendanceHistory.computeAttendanceCount(),
//                attendanceHistory.computeLateCount(),
//                attendanceHistory.computeAbsenceCount()
//        );
//    }
//
//    private static AttendanceDetailDTO generateAttendanceDetailDTO(
//            AttendanceHistory attendanceHistory,
//            AttendanceDate attendanceDate
//    ) {
//        if (attendanceHistory.containsAttendance(attendanceDate)) {
//            return AttendanceDetailDTO.fromArriveAttendance(
//                    attendanceHistory.findAttendanceDateTime(attendanceDate)
//            );
//        }
//        return AttendanceDetailDTO.fromNonArriveAttendance(attendanceDate.localDate());
//    }
//
//    private static boolean isDurationDay(int day) {
//        return WoowaDurationTime.isDurationDate(LocalDate.of(2024, 12, day));
//    }
//
//    private static int computeLastAttendanceDate() {
//        LocalDate now = LocalDate.now();
//        if (now.getYear() > DURING_YEAR) {
//            return LAST_DAY;
//        }
//        return now.getDayOfMonth();
//    }
//
//    public record AttendanceDetailDTO(
//            LocalDate attendanceDate,
//            LocalTime attendanceTime,
//            String attendanceType
//    ) {
//        public static AttendanceDetailDTO fromArriveAttendance(AttendanceDateTime attendanceDateTime) {
//            return new AttendanceDetailDTO(
//                    attendanceDateTime.getAttendanceDate().localDate(),
//                    attendanceDateTime.getAttendanceTime().localTime(),
//                    attendanceDateTime.getAttendanceType().name()
//            );
//        }
//
//        public static AttendanceDetailDTO fromNonArriveAttendance(LocalDate localDate) {
//            return new AttendanceDetailDTO(
//                    localDate,
//                    null,
//                    "결석"
//            );
//        }
//    }
//
//}
