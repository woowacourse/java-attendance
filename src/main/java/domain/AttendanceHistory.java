package domain;

import java.time.LocalDateTime;
import java.util.List;

public class AttendanceHistory { // 한사람의 기록 조회하는거
    private final List<LocalDateTime> localDateTimes;

    public AttendanceHistory(List<LocalDateTime> localDateTimes) {
        this.localDateTimes = localDateTimes;
    }

    //getAttendanceRecords
//    public List<AttendanceHistoryDto> readRecord(List<LocalDateTime> localDateTimes) {
//        localDateTimes.sort(Comparator.comparing((l -> DateTimeUtil.getDateBy(l.toLocalDate()))));
//
//        List<AttendanceHistoryDto> attendanceHistoryDtos = new ArrayList<>();
//
//        int idx = 0;
//        for (int dayIndex = 1; dayIndex < DateTimeUtil.getDateBy(LocalDate.now()); dayIndex++) {
//            if (DateTimeUtil.isHoliday(LocalDate.of(2024, 12, dayIndex))) {
//                continue;
//            }
//
//            LocalDateTime localDateTime = null;
//            try {
//                localDateTime = localDateTimes.get(idx);
//            } catch (IndexOutOfBoundsException e) {
//                checkAbsence(dayIndex, attendanceHistoryDtos);
//                continue;
//            }
//
//            int dayOfMonth = DateTimeUtil.getDateBy(localDateTime.toLocalDate());
////            int dayOfMonth = localDateTime.getDayOfMonth();
//
//            if (dayIndex == dayOfMonth) {
//                AttendanceState state = AttendanceState.findStateBy(localDateTime.toLocalTime(),
//                        localDateTime.toLocalDate());
//                AttendanceHistoryDto attendanceHistoryDto = new AttendanceHistoryDto(localDateTime,
//                        state.getDescription());
//                attendanceHistoryDtos.add(attendanceHistoryDto);
//                idx++;
//                continue;
//            }
//            checkAbsence(dayIndex, attendanceHistoryDtos);
//        }
//        return attendanceHistoryDtos;
//    }
//
//    private void checkAbsence(final int dayIndex, final List<AttendanceHistoryDto> attendanceResultDtos) {
//        String state = AttendanceState.ABSENCE.getDescription();
//        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, dayIndex, 0, 0);
//        AttendanceHistoryDto attendanceHistoryDto = new AttendanceHistoryDto(newLocalDateTime, state);
//        attendanceResultDtos.add(attendanceHistoryDto);
//    }
//
    public int calculateAbsence() {
        return 0;
    }
}
