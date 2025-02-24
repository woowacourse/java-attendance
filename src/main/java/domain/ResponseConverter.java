package domain;

import dto.AttendanceResponse;
import dto.ExpulsionCrewResponse;
import dto.UpdatedAttendanceSnapshotResponse;
import java.util.List;

public class ResponseConverter {
    public UpdatedAttendanceSnapshotResponse convertUpdatedAttendanceSnapshotToResponse(
            final UpdatedAttendanceSnapshot updatedAttendanceSnapshot
    ) {
        return new UpdatedAttendanceSnapshotResponse(
                convertAttendanceToResponse(updatedAttendanceSnapshot.getBefore()),
                convertAttendanceToResponse(updatedAttendanceSnapshot.getAfter())
        );
    }

    public List<AttendanceResponse> convertAttendancesToResponses(final List<Attendance> attendances) {
        return attendances.stream()
                .map(this::convertAttendanceToResponse)
                .toList();
    }

    public AttendanceResponse convertAttendanceToResponse(final Attendance attendance) {
        return new AttendanceResponse(attendance.getDateTime(), attendance.calculateStatus(), attendance.isEmpty());
    }

    public List<ExpulsionCrewResponse> convertExpulsionCrewResponses(final List<Crew> crews) {
        return crews.stream()
                .map(crew -> new ExpulsionCrewResponse(crew.getName().getName(), crew.calculateAttendanceStatistics(),
                        crew.calculateExpulsionStatus()))
                .toList();
    }
}
