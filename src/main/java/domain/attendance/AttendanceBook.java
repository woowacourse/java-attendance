package domain.attendance;

import domain.Crew;
import domain.attendance.constant.AttendanceRiskLevel;
import domain.datetime.CampusDate;
import domain.datetime.CampusTime;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class AttendanceBook {

    private static final String COMMA_OR_SPACE_SPLIT_REGEX = "[,\\s]+";

    private final Map<Crew, AttendanceInfos> book;

    private AttendanceBook(Map<Crew, AttendanceInfos> book) {
        this.book = book;
    }

    public static AttendanceBook initBook() {
        Map<Crew, AttendanceInfos> book = new HashMap<>();
        return new AttendanceBook(book);
    }

    public static AttendanceBook createBookByAttendances(final List<String> attendances) {
        AttendanceBook book = initBook();
        for (String attendance : attendances) {
            List<String> parsedAttendance = List.of(attendance.split(COMMA_OR_SPACE_SPLIT_REGEX));
            Crew crew = Crew.fromName(parsedAttendance.get(0));
            CampusDate date = CampusDate.from(parsedAttendance.get(1));
            CampusTime time = CampusTime.from(parsedAttendance.get(2));

            if (book.getBook().containsKey(crew)) {
                book.findInfoByCrew(crew).addInfoByDateAndTime(date, time);
                continue;
            }
            book.addCrew(crew);
            book.findInfoByCrew(crew).addInfoByDateAndTime(date, time);
        }
        return book;
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

    public boolean hasInfoByCrewAndDate(final Crew crew, final CampusDate date) {
        return book.get(crew).hasInfoByDate(date);
    }

    public AttendanceBook findRiskCrewBook(final LocalDate date) {
        return new AttendanceBook(book.entrySet().stream()
                .filter(entry -> isRiskCrew(date, entry))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue)));
    }

    private boolean isRiskCrew(final LocalDate date, final Entry<Crew, AttendanceInfos> entry) {
        return entry.getValue().countsByDate(date).calculateAttendanceRiskLevel()
                != AttendanceRiskLevel.NORMAL;
    }

    public Map<Crew, AttendanceInfos> getBook() {
        return new HashMap<>(book);
    }
}
