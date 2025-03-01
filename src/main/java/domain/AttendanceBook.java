package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class AttendanceBook {

    private final Map<Crew, AttendanceInfos> book;

    private AttendanceBook(Map<Crew, AttendanceInfos> book) {
        this.book = book;
    }

    public static AttendanceBook initBook() {
        Map<Crew, AttendanceInfos> book = new HashMap<>();
        return new AttendanceBook(book);
    }

    public void addCrew(final Crew crew) {
        book.put(crew, AttendanceInfos.initInfos());
    }

    public void addInfo(final Crew crew, final AttendanceInfo info) {
        book.get(crew).addInfo(info);
    }

    public AttendanceInfos addInfoWithDateAndTime(final Crew crew, final CampusDate date, final CampusTime time) {
        return book.get(crew).addInfoByDateAndTime(date, time);
    }

    public AttendanceInfos modifyInfoWithDateAndTime(final Crew crew, final CampusDate date, final CampusTime time) {
        return book.get(crew).modifyInfoByDateAndTime(date, time);
    }

    public AttendanceInfos findInfoByCrew(final Crew crew) {
        return AttendanceInfos.from(book.get(crew).getAttendanceInfos());
    }

    public AttendanceBook findRiskCrewBook(final CampusDate date) {
        return new AttendanceBook(book.entrySet().stream()
                .filter(entry -> isRiskCrew(date, entry))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue)));
    }

    private boolean isRiskCrew(final CampusDate date, final Entry<Crew, AttendanceInfos> entry) {
        return entry.getValue().countsByDate(date).calculateAttendanceRiskLevel()
                != AttendanceRiskLevel.NORMAL;
    }

    public Map<Crew, AttendanceInfos> getBook() {
        return new HashMap<>(book);
    }
}
