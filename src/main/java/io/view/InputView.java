package io.view;

import dto.requeset.AttendRequest;
import dto.requeset.AttendanceBookDecision;
import dto.requeset.AttendanceModifyRequest;
import util.dataProvider.DateProvider;
import util.inputProvider.InputProvider;
import util.outputHandler.OutputHandler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class InputView {
    
    private final InputProvider inputProvider;
    private final OutputHandler outputHandler;
    private final DateProvider dateProvider;
    
    public InputView(InputProvider inputProvider, OutputHandler outputHandler, DateProvider dateProvider) {
        this.inputProvider = inputProvider;
        this.outputHandler = outputHandler;
        this.dateProvider = dateProvider;
    }
    
    public AttendanceBookDecision inputDecision() {
        LocalDate now = dateProvider.getDate();
        int monthOfYear = now.getMonth().getValue();
        int todayOfMonth = now.getDayOfMonth();
        String todayOfWeek = checkToday(now);
        
        String decision = String.format("""
                오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """, monthOfYear, todayOfMonth, todayOfWeek);
        
        outputHandler.handle(decision);
        return AttendanceBookDecision.from(inputProvider.get());
    }
    
    private String checkToday(LocalDate localDate) {
        return localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }
    
    public AttendRequest getAttendRequest() {
        System.out.println("닉네임을 입력해 주세요.");
        String name = inputProvider.get();
        System.out.println("등교 시간을 입력해 주세요.");
        LocalTime time = LocalTime.parse(inputProvider.get());
        
        return new AttendRequest(name, time);
    }
    
    public AttendanceModifyRequest getAttendanceModifyRequest() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        String name = inputProvider.get();
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        
        LocalDate date = dateProvider.getDate().withDayOfMonth(Integer.parseInt(inputProvider.get()));
        System.out.println("언제로 변경하겠습니까?");
        LocalTime time = LocalTime.parse(inputProvider.get());
        
        return new AttendanceModifyRequest(name, date, time);
    }
    
    public String getAttendanceResultFindRequest() {
        System.out.println("닉네임을 입력해 주세요.");
        return inputProvider.get();
    }
    
}
