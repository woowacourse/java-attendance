package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.domain.PenaltyCrew;
import attendance.dto.AttendanceInfoDto;
import attendance.dto.CrewAttendanceDto;
import attendance.dto.EditResponseDto;
import attendance.dto.FileRequestDto;
import attendance.dto.PenaltyCrewDto;
import attendance.domain.AttendanceReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceService {

    private Attendances attendances;
    private final AttendanceReader attendanceReader;

    public AttendanceService(AttendanceReader attendanceReader) {
        this.attendanceReader = attendanceReader;
    }

    public void init() {
        List<FileRequestDto> dtos = attendanceReader.read();
        List<Attendance> convertedAttendances = dtos.stream()
                .map(dto -> new Attendance(dto.name(), dto.date(), dto.time()))
                .toList();
        attendances = new Attendances(convertedAttendances);
    }

    public void checkNameExists(String name) {
        attendances.checkName(name);
    }

    public void checkByNameAndDate(String name, LocalDate today) {
        attendances.findLocalTimeByNameAndDate(name, today);
    }

    public void insertAttendance(String name, LocalDate today, LocalTime time) {
        checkByNameAndDate(name, today);
        Attendance attendance = new Attendance(name, today, time);
        this.attendances = attendances.add(attendance);
    }

    public String getAttendanceStatus(LocalDate date, LocalTime time) {
        return AttendanceStatus.of(date, time).getKorean();
    }

    public EditResponseDto edit(String name, LocalDate date, LocalTime editTime) {
        LocalTime oldTime = editAttendance(name, date, editTime);
        String oldStatus = getAttendanceStatus(date, oldTime);
        String editStatus = getAttendanceStatus(date, editTime);

        return new EditResponseDto(date, oldTime, editTime, oldStatus, editStatus);
    }

    public CrewAttendanceDto getCrewAttendance(String name, LocalDate today) {
        Map<LocalDate, AttendanceInfoDto> attendanceInfos = getAttendanceInfos(name, today);
        Map<AttendanceStatus, Integer> attendanceCounts = getAttendanceCounts(name, today);
        AttendancePenalty penalty = AttendancePenalty.find(attendanceCounts);
        return CrewAttendanceDto.of(name, attendanceInfos, attendanceCounts, penalty, today);
    }

    public List<PenaltyCrewDto> getCrewsName(LocalDate today) {
        List<String> crewNames = attendances.getCrewNames();
        List<PenaltyCrew> penaltyCrews = new ArrayList<>();

        for (String crewName : crewNames) {
            addPenaltyCrew(crewName, today, penaltyCrews);
        }

        return penaltyCrews.stream()
                .sorted()
                .map(PenaltyCrewDto::toDto)
                .toList();
    }

    private LocalTime editAttendance(String name, LocalDate date, LocalTime editTime) {
        LocalTime oldTime = attendances.findLocalTimeByNameAndDate(name, date);
        this.attendances = attendances.editAttendance(name, date, editTime);
        return oldTime;
    }

    private void addPenaltyCrew(String crewName, LocalDate today, List<PenaltyCrew> penaltyCrews) {
        Map<AttendanceStatus, Integer> attendanceStatusCounts = attendances.countAttendanceStatusByNameAndDate(crewName, today);
        if (AttendancePenalty.find(attendanceStatusCounts) == AttendancePenalty.NONE) {
            return;
        }
        penaltyCrews.add(
                new PenaltyCrew(
                    crewName,
                    attendanceStatusCounts.get(AttendanceStatus.ABSENCE),
                    attendanceStatusCounts.get(AttendanceStatus.LATE))
        );
    }

    private Map<LocalDate, AttendanceInfoDto> getAttendanceInfos(String name, LocalDate today) {
        Map<LocalDate, AttendanceInfoDto> map = new HashMap<>();
        List<Attendance> attendanceList = attendances.findByNameAndDateWithAscend(name, today);

        for (Attendance attendance : attendanceList) {
            AttendanceInfoDto dto = AttendanceInfoDto.toDto(attendance);
            map.put(dto.attendanceDate(), dto);
        }

        return map;
    }

    private Map<AttendanceStatus, Integer> getAttendanceCounts(String name, LocalDate today) {
        return attendances.countAttendanceStatusByNameAndDate(name, today);
    }
}
