package service;

import constant.CampusConstant;
import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.AttendanceStatusStatistics;
import domain.Crew;
import domain.Manage;
import dto.AttendanceModifyRequest;
import dto.AttendanceRequest;
import dto.AttendanceResult;
import dto.CrewAlmostExpelledResult;
import dto.ModifiedResult;
import dto.ModifiedResult.TimeAttendanceStatus;
import dto.MonthRecord;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import repository.CrewRepository;
import util.DateTimeUtil;

public class AttendanceService {
    public AttendanceResult insertAttendanceRecord(AttendanceRequest request) {
        validateCampusTime(request.time());
        LocalDate nowDate = DateTimeUtil.nowDate();
        validateOffDay(nowDate);
        Crew crew = CrewRepository.findByNickname(request.nickname());
        if (crew.attendanceTimeExists(nowDate)) {
            throw new IllegalArgumentException(nowDate + ": 이미 출석 기록이 존재합니다. 수정 기능을 이용해 주세요.");
        }

        AttendanceStatus status = crew.insertAttendanceTime(nowDate, request.time());
        return AttendanceResult.of(DateTimeUtil.nowDate(), request.time(), status);
    }

    private void validateOffDay(LocalDate date) {
        if (DateTimeUtil.isOffDay(date)) {
            throw new IllegalArgumentException(date + ": 주말 및 공휴일에는 출석을 받지 않습니다.");
        }
    }

    public ModifiedResult modifyAttendanceRecord(AttendanceModifyRequest request) {
        validateCampusTime(request.time());

        Crew crew = CrewRepository.findByNickname(request.nickname());
        TimeAttendanceStatus before = TimeAttendanceStatus.of(crew, request.date());
        crew.modifyAttendanceTime(request.date(), request.time());
        TimeAttendanceStatus after = TimeAttendanceStatus.of(crew, request.date());

        return new ModifiedResult(request.date(), before, after);
    }

    public MonthRecord getMonthAttendanceRecordsResult(String nickname) {
        Crew crew = CrewRepository.findByNickname(nickname);
        LocalDate now = DateTimeUtil.nowDate();
        List<AttendanceRecord> attendanceRecords = getMonthAttendanceRecords(crew, now);
        Manage manage = Manage.of(getAttendanceStatusStatistics(crew, now));
        return new MonthRecord(
                crew.getNickname(), attendanceRecords, getAttendanceStatusStatistics(crew, now), manage
        );
    }

    private List<AttendanceRecord> getMonthAttendanceRecords(Crew crew, LocalDate today) {
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();
        List<LocalDate> notOffDates = IntStream.range(1, today.getDayOfMonth())
                .mapToObj(today::withDayOfMonth)
                .toList();
        notOffDates.forEach(date ->
                attendanceRecords.add(new AttendanceRecord(date, crew.getAttendanceTimeByDate(date)))
        );
        return attendanceRecords;
    }

    public AttendanceStatusStatistics getAttendanceStatusStatistics(Crew crew, LocalDate today) {
        Map<AttendanceStatus, Integer> statusCounter = new EnumMap<>(AttendanceStatus.class);
        initializeStatusCounter(statusCounter);
        List<LocalDate> notOffDates = IntStream.range(1, today.getDayOfMonth())
                .mapToObj(today::withDayOfMonth)
                .toList();
        notOffDates.forEach(date -> {
            AttendanceStatus attendanceStatus = crew.getAttendanceStatusByDate(date);
            statusCounter.put(attendanceStatus, statusCounter.getOrDefault(attendanceStatus, 0) + 1);
        });
        return new AttendanceStatusStatistics(statusCounter);
    }

    private void initializeStatusCounter(Map<AttendanceStatus, Integer> result) {
        Arrays.stream(AttendanceStatus.values()).forEach(status -> result.put(status, 0));
    }

    public List<CrewAlmostExpelledResult> getCrewsAlmostExpelled() {
        List<Crew> crews = CrewRepository.findAll();
        return crews.stream()
                .map(crew -> {
                    var attendanceStatusStatistics
                            = getAttendanceStatusStatistics(crew, DateTimeUtil.nowDate());
                    return new CrewAlmostExpelledResult(
                            crew.getNickname(), attendanceStatusStatistics, Manage.of(attendanceStatusStatistics));
                })
                .toList();
    }

    private void validateCampusTime(LocalTime time) {
        if (time.isBefore(CampusConstant.START_TIME) || time.isAfter(CampusConstant.END_TIME)) {
            throw new IllegalArgumentException(time + ": 캠퍼스 운영시간이 아닙니다.");
        }
    }
}
