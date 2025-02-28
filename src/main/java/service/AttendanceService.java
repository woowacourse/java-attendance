package service;

import controller.dto.SaveAttendanceRequest;
import domain.AttendanceRecord;
import domain.AttendanceRecords;
import domain.Crew;
import domain.Crews;
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
}