package io.reader;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleReader implements Reader {
    
    private final BufferedReader reader;
    
    public ConsoleReader(final BufferedReader reader) {
        this.reader = reader;
    }
    
    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("입력 중 장애가 발생하였습니다.", e);
        }
    }
}
