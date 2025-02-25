package service;

import controller.dto.AttendanceRecordResponse;
import controller.dto.ModifyAttendanceRequest;
import controller.dto.MonthAttendanceStatistics;
import controller.dto.MonthAttendanceStatisticsRequest;
import controller.dto.SaveAttendanceRequest;
import controller.dto.SavedAttendanceRecord;
import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.CampusTime;
import domain.LectureTime;
import domain.RiskRank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import repository.AttendanceRecordRepository;
import repository.CrewRepository;
import util.DateTimeUtil;

public class AttendanceService {

    public SavedAttendanceRecord saveAttendanceRecord(SaveAttendanceRequest request) {
        validateCrew(request.nickname());
        validateOffDay(request.date());
        validateCampusTime(request.time());

        AttendanceRecordRepository.add(new AttendanceRecord(request.nickname(), request.date(), request.time(),
                AttendanceStatus.of(request.date(), request.time())));
        AttendanceRecord found = AttendanceRecordRepository.find(request.nickname(), request.date());
        return SavedAttendanceRecord.of(found.date(), found.time(), found.status());
    }

    public SavedAttendanceRecord modifyAttendanceRecord(ModifyAttendanceRequest request) {
        validateCrew(request.nickname());
        validateOffDay(request.date());
        validateCampusTime(request.time());

        if (AttendanceRecordRepository.exists(request.nickname(), request.date(), request.time())) {
            throw new IllegalArgumentException("이미 같은 출석 기록이 존재합니다.");
        }
        AttendanceRecordRepository.put(new AttendanceRecord(request.nickname(), request.date(), request.time(),
                AttendanceStatus.of(request.date(), request.time())));
        AttendanceRecord found = AttendanceRecordRepository.find(request.nickname(), request.date());
        return SavedAttendanceRecord.of(found.date(), found.time(), found.status());
    }

    public MonthAttendanceStatistics getMonthAttendanceStatistics(MonthAttendanceStatisticsRequest request) {
        List<AttendanceRecordResponse> monthAttendanceRecords = getMonthAttendanceRecords(request.nickname(),
                request.today());
        Map<String, Integer> attendanceStatusCount = calculateAttendanceStatusCount(monthAttendanceRecords);
        String riskRank = calculateRiskRank(attendanceStatusCount);

        return new MonthAttendanceStatistics(monthAttendanceRecords,
                attendanceStatusCount,
                riskRank);
    }

    private List<AttendanceRecordResponse> getMonthAttendanceRecords(
            String nickname, LocalDate today) {
        List<AttendanceRecordResponse> monthAttendanceRecords = new ArrayList<>();
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            LocalDate targetDate = today.withDayOfMonth(day);
            if (!LectureTime.isLectureDate(targetDate)) {
                continue;
            }
            if (!AttendanceRecordRepository.exists(nickname, targetDate)) {
                monthAttendanceRecords.add(AttendanceRecordResponse.from(targetDate));
            } else {
                monthAttendanceRecords.add(
                        AttendanceRecordResponse.from(
                                AttendanceRecordRepository.find(nickname, targetDate)
                        )
                );
            }
        }
        return monthAttendanceRecords;
    }

    private Map<String, Integer> calculateAttendanceStatusCount(List<AttendanceRecordResponse> monthAttendanceRecords) {
        Map<String, Integer> attendanceStatusCount = new LinkedHashMap<>();
        monthAttendanceRecords.forEach(record -> {
            int before = attendanceStatusCount.getOrDefault(record.attendanceStatus(), 0);
            attendanceStatusCount.put(record.attendanceStatus(), before + 1);
        });
        return attendanceStatusCount;
    }

    private String calculateRiskRank(Map<String, Integer> attendanceStatusCount) {
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
}
