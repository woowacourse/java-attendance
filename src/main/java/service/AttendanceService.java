package service;

import constant.CampusConstant;
import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.Crew;
import domain.Manage;
import dto.AttendanceModifyRequest;
import dto.AttendanceRequest;
import dto.AttendanceResult;
import dto.CrewAlmostExpelledResult;
import dto.ModifiedResult;
import dto.ModifiedResult.TimeAttendanceStatus;
import dto.MonthAttendanceRecordsResult;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import repository.CrewRepository;
import util.DateTimeUtil;

public class AttendanceService {
    public AttendanceResult insertAttendanceRecord(AttendanceRequest request) {
        validateCampusTime(request.time());

        Crew crew = CrewRepository.findByNickname(request.nickname());
        AttendanceStatus status = crew.insertAttendanceTime(DateTimeUtil.nowDate(), request.time());
        return AttendanceResult.of(DateTimeUtil.nowDate(), request.time(), status);
    }

    public ModifiedResult modifyAttendanceRecord(AttendanceModifyRequest request) {
        validateCampusTime(request.time());

        Crew crew = CrewRepository.findByNickname(request.nickname());
        TimeAttendanceStatus before = TimeAttendanceStatus.of(crew, request.date());

        crew.modifyAttendanceTime(request.date(), request.time());

        TimeAttendanceStatus after = TimeAttendanceStatus.of(crew, request.date());

        return new ModifiedResult(request.date(), before, after);
    }

    public MonthAttendanceRecordsResult getMonthAttendanceRecordsResult(String nickname) {
        Crew crew = CrewRepository.findByNickname(nickname);
        LocalDate now = DateTimeUtil.nowDate();
        List<AttendanceRecord> attendanceRecords = getMonthAttendanceRecords(crew, now);
        Manage manage = Manage.of(crew.getAttendanceStatusStatistics(now));
        return new MonthAttendanceRecordsResult(
                crew.getNickname(), attendanceRecords, crew.getAttendanceStatusStatistics(now), manage
        );
    }

    private List<AttendanceRecord> getMonthAttendanceRecords(Crew crew, LocalDate today) {
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            if (DateTimeUtil.isOffDay(today.withDayOfMonth(day))) {
                continue;
            }
            LocalDate date = today.withDayOfMonth(day);
            attendanceRecords.add(
                    new AttendanceRecord(date, crew.getAttendanceTimeByDate(date),
                            crew.getAttendanceStatusByDate(date)));
        }
        return attendanceRecords;
    }


    public List<CrewAlmostExpelledResult> getCrewsAlmostExpelled() {
        List<Crew> crews = CrewRepository.findAll();
        return crews.stream()
                .map(crew -> {
                    var attendanceStatusStatistics
                            = crew.getAttendanceStatusStatistics(DateTimeUtil.nowDate());
                    return new CrewAlmostExpelledResult(
                            crew.getNickname(), attendanceStatusStatistics, Manage.of(attendanceStatusStatistics));
                })
                .toList();
    }

    private void validateCampusTime(LocalTime time) {
        if (time.isBefore(CampusConstant.START_TIME) || time.isAfter(CampusConstant.END_TIME)) {
            throw new IllegalArgumentException("캠퍼스 운영시간이 아닙니다.");
        }
    }
}
