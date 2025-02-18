import java.time.LocalDateTime;
import java.util.List;

public class Crew {
    private List<LocalDateTime> records;

    public Crew(List<LocalDateTime> records){
        this.records = records;
    }

    public LocalDateTime attend(LocalDateTime dateAndTime){
        if(isAttend(dateAndTime)) {
            throw new IllegalArgumentException("이미 출석한 경우 수정만 가능합니다.");
        }
        records.add(dateAndTime);
        return dateAndTime;
    }

    private boolean isAttend(LocalDateTime dateAndTime) {
        return records.contains(dateAndTime);
    }

    public List<LocalDateTime> getRecords(){
        return records;
    }
}
