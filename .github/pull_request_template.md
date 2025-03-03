<!-- 

## 코드 리뷰 팁

- 코드와 관련된 질문이 있다면, PR 본문에 적기 보다는 해당 코드를 선택하고 코멘트를 남겨주세요.
  - [참고: Adding comments to a pull request](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/reviewing-changes-in-pull-requests/commenting-on-a-pull-request#adding-comments-to-a-pull-request)

-->

## 체크 리스트

- [x] 미션의 필수 요구사항을 모두 구현했나요?
- [x] Gradle `test`를 실행했을 때, 모든 테스트가 정상적으로 통과했나요?
- [x] 애플리케이션이 정상적으로 실행되나요?
- [x] [프롤로그](https://prolog.techcourse.co.kr)에 셀프 체크를 작성했나요?
  - [코기 프롤로그](https://prolog.techcourse.co.kr/studylogs/3929)

## 객체지향 생활체조 요구사항을 얼마나 잘 충족했다고 생각하시나요?

### 1~5점 중에서 선택해주세요.

- [ ] 1 (전혀 충족하지 못함)
- [ ] 2
- [x] 3 (보통)
- [ ] 4
- [ ] 5 (완벽하게 충족)

### 선택한 점수의 이유를 적어주세요.

<!-- 이유 작성 -->
```규칙 4: 한 줄에 점을 하나만 찍는다.```

해당 규칙을 지키지 못한 코드가 존재한다.
  
## 어떤 부분에 집중하여 리뷰해야 할까요?

<!-- 리뷰어가 효과적으로 피드백할 수 있도록 중점적으로 피드백받고 싶은 내용을 공유해주세요.  
예를 들어, 가장 고민했던 점이나 여전히 어려운 부분, 그리고 이에 대한 생각을 적을 수 있습니다. -->

안녕하세요 로키!😊 step1에서 로키 덕분에 제 코드에 대해 의구심을 가지고 생각하는 기회를 얻을 수 있었습니다. 감사합니다 👍
step1에서 머지된 후 마지막에 코멘트 받은 부분에 대해서도 적용했습니다!
#### 👨‍💻 피드백 적용
- [X] 월, 일은 MonthDay 를 활용하기 -> Holiday 객체
- [X] DTO record 클래스 활용
- [X] LocalDateTime을 LocalDate, LocalTime 으로 분리하여 관리 -> AttendanceHistory
- [X] csv 파일에 없는 기록을 23:59 임의 시간으로 결석을 처리하지 않기 -> LocalDateTime을 LocalDate, LocalTime으로 나눈후 만약 csv 파일에 기록이 없다면 LocalTime을 null로 받는다. null은 위험성이 크기에 Optional을 사용하였고 로직에서 null 포인터 예외가 터지지 않도록 사용 자제 
- [X] 상수와 상태를 적절히 분리하기
#### 🤔 궁금한 점
- csv 파일에 없는 기록 즉, 하루 기록이 없는 경우 처리를 예전에 23:59 시간을 정해서 해당 시간은 결석으로 처리했습니다.
하지만 로키의 말대로 현재는 08:00 ~ 23:00만 출석 가능하지만 추후에 해당 요구사항이 변경이 되면 큰 문제가 발생할거라 생각하고 수정을 했습니다.
수정한 방법으로는 LocalDate와 LocalTime 으로 분리 후 LocalTime이 null인 경우 결석으로 간주하도록 했습니다.
하지만 null을 이용한 방법은 null 포인트 예외와 메서드를 사용하는 입장에서 위험하다고 판단했습니다. 그래서 LocalTime을 리턴하는 메서드인 경우 Optional을 통해 null이 있을 수 있음을 알렸습니다. 또한 LocalTime을 이용한 로직은 최소화하고 만약 사용한다면 null 체크를 통해 예외를 방지했습니다.
현재는 결석을 처리하는 방법이 이정도 밖에 생각나지 않네요 ㅠㅠ 사실 이렇게 null로 처리하는 것도 문제가 있다고 생각합니다..
현재 선택한 방법에 대해 로키의 생각과 혹시 추천하시는 다른 방법이 있을지 피드백을 받고 싶습니다!
- 테스트와 관련된 질문입니다!
```text
    @Test
    @DisplayName("특정 크루 출석 기록 추가하는 테스트")
    void addAttendanceHistory() {
        // given
        String username = "a";
        LocalDateTime time = LocalDateTime.of(2024, 12, 24, 10, 31);
        // when
        AttendanceResult result = crews.addAttendanceHistory(username, time);
        // then
        assertThat(result).isEqualTo(AttendanceResult.ABSENCE);
    }
```
TDD를 진행하면서 crews의 addAttendanceHistory(특정 크루의 출석 기록을 추가하는 메서드) 테스트 만드려고 했습니다(RED 단계). 하지만 // then 에서 검증을 할 때 정상적으로 잘 들어갔는지 확인하는 로직이 필요했습니다.
그래서 이때 테스트를 위한 메서드(특정 크루의 출석 기록을 가져오는 기능)를 추가해서 검증을 해야할지 고민이였습니다. 물론 운이 좋게도 특정 크루의 출석 기록을 가져오는 기능은 추후에 사용되기에 상관없지만 만약 테스트를 위한 메서드가 추후 도메인 로직에서 사용하지 않으면 테스트를 위한 메서드가 존재해 좋지 않다고 생각이 들었습니다.
해당 상황에서 그렇다면 getter를 통해 확인하는 것이 맞는지 혹은 그 외에 테스트 방법이 있을지 궁금합니다!
또한 코치에게 여쭤봤을 땐 테스트를 위한 생성자를 만들어서 equal 메서드로 해결할거 같다고 하셨습니다. 그런데 의문이 든 점이 테스트를 위한 메서드는 지양해야하는데 테스트를 위한 생성자는 괜찮은건지 의문입니다. 로키의 의견은 어떨지 궁금합니다! 