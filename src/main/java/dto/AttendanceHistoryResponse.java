package dto;

import java.util.EnumMap;
import java.util.List;
import model.Attendance;
import model.AttendanceType;
import model.PunishmentType;

public record AttendanceHistoryResponse(
        String nickname,
        List<Attendance> attendances,
        EnumMap<AttendanceType, Integer> attendanceTotal,
        PunishmentType punishmentType
) {
}
