package io.writer;

import java.io.PrintWriter;

public class ConsoleWriter implements Writer {
    
    private final PrintWriter writer;
    
    public ConsoleWriter(final PrintWriter writer) {
        this.writer = writer;
    }
    
    @Override
    public void writeLine(final String value) {
        writer.println(value);
    }
}
