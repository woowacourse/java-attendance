import domain.AttendanceBook;
import domain.Attendances;
import java.util.Map;

public class PenaltyCheck {
    public Map<String, Attendances> getPenaltyHistory(AttendanceBook attendanceBook) {
        return attendanceBook.getPenaltyHistory();
    }
}
