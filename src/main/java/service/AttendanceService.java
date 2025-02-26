package service;

import controller.dto.ModifyAttendanceRequest;
import controller.dto.MonthAttendanceStatisticsRequest;
import controller.dto.RiskCrewsRequest;
import controller.dto.SaveAttendanceRequest;
import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.CampusTime;
import domain.Crew;
import domain.LectureTime;
import domain.RiskRank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import repository.AttendanceRecordRepository;
import repository.CrewRepository;
import service.dto.AttendanceRecordResponse;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.ModifyAttendanceRecordResponse.TimeStatus;
import service.dto.MonthAttendanceStatisticsResponse;
import service.dto.RiskCrew;
import service.dto.RiskCrewsResponse;
import service.dto.SaveAttendanceRecordResponse;
import util.DateTimeUtil;

public class AttendanceService {

    public SaveAttendanceRecordResponse saveAttendanceRecord(SaveAttendanceRequest request) {
        validateCrew(request.nickname());
        validateOffDay(request.date());
        validateCampusTime(request.time());

        AttendanceRecordRepository.add(
                new AttendanceRecord(request.nickname(), request.date(), request.time(),
                        AttendanceStatus.of(request.date(), request.time()))
        );
        AttendanceRecord found = AttendanceRecordRepository.find(request.nickname(), request.date());
        return SaveAttendanceRecordResponse.of(found.date(), found.time(), found.status());
    }

    public ModifyAttendanceRecordResponse modifyAttendanceRecord(ModifyAttendanceRequest request) {
        validateCrew(request.nickname());
        validateOffDay(request.date());
        validateCampusTime(request.time());
        validateSameAttendanceRecordExists(request.nickname(), request.date(), request.time());

        TimeStatus before = getTimeStatus(request.nickname(), request.date());
        AttendanceRecordRepository.put(
                new AttendanceRecord(request.nickname(), request.date(), request.time(),
                        AttendanceStatus.of(request.date(), request.time()))
        );
        TimeStatus after = getTimeStatus(request.nickname(), request.date());
        return ModifyAttendanceRecordResponse.of(request.date(), before, after);
    }

    public MonthAttendanceStatisticsResponse getMonthAttendanceStatistics(MonthAttendanceStatisticsRequest request) {
        List<AttendanceRecordResponse> monthAttendanceRecords = getMonthAttendanceRecords(
                request.nickname(),
                request.today());
        Map<String, Integer> attendanceStatusCount = calculateAttendanceStatusCount(
                monthAttendanceRecords);
        String riskRank = calculateRiskRank(attendanceStatusCount);

        return new MonthAttendanceStatisticsResponse(monthAttendanceRecords,
                attendanceStatusCount,
                riskRank);
    }

    public RiskCrewsResponse getRiskCrews(RiskCrewsRequest request) {
        List<String> nicknames = CrewRepository.findAll()
                .stream()
                .map(Crew::getNickname)
                .toList();
        List<RiskCrew> riskCrews = new ArrayList<>();
        nicknames.forEach(nickname -> {
            List<AttendanceRecordResponse> monthAttendanceRecords = getMonthAttendanceRecords(nickname,
                    request.today());
            Map<String, Integer> attendanceStatusCount = calculateAttendanceStatusCount(
                    monthAttendanceRecords);
            String riskRank = calculateRiskRank(attendanceStatusCount);
            riskCrews.add(new RiskCrew(nickname, attendanceStatusCount, riskRank));
        });
        return new RiskCrewsResponse(riskCrews);
    }

    public TimeStatus getTimeStatus(String nickName, LocalDate date) {
        if (!AttendanceRecordRepository.exists(nickName, date)) {
            return TimeStatus.createAbsentTimeStatus();
        }
        AttendanceRecord found = AttendanceRecordRepository.find(nickName, date);
        return TimeStatus.of(found.time(), found.status().getTitle());
    }

    public List<AttendanceRecordResponse> getMonthAttendanceRecords(
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
        monthAttendanceRecords.add(
                AttendanceRecordResponse.of(found.date(), found.time(), found.status())
        );
    }

    public Map<String, Integer> calculateAttendanceStatusCount(List<AttendanceRecordResponse> monthAttendanceRecords) {
        Map<String, Integer> attendanceStatusCount = new LinkedHashMap<>();
        Arrays.stream(AttendanceStatus.values())
                .forEach(status -> attendanceStatusCount.put(status.getTitle(), 0));
        monthAttendanceRecords.forEach(record -> {
            int before = attendanceStatusCount.getOrDefault(record.attendanceStatus(), 0);
            attendanceStatusCount.put(record.attendanceStatus(), before + 1);
        });
        return attendanceStatusCount;
    }

    public String calculateRiskRank(Map<String, Integer> attendanceStatusCount) {
        int accumulatedCount =
                attendanceStatusCount.getOrDefault("지각", 0) / 3
                        + attendanceStatusCount.getOrDefault("결석", 0);
        return RiskRank.getRiskRankNameByAbsentCount(accumulatedCount);
    }

    private void validateCrew(String nickname) {
        if (!CrewRepository.existsCrew(nickname)) {
            throw new IllegalArgumentException(nickname + ": 존재하지 않는 크루입니다.");
        }
    }

    private void validateOffDay(LocalDate date) {
        if (DateTimeUtil.isWeekend(date)
                || DateTimeUtil.isHoliday(date)) {
            throw new IllegalArgumentException(date + ": 주말 및 공휴일에는 출석을 기록할 수 없습니다.");
        }
    }

    private void validateCampusTime(LocalTime time) {
        if (!DateTimeUtil.isInRange(CampusTime.openTime, CampusTime.closeTime, time)) {
            throw new IllegalArgumentException(time + ": 캠퍼스 운영시간이 아닙니다.");
        }
    }

    private void validateSameAttendanceRecordExists(String nickname, LocalDate date, LocalTime time) {
        if (AttendanceRecordRepository.exists(nickname, date, time)) {
            throw new IllegalArgumentException("이미 같은 출석 기록이 존재합니다.");
        }
    }
}
