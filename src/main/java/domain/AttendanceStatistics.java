package domain;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class AttendanceStatistics {

    private static final int COUNT_START_YEAR = 2024;
    private static final int COUNT_START_MONTH = 12;
    private static final int COUNT_START_DAY = 1;

    public static StatisticsResult countStatus(LocalDate nowDate, Records records) {
        int attendanceCount = 0;
        int latenessCount = 0;
        int absenceCount = 0;

        LocalDate startDate = LocalDate.of(COUNT_START_YEAR, COUNT_START_MONTH, COUNT_START_DAY);
        while (startDate.isBefore(nowDate)) {
            TimeAndStatus status = records.findByDate(startDate);
            startDate = startDate.plusDays(1);

            if (!Holiday.isHoliday(startDate.minusDays(1))) {
                if (status == null || status.getStatus() == null) {
                    absenceCount++;
                    continue;
                }

                AttendanceStatus attendanceStatus = status.getStatus();

                if (attendanceStatus == AttendanceStatus.ATTENDANCE) {
                    attendanceCount++;
                }
                if (attendanceStatus == AttendanceStatus.LATENESS) {
                    latenessCount++;
                }
                if (attendanceStatus == AttendanceStatus.ABSENCE) {
                    absenceCount++;
                }
            }
        }
        return new StatisticsResult(attendanceCount, latenessCount, absenceCount);
    }

    public static Map<String, StatisticsResult> calculateExpelledWarning(LocalDate nowDate,
                                                                         Map<String, Records> crews) {
        Map<String, StatisticsResult> result = new LinkedHashMap<>();
        for (String crewName : crews.keySet()) {
            StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, crews.get(crewName));

            if (statisticsResult.hasPenalty()) {
                result.put(crewName, statisticsResult);
            }
        }
        return result;
    }
}
