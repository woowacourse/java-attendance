package service;

import controller.dto.ModifyAttendanceRequest;
import controller.dto.MonthAttendanceStatisticsRequest;
import controller.dto.RiskCrewsRequest;
import controller.dto.SaveAttendanceRequest;
import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.Crew;
import domain.LectureTime;
import domain.RiskRank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import repository.AttendanceRecordRepository;
import repository.CrewRepository;
import service.dto.AttendanceRecordResponse;
import service.dto.AttendanceStatusCount;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.ModifyAttendanceRecordResponse.TimeStatus;
import service.dto.MonthAttendanceStatisticsResponse;
import service.dto.RiskCrew;
import service.dto.RiskCrewsResponse;
import service.dto.SaveAttendanceRecordResponse;

public class AttendanceService {

    public SaveAttendanceRecordResponse saveAttendanceRecord(SaveAttendanceRequest request) {
        validateCrew(request.nickname());

        AttendanceRecordRepository.add(
                new AttendanceRecord(request.nickname(), request.date(), request.time(),
                        AttendanceStatus.of(request.date(), request.time()))
        );
        AttendanceRecord found = AttendanceRecordRepository.find(request.nickname(), request.date());
        return SaveAttendanceRecordResponse.of(found.date(), found.time(), found.status().getDescription());
    }

    public ModifyAttendanceRecordResponse modifyAttendanceRecord(ModifyAttendanceRequest request) {
        validateCrew(request.nickname());
        validateSameAttendanceRecordExists(request.nickname(), request.date(), request.time());

        TimeStatus before = getTimeStatus(request.nickname(), request.date());
        AttendanceRecordRepository.put(new AttendanceRecord(request.nickname(), request.date(), request.time(),
                AttendanceStatus.of(request.date(), request.time()))
        );
        TimeStatus after = getTimeStatus(request.nickname(), request.date());
        return ModifyAttendanceRecordResponse.of(request.date(), before, after);
    }

    public MonthAttendanceStatisticsResponse getMonthAttendanceStatistics(MonthAttendanceStatisticsRequest request) {
        List<AttendanceRecordResponse> monthRecords = getMonthAttendanceRecordResponses(
                request.nickname(),
                request.today());
        AttendanceStatusCount statusCount = calculateAttendanceStatusCount(monthRecords);
        RiskRank riskRank = calculateRiskRank(statusCount);

        return new MonthAttendanceStatisticsResponse(monthRecords, statusCount, riskRank.getName());
    }

    public RiskCrewsResponse getRiskCrews(RiskCrewsRequest request) {
        List<String> nicknames = findCrewNicknames();
        TreeSet<RiskCrew> riskCrews = new TreeSet<>();
        nicknames.forEach(nickname -> {
            RiskCrew riskCrew = getRiskCrew(nickname, request.today());
            riskCrews.add(riskCrew);
        });
        return new RiskCrewsResponse(riskCrews);
    }

    private RiskCrew getRiskCrew(String nickname, LocalDate today) {
        List<AttendanceRecordResponse> monthRecords = getMonthAttendanceRecordResponses(nickname, today);
        AttendanceStatusCount statusCount = calculateAttendanceStatusCount(monthRecords);
        RiskRank riskRank = calculateRiskRank(statusCount);
        return new RiskCrew(
                nickname,
                statusCount.lateCount(),
                statusCount.absentCount(),
                riskRank.getName());
    }

    private List<String> findCrewNicknames() {
        return CrewRepository.findAll()
                .stream()
                .map(Crew::getNickname)
                .toList();
    }

    private TimeStatus getTimeStatus(String nickName, LocalDate date) {
        if (!AttendanceRecordRepository.exists(nickName, date)) {
            return TimeStatus.createAbsentTimeStatus();
        }
        AttendanceRecord found = AttendanceRecordRepository.find(nickName, date);
        return TimeStatus.of(found.time(), found.status().getDescription());
    }

    private List<AttendanceRecordResponse> getMonthAttendanceRecordResponses(
            String nickname, LocalDate today) {
        List<AttendanceRecordResponse> monthAttendanceRecords = new ArrayList<>();
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            addAttendanceRecord(nickname, today.withDayOfMonth(day), monthAttendanceRecords);
        }
        return monthAttendanceRecords;
    }


    private void addAttendanceRecord(String nickname, LocalDate targetDate,
                                     List<AttendanceRecordResponse> monthAttendanceRecords) {
        if (!LectureTime.isLectureDate(targetDate)) {
            return;
        }
        if (!AttendanceRecordRepository.exists(nickname, targetDate)) {
            monthAttendanceRecords.add(AttendanceRecordResponse.from(targetDate));
            return;
        }
        AttendanceRecord found = AttendanceRecordRepository.find(nickname, targetDate);
        monthAttendanceRecords.add(AttendanceRecordResponse.of(found.date(), found.time(), found.status()));
    }

    private AttendanceStatusCount calculateAttendanceStatusCount(
            List<AttendanceRecordResponse> monthAttendanceRecords) {
        int attendanceCount = (int) monthAttendanceRecords.stream()
                .filter(record -> record.attendanceStatus().equals(AttendanceStatus.ATTENDANCE.getDescription()))
                .count();
        int lateCount = (int) monthAttendanceRecords.stream()
                .filter(record -> record.attendanceStatus().equals(AttendanceStatus.LATE.getDescription()))
                .count();
        int absentCount = (int) monthAttendanceRecords.stream()
                .filter(record -> record.attendanceStatus().equals(AttendanceStatus.ABSENT.getDescription()))
                .count();
        return new AttendanceStatusCount(attendanceCount, lateCount, absentCount);
    }

    private RiskRank calculateRiskRank(AttendanceStatusCount attendanceStatusCount) {
        int riskCount = attendanceStatusCount.lateCount() / 3
                + attendanceStatusCount.absentCount();
        return RiskRank.from(riskCount);
    }

    private void validateCrew(String nickname) {
        if (!CrewRepository.existsCrew(nickname)) {
            throw new IllegalArgumentException(nickname + ": 존재하지 않는 크루입니다.");
        }
    }


    private void validateSameAttendanceRecordExists(String nickname, LocalDate date, LocalTime time) {
        if (AttendanceRecordRepository.exists(nickname, date, time)) {
            throw new IllegalArgumentException("이미 같은 출석 기록이 존재합니다.");
        }
    }
}