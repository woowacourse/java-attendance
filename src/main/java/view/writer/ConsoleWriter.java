package view.writer;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConsoleWriter implements Writer {
    
    private final BufferedWriter writer;
    
    private ConsoleWriter(final BufferedWriter writer) {
        this.writer = writer;
    }
    
    @Override
    public void writeLine(final String value) {
        try {
            writer.write(value + "\n");
        } catch (IOException e) {
            throw new RuntimeException("출력 중 장애가 발생하였습니다.", e);
        }
    }
}
