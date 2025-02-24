package attendance.domain.attendanceManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import attendance.domain.AttendanceStatus;
import attendance.domain.attendanceBook.AttendanceBook;

public class AttendanceStatistician extends AttendanceManager {
    private Map<AttendanceStatus, Integer> statistic;

    public AttendanceStatistician(AttendanceBook attendanceBook) {
        super(attendanceBook);
    }

    @Override
    public void manage(String nickname, LocalDate date, LocalTime time) {
        var attendanceList = attendanceBook.getAttendanceList(nickname);
        statistic = attendanceList.produceStatistic();
        List<String> history = attendanceList.getHistory();

        builder.append(Message.INITIALIZE.getMessage(nickname));
        history.forEach(builder::append);
    }

    protected enum Message {
        INITIALIZE("이번 달 %s의 출석 기록입니다."),
        STATE_TOTAL("%s %d회"),
        SANCTION_LEVEL("%s 대상자입니다");
        private final String message;

        Message(String message) {
            this.message = message;
        }

        public String getMessage(Object... args) {
            return String.format(message, args);
        }
    }
}
