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
import domain.RiskRank;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.MonthAttendanceStatisticsResponse;
import service.dto.MonthAttendanceStatisticsResponse.AttendanceStatusCount;
import service.dto.RiskCrewsResponse;
import service.dto.RiskCrewsResponse.RiskCrew;
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
            attendanceRecords = new AttendanceRecords(AttendanceRecordLoader.loadAttendanceRecordsFromFile());
            crews = new Crews(attendanceRecords.findAllDistinctCrews());
            return;
        }
        crews = new Crews();
        attendanceRecords = new AttendanceRecords();
    }


    public SaveAttendanceRecordResponse saveAttendanceRecord(SaveAttendanceRequest request) {
        Crew crew = crews.findByNickname(request.nickname());
        AttendanceRecord saved = AttendanceRecord.of(crew, request.date(), request.time());
        attendanceRecords.add(saved);
        return SaveAttendanceRecordResponse.of(saved);
    }

    public ModifyAttendanceRecordResponse modifyAttendanceRecord(ModifyAttendanceRequest request) {
        Crew crew = crews.findByNickname(request.nickname());
        AbstractAttendanceRecord before = attendanceRecords.findOneByCrewAndDate(crew, request.date());
        AttendanceRecord after = AttendanceRecord.of(crew, request.date(), request.time());
        attendanceRecords.overwriteAttendanceRecord(after);
        return new ModifyAttendanceRecordResponse(before, after);
    }

    public MonthAttendanceStatisticsResponse getMonthAttendanceStatistics(MonthAttendanceStatisticsRequest request) {
        Crew crew = crews.findByNickname(request.nickname());
        LocalDate from = request.today().withDayOfMonth(1);
        LocalDate to = request.today().minusDays(1);

        List<AbstractAttendanceRecord> records = attendanceRecords.getByCrewFromTo(crew, from, to);
        Map<AttendanceStatus, Integer> statusCount = attendanceRecords.calculateAttendanceStatusCount(crew, from, to);
        RiskRank riskRank = RiskRank.from(statusCount);
        return new MonthAttendanceStatisticsResponse(records, AttendanceStatusCount.of(statusCount), riskRank);
    }

    public RiskCrewsResponse findRiskCrews(RiskCrewsRequest request) {
        LocalDate from = request.today().withDayOfMonth(1);
        LocalDate to = request.today().minusDays(1);

        List<RiskCrew> riskCrews = crews.findAllCrews().stream()
                .map(crew -> RiskCrew.of(crew, attendanceRecords.calculateAttendanceStatusCount(crew, from, to)))
                .filter(crew -> crew.riskRank() != RiskRank.NOT_MANAGED)
                .toList();
        return new RiskCrewsResponse(riskCrews);
    }
}