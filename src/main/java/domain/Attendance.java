package domain;

import dto.AbsenceResultDto;
import dto.AttendanceResultDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Attendance {

    private final Map<Crew, List<LocalDateTime>> attendanceMap;

    public Attendance(final Map<Crew, List<LocalDateTime>> attendanceMap) {
        this.attendanceMap = attendanceMap;
    }

    public Crew getCrewByName(String name) {
        return attendanceMap.keySet()
                .stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Map<Crew, List<LocalDateTime>> getAttendanceMap() {
        return attendanceMap;
    }

    public void save(final Crew crew, final String schoolStartTime, final int todayDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<LocalDateTime> localDateTimes = attendanceMap.get(crew);

        String today = String.format("2024-12-%02d %s", todayDay, schoolStartTime);

        LocalDateTime todayLocalDateTime = LocalDateTime.parse(today, formatter);

        localDateTimes.add(todayLocalDateTime);
        attendanceMap.put(crew, localDateTimes);
    }

    public LocalDateTime update(final Crew crew, final String updateTime, final int date) {
        List<LocalDateTime> localDateTimes = attendanceMap.get(crew);
        int i;
        LocalDateTime beforeLocalDateTime = null;
        for (i = 0; i < localDateTimes.size(); i++) {
            LocalDateTime localDateTime = localDateTimes.get(i);
            int dayOfMonth = localDateTime.getDayOfMonth();
            if (dayOfMonth == date) {
                beforeLocalDateTime = localDateTime;
                break;
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String today = String.format("2024-12-%02d %s", date, updateTime);
        LocalDateTime todayLocalDateTime = LocalDateTime.parse(today, formatter);

        localDateTimes.set(i, todayLocalDateTime);

        return beforeLocalDateTime;
    }

    public List<AttendanceResultDto> readRecord(final Crew crew, int todayDay) {
        List<LocalDateTime> localDateTimes = attendanceMap.get(crew); //해당 크루의 출석 기록
        localDateTimes.sort(Comparator.comparing((LocalDateTime::getDayOfMonth)));

        List<AttendanceResultDto> attendanceResultDtos = new ArrayList<>();

        int idx = 0;
        for (int dayIndex = 1; dayIndex < todayDay; dayIndex++) {
            if (Calender.findBy(dayIndex).equals("공휴일")) {
                continue;
            }

            LocalDateTime localDateTime = null;
            try {
                localDateTime = localDateTimes.get(idx);
            } catch (IndexOutOfBoundsException e) {
                checkAbsence(dayIndex, attendanceResultDtos);
                continue;
            }

            int dayOfMonth = localDateTime.getDayOfMonth();

            if (dayIndex == dayOfMonth) {
                String state = AttendanceState.findStateBy(localDateTime.toLocalTime(), dayOfMonth);
                AttendanceResultDto attendanceResultDto = new AttendanceResultDto(localDateTime, state);
                attendanceResultDtos.add(attendanceResultDto);
                idx++;
                continue;
            }
            checkAbsence(dayIndex, attendanceResultDtos);
        }
        return attendanceResultDtos;
    }

    private void checkAbsence(final int dayIndex, final List<AttendanceResultDto> attendanceResultDtos) {
        String state = "결석";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, dayIndex, 0, 0);
        AttendanceResultDto attendanceResultDto = new AttendanceResultDto(newLocalDateTime, state);
        attendanceResultDtos.add(attendanceResultDto);
    }
}
