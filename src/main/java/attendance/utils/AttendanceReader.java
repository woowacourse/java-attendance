package attendance.utils;

import attendance.dto.FileRequestDto;

import java.util.List;

public interface AttendanceReader {

    public List<FileRequestDto> read();
}
