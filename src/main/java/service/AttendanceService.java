package service;

import controller.dto.ModifyAttendanceRequest;
import controller.dto.MonthAttendanceStatisticsRequest;
import controller.dto.RiskCrewsRequest;
import controller.dto.SaveAttendanceRequest;
import domain.AbstractAttendanceRecord;
import domain.AttendanceRecord;
import domain.AttendanceRecords;
import domain.AttendanceStatusCount;
import domain.Crew;
import domain.Crews;
import domain.RiskCrew;
import domain.RiskRank;
import java.time.LocalDate;
import java.util.List;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.MonthAttendanceStatisticsResponse;
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
            attendanceRecords = new AttendanceRecords(AttendanceRecordLoader.loadAttendanceRecordsFromFile());
            crews = new Crews(attendanceRecords.findAllDistinctCrews());
            return;
        }
        crews = new Crews();
        attendanceRecords = new AttendanceRecords();
    }


    public SaveAttendanceRecordResponse saveAttendanceRecord(SaveAttendanceRequest request) {
        Crew crew = crews.findByNickname(request.nickname());
        AttendanceRecord willBeSaved = AttendanceRecord.of(crew, request.date(), request.time());
        attendanceRecords.addIfAbsent(willBeSaved);
        AttendanceRecord found = attendanceRecords.getByCrewAndDate(crew, request.date());
        return SaveAttendanceRecordResponse.of(found);
    }

    public ModifyAttendanceRecordResponse modifyAttendanceRecord(ModifyAttendanceRequest request) {
        Crew crew = crews.findByNickname(request.nickname());
        AbstractAttendanceRecord before = attendanceRecords.findByCrewAndDate(crew, request.date());
        AttendanceRecord after = AttendanceRecord.of(crew, request.date(), request.time());
        attendanceRecords.updateOrAdd(after);

        return new ModifyAttendanceRecordResponse(before, after);
    }

    public MonthAttendanceStatisticsResponse getMonthAttendanceStatistics(MonthAttendanceStatisticsRequest request) {
        Crew crew = crews.findByNickname(request.nickname());
        LocalDate from = request.from();
        LocalDate to = request.to();

        List<AbstractAttendanceRecord> records = attendanceRecords.getByCrewFromTo(crew, from, to);
        AttendanceStatusCount statusCount = attendanceRecords.calculateAttendanceStatusCount(crew, from, to);
        RiskRank riskRank = RiskRank.from(statusCount);
        return new MonthAttendanceStatisticsResponse(records, statusCount, riskRank);
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