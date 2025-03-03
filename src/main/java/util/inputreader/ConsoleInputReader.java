package util.inputreader;

import domain.ErrorCode;
import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleInputReader implements InputReader {
    private final BufferedReader bufferedReader;

    public ConsoleInputReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public String readline() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(ErrorCode.INPUT_CONSOLE_READER_FAILED.getMessage());
        }
    }
}
