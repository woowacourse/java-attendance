package domain.holiday;

public enum KoreanHoliday implements Holiday {
    
    새해첫날(1, 1, false),
    설날(1, 1, true),
    삼일절(3, 1, false),
    부처님오신날(4, 8, true),
    어린이날(5, 5, false),
    현충일(6, 6, false),
    광복절(8, 15, false),
    추석(8, 15, true),
    개천절(10, 3, false),
    한글날(10, 9, false),
    기독탄신일(12, 25, false),
    ;
    
    private final int month;
    private final int dayOfMonth;
    private final boolean isLunarDate;
    
    KoreanHoliday(final int month, final int dayOfMonth, final boolean isLunarDate) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.isLunarDate = isLunarDate;
    }
    
    @Override
    public int getMonth() {
        return month;
    }
    
    @Override
    public int getDayOfMonth() {
        return dayOfMonth;
    }
    
    @Override
    public boolean isLunarHoliday() {
        return isLunarDate;
    }
}
