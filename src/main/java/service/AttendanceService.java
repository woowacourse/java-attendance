package service;

import controller.dto.ModifyAttendanceRequest;
import controller.dto.MonthAttendanceStatisticsRequest;
import controller.dto.SaveAttendanceRequest;
import domain.AbstractAttendanceRecord;
import domain.AttendanceRecord;
import domain.AttendanceRecords;
import domain.AttendanceStatus;
import domain.Crew;
import domain.Crews;
import domain.EmptyAttendanceRecord;
import domain.LectureTime;
import domain.RiskRank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import service.dto.AttendanceStatusCount;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.MonthAttendanceStatisticsResponse;
import service.dto.SaveAttendanceRecordResponse;

public class AttendanceService {
    private final Crews crews;
    private final AttendanceRecords attendanceRecords;

    public AttendanceService(Crews crews, AttendanceRecords attendanceRecords) {
        this.crews = crews;
        this.attendanceRecords = attendanceRecords;
    }

    public AttendanceService(boolean loadFromFile) {
        if (loadFromFile) {
            attendanceRecords = new AttendanceRecords(
                    AttendanceRecordLoader.loadAttendanceRecordsFromFile());
            crews = new Crews(attendanceRecords.findAllDistinctCrews());
            return;
        }
        crews = new Crews();
        attendanceRecords = new AttendanceRecords();
    }

    public SaveAttendanceRecordResponse saveAttendanceRecord(SaveAttendanceRequest request) {
        Crew crew = crews.findByNickname(request.nickname());
        attendanceRecords.add(AttendanceRecord.of(crew, request.date(), request.time()));

        AttendanceRecord found = attendanceRecords.find(crew, request.date());
        return SaveAttendanceRecordResponse.of(found);
    }

    public ModifyAttendanceRecordResponse modifyAttendanceRecord(ModifyAttendanceRequest request) {
        Crew crew = crews.findByNickname(request.nickname());
        AttendanceRecord before = attendanceRecords.find(crew, request.date());
        AttendanceRecord after = AttendanceRecord.of(crew, request.date(), request.timeToModify());
        attendanceRecords.overwriteAttendanceRecord(after);
        return new ModifyAttendanceRecordResponse(before, after);
    }

    public MonthAttendanceStatisticsResponse bringMonthAttendanceStatistics(MonthAttendanceStatisticsRequest request) {
        Crew crew = crews.findByNickname(request.nickname());
        LocalDate today = request.today();

        List<AbstractAttendanceRecord> monthAttendanceRecords = getMonthAttendanceRecords(today, crew);
        AttendanceStatusCount attendanceStatusCount = calculateAttendanceStatusCount(monthAttendanceRecords);
        RiskRank riskRank = RiskRank.of(attendanceStatusCount.lateCount(), attendanceStatusCount.absentCount());
        return new MonthAttendanceStatisticsResponse(monthAttendanceRecords, attendanceStatusCount, riskRank);
    }

    private List<AbstractAttendanceRecord> getMonthAttendanceRecords(LocalDate today, Crew crew) {
        List<AbstractAttendanceRecord> monthAttendanceRecords = new ArrayList<>();
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            LocalDate date = today.withDayOfMonth(day);
            if (!LectureTime.isLectureDate(date)) {
                continue;
            }
            if (!attendanceRecords.exists(crew, date)) {
                monthAttendanceRecords.add(EmptyAttendanceRecord.of(crew, date));
                continue;
            }
            monthAttendanceRecords.add(attendanceRecords.find(crew, date));
        }
        return monthAttendanceRecords;
    }

    private AttendanceStatusCount calculateAttendanceStatusCount(
            List<AbstractAttendanceRecord> monthAttendanceRecords) {
        Map<AttendanceStatus, Integer> statusCount = new HashMap<>();
        Arrays.stream(AttendanceStatus.values()).forEach(status -> statusCount.put(status, 0));
        for (AbstractAttendanceRecord record : monthAttendanceRecords) {
            int count = statusCount.getOrDefault(record.getStatus(), 0);
            statusCount.put(record.getStatus(), count + 1);
        }
        return new AttendanceStatusCount(
                statusCount.getOrDefault(AttendanceStatus.ATTENDANCE, 0),
                statusCount.getOrDefault(AttendanceStatus.LATE, 0),
                statusCount.getOrDefault(AttendanceStatus.ABSENT, 0)
        );
    }
}