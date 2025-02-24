package attendance.domain.attendanceManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import attendance.domain.StatusStatistic;
import attendance.domain.attendance.AttendanceBook;

public class SanctionManager extends AttendanceManager {
    public static final String INIT_MESSAGE = "\n제적 위험자 조회 결과\n";

    public SanctionManager(AttendanceBook attendanceBook) {
        super(attendanceBook);
    }

    @Override
    public void manage(String nickname, LocalDate date, LocalTime time) {
        attendanceBook.updateStatusStatistics();
    }

    @Override
    public String getResult() {
        builder.append(INIT_MESSAGE);
        List<String> reportDetails = attendanceBook.statusStatistics()
            .stream()
            .map(StatusStatistic::getReportSanctions)
            .toList();
        for (String reportDetail : reportDetails) {
            builder.append(reportDetail);
        }
        return builder.toString();
    }
}
