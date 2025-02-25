package attendance.domain.attendanceManager;

import java.util.List;

import attendance.domain.StatusStatistic;
import attendance.domain.attendance.AttendanceBook;

public class StatisticManger {
    private final AttendanceBook attendanceBook;
    private final StringBuilder report = new StringBuilder();

    public StatisticManger(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public void manage(String nickname) {
        var attendanceList = attendanceBook.getAttendanceList(nickname);

        StatusStatistic statistic = StatusStatistic.of(attendanceList, nickname);
        List<String> history = attendanceList.getHistory();

        writeReport(nickname, history, statistic);
    }

    public String getResult() {
        return report.toString();
    }

    private void writeReport(String nickname, List<String> history, StatusStatistic statistic) {
        report.append(Message.INITIALIZE.getMessage(nickname));
        history.forEach(report::append);

        report.append(statistic.getReportDetail());

        StatusStatistic.SanctionLevel level = statistic.judgeSanctionLevel();
        if (level.equals(StatusStatistic.SanctionLevel.NONE)) {
            return;
        }
        report.append(Message.SANCTION_LEVEL.getMessage(statistic.judgeSanctionLevel().getValues()));
    }

    protected enum Message {
        INITIALIZE("이번 달 %s의 출석 기록입니다.\n"),
        SANCTION_LEVEL("\n%s 대상자입니다.\n");
        private final String message;

        Message(String message) {
            this.message = message;
        }

        public String getMessage(Object... args) {
            return String.format(message, args);
        }
    }
}
