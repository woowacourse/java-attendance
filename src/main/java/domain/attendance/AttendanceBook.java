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
        Map<Crew, AttendanceInfos> book = new HashMap<>();
        for (String attendance : attendances) {
            List<String> parsedAttendance = List.of(attendance.split(COMMA_OR_SPACE_SPLIT_REGEX));
            Crew crew = Crew.fromName(parsedAttendance.get(0));
            CampusDate date = CampusDate.from(parsedAttendance.get(1));
            CampusTime time = CampusTime.from(parsedAttendance.get(2));
            registerCrewAndInfo(book, crew, date, time);
        }
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

    public boolean hasInfoByCrewAndDate(final Crew crew, final CampusDate date) {
        return book.get(crew).hasInfoByDate(date);
    }

    public AttendanceBook findRiskCrewBook(final LocalDate date) {
        return new AttendanceBook(book.entrySet().stream()
                .filter(entry -> isRiskCrew(date, entry))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue)));
    }

    public void updateCrewAttendanceInfos(Crew crew, AttendanceInfos modifiedInfos) {
        book.put(crew, modifiedInfos);
    }

    private static void registerCrewAndInfo(Map<Crew, AttendanceInfos> book, Crew crew, CampusDate date,
                                            CampusTime time) {
        if (book.containsKey(crew)) {
            addInfosWithExistCrew(book, crew, date, time);
            return;
        }
        addInfosWithOutCrew(book, crew, date, time);
    }

    private static void addInfosWithExistCrew(Map<Crew, AttendanceInfos> book, Crew crew, CampusDate date,
                                              CampusTime time) {
        AttendanceInfos crewInfos = book.get(crew);
        AttendanceInfos attendanceInfos = crewInfos.addInfoByDateAndTime(date, time);
        book.put(crew, attendanceInfos);
    }

    private static void addInfosWithOutCrew(Map<Crew, AttendanceInfos> book, Crew crew, CampusDate date,
                                            CampusTime time) {
        AttendanceInfos initInfos = AttendanceInfos.initInfos();
        AttendanceInfos attendanceInfos = initInfos.addInfoByDateAndTime(date, time);
        book.put(crew, attendanceInfos);
    }

    private boolean isRiskCrew(final LocalDate date, final Entry<Crew, AttendanceInfos> entry) {
        return entry.getValue().countsByDate(date).calculateAttendanceRiskLevel()
                != AttendanceRiskLevel.NORMAL;
    }

    public Map<Crew, AttendanceInfos> getBook() {
        return new HashMap<>(book);
    }
}
