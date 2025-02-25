package service;

import constant.AttendanceStatus;
import constant.CampusTime;
import controller.dto.ModifyAttendanceRequest;
import controller.dto.SaveAttendanceRequest;
import controller.dto.SavedAttendanceRecord;
import domain.AttendanceRecord;
import java.time.LocalTime;
import repository.AttendanceRecordRepository;
import repository.CrewRepository;
import util.DateTimeUtil;

public class AttendanceService {

    public SavedAttendanceRecord saveAttendanceRecord(SaveAttendanceRequest request) {
        validateCrew(request.nickname());
        validateCampusTime(request.time());
        AttendanceRecordRepository.add(new AttendanceRecord(request.nickname(), request.date(), request.time(),
                AttendanceStatus.of(request.date(), request.time())));
        AttendanceRecord record = AttendanceRecordRepository.find(request.nickname(), request.date());
        return SavedAttendanceRecord.of(record.date(), record.time(), record.status());
    }

    public SavedAttendanceRecord modifyAttendanceRecord(ModifyAttendanceRequest request) {
        return null;
    }

    private void validateCrew(String nickname) {
        if (!CrewRepository.existsCrew(nickname)) {
            throw new IllegalArgumentException(nickname + ": 존재하지 않는 크루입니다.");
        }
    }

    private void validateCampusTime(LocalTime time) {
        if (!DateTimeUtil.isBetween(CampusTime.openTime, CampusTime.closeTime, time)) {
            throw new IllegalArgumentException(time + ": 캠퍼스 운영시간이 아닙니다.");
        }
    }
}
