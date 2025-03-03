package dto;

import java.util.List;
import java.util.Map;
import model.Attendance;
import model.AttendanceType;
import model.PunishmentType;

public record AttendanceHistoryResponse(
        String nickname,
        List<Attendance> attendances,
        Map<AttendanceType, Integer> attendanceTotal,
        PunishmentType punishmentType
) {
}
