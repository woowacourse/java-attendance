# java-attendance

출석 미션 저장소

## 기능

### 1. 출석확인

- 닉네임, 등교 시간을 입력받는다.
- 입력이 완료 된 후 정보를 출력한다. `12월 05일 화요일 09:59 (출석)`

**예외**

- [ ] 닉네임이 2글자에서 4글자 사이가 아닌 경우
- [ ] 닉네임이 존재하지 않는 경우
- [ ] 주말과 공휴일에 출석을 하는 경우
- [ ] 등원 시간이 아닌 경우
- [ ] 이미 출석한 경우에는 다시 출석할 수 없으며 수정 기능을 이용하도록 안내

### 2. 출석 수정

- 닉네임, 수정하려는 날짜, 변경하려는 시간을 입력받는다.
- 기존 정보와 변경된 정보를 출력한다. `12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!`

**예외**

- [ ] 수정하려는 날짜가 미래인 경우
- [ ] 입력한 닉네임이 존재하지 않는 경우

### 3. 크루별 출석 기록 확인

- 닉네임을 입력받는다.
- 전날까지의 출석 기록을 출력한다.
- 출석, 지각, 결석 횟수를 출력한다.
- [면담 | 경고] 대상자인지 츨력한다

**예외**

- [ ] 닉네임이 존재하지 않는 경우

### 4. 제적 위험자 확인

- 전날까지의 크루 출석 기록을 바탕으로 제적 위험자를 파악한다.
- 제적 위험자는 제적 대상자, 면담 대상자, 경고 대상자순으로 출력하며
- 대상 항목별 정렬 순서는 지각을 결석으로 간주하여 내림차순한다.
- 출석 상태가 같으면 닉네임으로 오름차순 정렬한다.

## 예상 도메인

```java
class Crew {
    private final String name;
}

class CheckInTime {
    private LocalDateTime time;
}

class CheckInTimes {
    private final List<CheckInTime> ts;
}

class Attendance {
    private final Crew crew;
    private final CheckInTimes checkInTimes;
}

class Attendances {
    List<Attendance> attendanceList;
}

```
