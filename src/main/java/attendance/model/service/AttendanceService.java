package attendance.model.service;

import attendance.dto.AttendanceLogResponse;
import attendance.dto.CrewAttendanceLogResponse;
import attendance.dto.RequiresManagementCrewResponse;
import attendance.dto.UpdateAttendanceResponse;
import attendance.model.Calender;
import attendance.model.domain.attendance.AttendanceStatus;
import attendance.model.domain.attendance.CrewAttendance;
import attendance.model.domain.crew.Crew;
import attendance.model.domain.crew.CrewAttendanceComparator;
import attendance.model.repository.AttendanceRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceLogResponse attendance(Crew crew, LocalDateTime attendanceTime) {
        attendanceRepository.save(crew, attendanceTime);
        return AttendanceLogResponse.fromDateTime(attendanceTime);
    }

    public Crew findCrewByName(String crewName) {
        return attendanceRepository.findCrewByName(crewName)
                .orElseThrow(() -> new IllegalArgumentException("해당 크루는 존재하지 않습니다."));
    }

    public UpdateAttendanceResponse updateAttendance(Crew crew, LocalDateTime updatedTime) {
        LocalDateTime previousTime = attendanceRepository.findDateTimeByCrewAndDate(crew, updatedTime.toLocalDate())
                .orElseThrow(() -> new IllegalArgumentException("해당 크루는 해당 일자의 출석 기록이 없습니다."));

        attendanceRepository.update(crew, previousTime, updatedTime);

        return UpdateAttendanceResponse.of(
                previousTime,
                updatedTime,
                AttendanceStatus.from(previousTime),
                AttendanceStatus.from(updatedTime)
        );
    }

    public CrewAttendanceLogResponse getAttendanceLog(Crew crew) {
        List<LocalDateTime> attendanceLogs = attendanceRepository.findByCrew(crew);

        return CrewAttendanceLogResponse.of(
                crew,
                mergeAndSotTimeLogResponses(attendanceLogs),
                CrewAttendance.of(crew, attendanceLogs)
        );
    }

    public List<RequiresManagementCrewResponse> getRequiresManagementCrews(
            CrewAttendanceComparator crewAttendanceComparator
    ) {

        return getSortedCrewAttendance(crewAttendanceComparator).stream()
                .filter(CrewAttendance::requiresManagement)
                .map(RequiresManagementCrewResponse::from)
                .toList();
    }

    private List<AttendanceLogResponse> mergeAndSotTimeLogResponses(List<LocalDateTime> attendanceLogs) {
        return Stream.concat(
                        makeExistsTimeLogResponses(attendanceLogs).stream(),
                        makeNoneExistsTimeLogResponses(attendanceLogs).stream()
                )
                .sorted(Comparator.comparing(AttendanceLogResponse::getDate))
                .toList();
    }

    private List<AttendanceLogResponse> makeExistsTimeLogResponses(List<LocalDateTime> attendanceLogs) {
        return attendanceLogs.stream()
                .map(AttendanceLogResponse::fromDateTime)
                .toList();
    }

    private List<AttendanceLogResponse> makeNoneExistsTimeLogResponses(List<LocalDateTime> attendanceLogs) {
        List<LocalDate> dateLogs = attendanceLogs.stream()
                .map(LocalDateTime::toLocalDate)
                .toList();

        return Calender.getNotExistsDatesBeforeToday(dateLogs).stream()
                .map(AttendanceLogResponse::fromAbsenceDate)
                .toList();
    }

    private List<CrewAttendance> getSortedCrewAttendance(CrewAttendanceComparator crewAttendanceComparator) {
        return attendanceRepository.findAllCrews().stream()
                .map(crew -> CrewAttendance.of(crew, attendanceRepository.findByCrew(crew)))
                .sorted(crewAttendanceComparator)
                .toList();
    }

}
