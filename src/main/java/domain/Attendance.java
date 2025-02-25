package domain;

import dto.AttendanceResultDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Attendance {

    private final Map<Crew, List<LocalDateTime>> attendances = new LinkedHashMap<>();

    public Attendance() {
    }

    public void addAttendance(final Crew crew, final LocalDateTime localDateTime) {
        List<LocalDateTime> localDateTimes = attendances.getOrDefault(crew, new ArrayList<>());
        localDateTimes.add(localDateTime);
        attendances.put(crew, localDateTimes);
    }

    public Crew getCrewByName(String name) {
        return attendances.keySet().stream().filter(crew -> crew.isSame(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루 입니다."));
    }

    public void save(final Crew crew, final LocalDateTime attendanceTime) {
        List<LocalDateTime> localDateTimes = attendances.get(crew);

        validateDuplicateSave(attendanceTime.getDayOfMonth(), localDateTimes);
        localDateTimes.add(attendanceTime);
        attendances.put(crew, localDateTimes);
    }

    public LocalDateTime update(final Crew crew, final LocalDateTime updateTime) {
        Calender.validateHolyDay(updateTime.getDayOfMonth());

        List<LocalDateTime> localDateTimes = attendances.get(crew);
        LocalDateTime beforeRecord = findBeforeRecord(updateTime, localDateTimes);

        updateRecord(localDateTimes, updateTime);

        return beforeRecord;
    }

    private LocalDateTime findBeforeRecord(final LocalDateTime updateTime, final List<LocalDateTime> localDateTimes) {
        return localDateTimes.stream()
                .filter(attendanceTime -> attendanceTime.toLocalDate().equals(updateTime.toLocalDate())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("수정 가능한 출석 기록이 존재하지 않습니다."));
    }

    private void updateRecord(final List<LocalDateTime> localDateTimes, final LocalDateTime updateTime) {
        for (int attendanceRecordIndex = 0; attendanceRecordIndex < localDateTimes.size(); attendanceRecordIndex++) {
            LocalDateTime localDateTime = localDateTimes.get(attendanceRecordIndex);

            if (localDateTime.toLocalDate().equals(updateTime.toLocalDate())) {
                localDateTimes.set(attendanceRecordIndex, updateTime);
                break;
            }
        }
    }

    public List<AttendanceResultDto> readRecord(final Crew crew, int todayDay) {
        List<LocalDateTime> localDateTimes = attendances.get(crew);
        sortRecord(localDateTimes);

        List<AttendanceResultDto> attendanceResultDtos = new ArrayList<>();
        checkRecord(todayDay, localDateTimes, attendanceResultDtos);
        return attendanceResultDtos;
    }

    public Map<Crew, AbsenceHistory> getAbsence(final int todayDay) {
        Map<Crew, AbsenceHistory> absenceMap = new HashMap<>();
        for (Crew crew : attendances.keySet()) {
            List<AttendanceResultDto> attendanceResultDtos = readRecord(crew, todayDay);

            AbsenceHistory absenceHistory = AbsenceHistory.calculate(attendanceResultDtos);
            absenceMap.put(crew, absenceHistory);
        }
        return absenceMap;
    }

    private void validateDuplicateSave(final int todayDay, final List<LocalDateTime> localDateTimes) {
        for (LocalDateTime localDateTime : localDateTimes) {
            int dayOfMonth = localDateTime.getDayOfMonth();

            if (dayOfMonth == todayDay) {
                throw new IllegalArgumentException("이미 출석한 크루입니다.");
            }
        }
    }

    private void sortRecord(final List<LocalDateTime> localDateTimes) {
        localDateTimes.sort(Comparator.comparing((LocalDateTime::getDayOfMonth)));
    }

    private void checkRecord(final int todayDay, final List<LocalDateTime> localDateTimes,
                             final List<AttendanceResultDto> attendanceResultDtos) {
        int idx = 0;
        for (int dayIndex = 1; dayIndex < todayDay; dayIndex++) {
            if (Calender.isHolyDay(dayIndex)) {
                continue;
            }
            if (hasRecord(idx, localDateTimes, dayIndex)) {
                LocalDateTime localDateTime = localDateTimes.get(idx++);
                insertRecord(localDateTime, dayIndex, attendanceResultDtos);
                continue;
            }
            checkAbsence(dayIndex, attendanceResultDtos);
        }
    }

    private boolean hasRecord(final int idx, final List<LocalDateTime> localDateTimes, final int dayIndex) {
        return idx < localDateTimes.size() && localDateTimes.get(idx).getDayOfMonth() == dayIndex;
    }

    private void insertRecord(final LocalDateTime localDateTime, final int dayIndex,
                              final List<AttendanceResultDto> attendanceResultDtos) {
        AttendanceState state = AttendanceState.findStateBy(localDateTime.toLocalTime(), dayIndex);
        attendanceResultDtos.add(new AttendanceResultDto(localDateTime, state));
    }

    private void checkAbsence(final int dayIndex, final List<AttendanceResultDto> attendanceResultDtos) {
        AttendanceState state = AttendanceState.ABSENCE;
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, dayIndex, 0, 0);
        AttendanceResultDto attendanceResultDto = new AttendanceResultDto(newLocalDateTime, state);
        attendanceResultDtos.add(attendanceResultDto);
    }

    public Map<Crew, List<LocalDateTime>> getAttendances() {
        return attendances;
    }
}
