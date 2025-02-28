package service;

import controller.dto.ModifyAttendanceRequest;
import controller.dto.MonthAttendanceStatisticsRequest;
import controller.dto.RiskCrewsRequest;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import service.dto.AttendanceStatusCount;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.MonthAttendanceStatisticsResponse;
import service.dto.RiskCrew;
import service.dto.RiskCrewsResponse;
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
        LocalDate today = request.date();
        AbstractAttendanceRecord before = findAttendanceRecord(crew, today);
        AttendanceRecord after = AttendanceRecord.of(crew, request.date(), request.timeToModify());
        attendanceRecords.overwriteAttendanceRecord(after);
        return new ModifyAttendanceRecordResponse(before, after);
    }

    private AbstractAttendanceRecord findAttendanceRecord(Crew crew, LocalDate today) {
        if (attendanceRecords.exists(crew, today)) {
            return attendanceRecords.find(crew, today);
        }
        return EmptyAttendanceRecord.of(crew, today);
    }

    public MonthAttendanceStatisticsResponse bringMonthAttendanceStatistics(MonthAttendanceStatisticsRequest request) {
        Crew crew = crews.findByNickname(request.nickname());
        LocalDate today = request.today();

        List<AbstractAttendanceRecord> monthAttendanceRecords = getMonthAttendanceRecords(today, crew);
        AttendanceStatusCount attendanceStatusCount = calculateAttendanceStatusCount(monthAttendanceRecords);
        RiskRank riskRank = RiskRank.of(attendanceStatusCount.lateCount(), attendanceStatusCount.absentCount());
        return new MonthAttendanceStatisticsResponse(monthAttendanceRecords, attendanceStatusCount, riskRank);
    }

    public RiskCrewsResponse bringRiskCrews(RiskCrewsRequest request) {
        LocalDate today = request.today();
        List<Crew> foundCrews = new ArrayList<>(crews.findAllCrews());
        List<RiskCrew> riskCrews = new ArrayList<>();
        for (Crew crew : foundCrews) {
            extractRiskCrew(crew, today, riskCrews);
        }
        return new RiskCrewsResponse(sortedRiskCrews(riskCrews));
    }

    private void extractRiskCrew(Crew crew, LocalDate today, List<RiskCrew> riskCrews) {
        List<AbstractAttendanceRecord> monthAttendanceRecords = getMonthAttendanceRecords(today, crew);
        AttendanceStatusCount attendanceStatusCount = calculateAttendanceStatusCount(monthAttendanceRecords);
        RiskRank riskRank = RiskRank.of(attendanceStatusCount.lateCount(), attendanceStatusCount.absentCount());
        RiskCrew riskCrew = new RiskCrew(crew.getNickname(),
                attendanceStatusCount.lateCount(), attendanceStatusCount.absentCount(), riskRank);
        if (riskRank == RiskRank.NOT_MANAGED) {
            return;
        }
        riskCrews.add(riskCrew);
    }

    private List<RiskCrew> sortedRiskCrews(List<RiskCrew> riskCrews) {
        Function<RiskCrew, Integer> firstSort = riskCrew -> riskCrew.lateCount() +
                riskCrew.absentCount() * 3;
        Function<RiskCrew, String> secondSort = RiskCrew::nickname;
        return riskCrews.stream().sorted(
                        Comparator.comparing(firstSort, Comparator.reverseOrder())
                                .thenComparing(secondSort))
                .toList();
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