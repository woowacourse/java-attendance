package attendance.domain;

import attendance.dto.AttendanceFileDto;

import java.util.List;

public interface AttendanceReader {

    public List<AttendanceFileDto> read();
}
