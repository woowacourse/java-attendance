package service;

import controller.dto.ModifyAttendanceRequest;
import controller.dto.SaveAttendanceRequest;
import controller.dto.SavedAttendanceRecord;
import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.CampusTime;
import java.time.LocalDate;
import java.time.LocalTime;
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

//    public MonthAttendanceStatistics getMonthAttendanceStatistics(MonthAttendanceStatisticsRequest request) {
//        // TODO: 특정 크루의 월 단위 출석 기록
//
//        // TODO: 출석 상태별 횟수 계산
//
//        // TODO: 제적 위험 등급 계산
//        return null;
//    }

    private void validateOffDay(LocalDate date) {
        if (DateTimeUtil.isWeekend(date)
                || DateTimeUtil.isHoliday(date)) {
            throw new IllegalArgumentException(date + ": 주말 및 공휴일에는 출석을 기록할 수 없습니다.");
        }
    }

    private void validateCrew(String nickname) {
        if (!CrewRepository.existsCrew(nickname)) {
            throw new IllegalArgumentException(nickname + ": 존재하지 않는 크루입니다.");
        }
    }

    private void validateCampusTime(LocalTime time) {
        if (!DateTimeUtil.isInRange(CampusTime.openTime, CampusTime.closeTime, time)) {
            throw new IllegalArgumentException(time + ": 캠퍼스 운영시간이 아닙니다.");
        }
    }
}
