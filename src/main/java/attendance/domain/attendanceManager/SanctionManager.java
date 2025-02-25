package attendance.domain.attendanceManager;

import java.util.List;

import attendance.domain.StatusStatistic;
import attendance.domain.attendance.AttendanceBook;

public class SanctionManager {
    public static final String INIT_MESSAGE = "\n제적 위험자 조회 결과\n";

    private final AttendanceBook attendanceBook;
    private final StringBuilder report = new StringBuilder();

    public SanctionManager(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public void manage() {
        attendanceBook.updateStatusStatistics();
    }

    public String getResult() {
        report.append(INIT_MESSAGE);
        List<String> reportDetails = attendanceBook.statusStatistics()
            .stream()
            .map(StatusStatistic::getReportSanctions)
            .toList();

        for (String reportDetail : reportDetails) {
            report.append(reportDetail);
        }
        return report.toString();
    }
}
