package domain.factory;

import domain.AttendanceBook;
import domain.AttendanceTime;
import domain.AttendanceTimes;
import domain.Crew;
import domain.CrewAttendance;
import domain.dto.AttendanceRecordDto;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBookFactory {
    public static AttendanceBook createAttendanceBook(List<AttendanceRecordDto> records) {
        Map<String, List<AttendanceTime>> groupedRecords = mapToAttendanceTimes(records);

        List<CrewAttendance> crewAttendances = groupedRecords.entrySet().stream()
                .map(entry -> createCrewAttendance(entry.getKey(), entry.getValue()))
                .toList();

        return AttendanceBook.of(crewAttendances);
    }

    private static Map<String, List<AttendanceTime>> mapToAttendanceTimes(List<AttendanceRecordDto> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        AttendanceRecordDto::getNickname,
                        Collectors.mapping(AttendanceBookFactory::createAttendanceTime, Collectors.toList())
                ));
    }

    private static CrewAttendance createCrewAttendance(String nickname, List<AttendanceTime> attendanceTimes) {
        Crew crew = Crew.of(nickname);
        AttendanceTimes times = AttendanceTimes.of(attendanceTimes);
        return CrewAttendance.of(crew, times);
    }

    private static AttendanceTime createAttendanceTime(AttendanceRecordDto record) {
        return AttendanceTime.of(
                record.getAttendanceTime().toLocalDate(),
                record.getAttendanceTime().toLocalTime()
        );
    }
}
