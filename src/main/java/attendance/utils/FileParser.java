package attendance.utils;

import attendance.dto.FileRequestDto;

import java.util.List;

public interface FileParser {

    public List<FileRequestDto> read();
}
