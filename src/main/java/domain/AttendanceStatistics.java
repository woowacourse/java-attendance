package domain;

import java.time.LocalDate;

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

            if (status == null || status.getStatus() == null) {
                continue;
            }

            if (status.getStatus().equals("출석")) {
                attendanceCount++;
            }
            if (status.getStatus().equals("지각")) {
                latenessCount++;
            }
            if (status.getStatus().equals("결석")) {
                absenceCount++;
            }
        }
        return new StatisticsResult(attendanceCount, latenessCount, absenceCount);
    }
}
