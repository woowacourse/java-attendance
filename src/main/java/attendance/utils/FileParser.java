package attendance.utils;

import attendance.common.ErrorMessage;
import attendance.dto.FileRequestDto;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

    private final String path;

    public FileParser(String path) {
        this.path = path;
    }

    public List<FileRequestDto> read() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            List<FileRequestDto> fileRequestDtos = new ArrayList<>();
            br.readLine();
            generateFileRequestDto(br, fileRequestDtos);
            return fileRequestDtos;
        } catch (IOException e) {
            throw new UncheckedIOException(ErrorMessage.FILE_READ_FAIL.formatMessage(path), e);
        }
    }

    private static void generateFileRequestDto(BufferedReader br, List<FileRequestDto> fileRequestDtos)
        throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] inputs = line.split(",");
            validateInput(inputs);
            String name = inputs[0];
            LocalDate date = DateConverter.convertToDate(inputs[1]);
            LocalTime time = DateConverter.convertToTime(inputs[1]);
            fileRequestDtos.add(new FileRequestDto(name, date, time));
        }
    }

    private static void validateInput(String[] inputs) {
        if (inputs.length != 2) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_FORMAT.getMessage());
        }
    }
}
