package domain;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {

    private final Map<Crew, AttendanceInfos> book;

    private AttendanceBook(Map<Crew, AttendanceInfos> book) {
        this.book = book;
    }

    public static AttendanceBook initBook() {
        Map<Crew, AttendanceInfos> book = new HashMap<>();
        return new AttendanceBook(book);
    }

    public void addCrew(Crew crew) {
        book.put(crew, AttendanceInfos.initInfos());
    }

    public void addInfo(Crew crew, AttendanceInfo info) {
        book.get(crew).addInfo(info);
    }

    public void addInfoWithDate(Crew crew, CampusDate date, CampusTime time) {
        book.get(crew).addInfoByDateAndTime(date, time);
    }

    public Map<Crew, AttendanceInfos> getBook() {
        return new HashMap<>(book);
    }
}
