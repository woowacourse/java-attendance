package service;

import controller.dto.ModifyAttendanceRequest;
import controller.dto.SaveAttendanceRequest;
import domain.AttendanceRecord;
import domain.AttendanceRecords;
import domain.Crew;
import domain.Crews;
import service.dto.ModifyAttendanceRecordResponse;
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
}