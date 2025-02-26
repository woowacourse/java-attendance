package attendance.domain.attendanceManager;

import attendance.domain.StatusStatistic;
import attendance.domain.attendance.AttendanceBook;

public class SanctionManager {
    public static final String INIT_MESSAGE = "\n제적 위험자 조회 결과\n";

    private final AttendanceBook attendanceBook;
    private final StringBuilder stringBuilder = new StringBuilder();

    public SanctionManager(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public void manage() {
        // attendanceBook.updateStatusStatistics();
    }

    public String getResult() {
        stringBuilder.append(INIT_MESSAGE);

        attendanceBook.statusStatistics()
            .stream()
            .map(StatusStatistic::getReportSanctions)
            .forEach(stringBuilder::append);

        return stringBuilder.toString();
    }
}
