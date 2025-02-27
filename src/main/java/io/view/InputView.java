package io.view;

import io.dto.MenuSelect;
import io.reader.Reader;
import io.writer.Writer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

public class InputView {
    
    private final Reader reader;
    private final Writer writer;
    
    public InputView(final Reader reader, final Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }
    
    public MenuSelect getSelectedMenu(final LocalDate today) {
        String output = """
                
                오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """.formatted(
                today.getMonth().getValue(),
                today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
        );
        
        writer.writeLine(output);
        final var input = reader.readLine();
        
        return MenuSelect.of(input);
    }
    
    public String getAttendNickname() {
        String output = "닉네임을 입력해 주세요.";
        
        writer.writeLine(output);
        return reader.readLine();
    }
    
    public LocalTime getAttendTime() {
        String output = "등교 시간을 입력해 주세요.";
        
        writer.writeLine(output);
        return inputTimeUntilPresent();
    }
    
    public String getModifyNickname() {
        String output = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
        
        writer.writeLine(output);
        return reader.readLine();
    }
    
    public LocalDate getModifyDate(final LocalDate today) {
        String output = "수정하려는 날짜(일)를 입력해 주세요.";
        
        writer.writeLine(output);
        int dayOfMonth = Integer.parseInt(reader.readLine());
        return today.withDayOfMonth(dayOfMonth);
    }
    
    public LocalTime getModifyTime() {
        String output = "언제로 변경하겠습니까?";
        
        writer.writeLine(output);
        return inputTimeUntilPresent();
    }
    
    public String getRecordFindNickname() {
        String output = "닉네임을 입력해 주세요.";
        
        writer.writeLine(output);
        return reader.readLine();
    }
    
    private LocalTime inputTimeUntilPresent() {
        while (true) {
            var input = inputTime();
            if (input.isPresent()) {
                return input.get();
            }
            writer.writeLine("\"XX:XX\" 형식으로 입력해주세요.");
        }
    }
    
    private Optional<LocalTime> inputTime() {
        try {
            return Optional.of(LocalTime.parse(reader.readLine()));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
}
