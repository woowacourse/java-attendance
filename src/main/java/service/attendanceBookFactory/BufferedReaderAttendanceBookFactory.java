package service.attendanceBookFactory;

import domain.AttendanceBook;
import service.todayProvider.TodayProvider;

import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BufferedReaderAttendanceBookFactory implements AttendanceBookFactory {
    
    private static final DateTimeFormatter ATTEND_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private final Map<String, Set<LocalDateTime>> attendanceRecords = new HashMap<>();
    private final TodayProvider todayProvider;
    private final BufferedReader reader;
    
    public BufferedReaderAttendanceBookFactory(final TodayProvider todayProvider, final BufferedReader reader) {
        this.todayProvider = todayProvider;
        this.reader = reader;
    }
    
    @Override
    public AttendanceBook create() {
        readAndInsertAllAttendanceRecords();
        return new AttendanceBook(attendanceRecords, todayProvider.today());
    }
    
    private void readAndInsertAllAttendanceRecords() {
        reader.lines()
                .skip(1)
                .forEach(this::insertAttendanceRecordIfThisMonth);
    }
    
    private void insertAttendanceRecordIfThisMonth(final String line) {
        var values = line.split(",");
        var attendDateTime = LocalDateTime.parse(values[1], ATTEND_DATE_TIME_FORMATTER);
        
        if (attendDateTime.getMonth() != todayProvider.today().getMonth()) {
            return;
        }
        
        attendanceRecords.putIfAbsent(values[0], new HashSet<>());
        attendanceRecords.get(values[0]).add(attendDateTime);
    }
}
