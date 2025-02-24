package attendance.domain.attendanceManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import attendance.domain.StatusStatistic;
import attendance.domain.attendance.AttendanceBook;

public class StatisticManger extends AttendanceManager {
    public StatisticManger(AttendanceBook attendanceBook) {
        super(attendanceBook);
    }

    @Override
    public void manage(String nickname, LocalDate date, LocalTime time) {
        var attendanceList = attendanceBook.getAttendanceList(nickname);

        StatusStatistic statistic = StatusStatistic.of(attendanceList, nickname);
        List<String> history = attendanceList.getHistory();

        writeReport(nickname, history, statistic);
    }

    @Override
    public String getResult() {
        return builder.toString();
    }

    private void writeReport(String nickname, List<String> history, StatusStatistic statistic) {
        builder.append(Message.INITIALIZE.getMessage(nickname));
        history.forEach(builder::append);

        builder.append(statistic.getReportDetail());

        StatusStatistic.SanctionLevel level = statistic.judgeSanctionLevel();
        if (level.equals(StatusStatistic.SanctionLevel.NONE)) {
            return;
        }
        builder.append(Message.SANCTION_LEVEL.getMessage(statistic.judgeSanctionLevel().getValues()));
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
