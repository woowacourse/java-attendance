<!-- 

## 코드 리뷰 팁

- 코드와 관련된 질문이 있다면, PR 본문에 적기 보다는 해당 코드를 선택하고 코멘트를 남겨주세요.
  - [참고: Adding comments to a pull request](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/reviewing-changes-in-pull-requests/commenting-on-a-pull-request#adding-comments-to-a-pull-request)

-->

안녕하세요 앤지! 2단계 리뷰 요청드립니다!😄

## 체크 리스트

- [x] 미션의 필수 요구사항을 모두 구현했나요?
- [x] Gradle `test`를 실행했을 때, 모든 테스트가 정상적으로 통과했나요?
- [x] 애플리케이션이 정상적으로 실행되나요?
- [x] [프롤로그](https://prolog.techcourse.co.kr)에 셀프 체크를 작성했나요?
    - <!-- https://prolog.techcourse.co.kr/studylogs/3910 -->

## 객체지향 생활체조 요구사항을 얼마나 잘 충족했다고 생각하시나요?

### 1~5점 중에서 선택해주세요.

- [ ] 1 (전혀 충족하지 못함)
- [ ] 2
- [x] 3 (보통)
- [ ] 4
- [ ] 5 (완벽하게 충족)

### 선택한 점수의 이유를 적어주세요.

<!-- 이유 작성 -->
1단계의 코드보다 객체지향의 생활체조를 더 신경 써서 구현해보앗습니다.

## 어떤 부분에 집중하여 리뷰해야 할까요?

<!-- 리뷰어가 효과적으로 피드백할 수 있도록 중점적으로 피드백받고 싶은 내용을 공유해주세요.  
예를 들어, 가장 고민했던 점이나 여전히 어려운 부분, 그리고 이에 대한 생각을 적을 수 있습니다. -->

이번에도 역시 tdd에 집중해서 공부해보았습니다. tdd를 사용하면서 getter()의 사용에 관해서 크루들과 이야기를 나누어 보았고,
결론은 이번 미션에서는 extracting 보다 getter()를 사용해도 된다라는 생각이 나왔습니다.


---
이번 미션에서 가장 고민 되었던 부분은 원시 값 포장,일급 컬렉션 부분이었습니다.
tdd를 하는 과정에서 객체지향 생활체조에 맞춰서 원시 값 포장과 일급 컬렉션을 사용하다 보니, 너무 깊게까지 포장하게 된 것 같습니다.

**크루의 관한 정보를 관리하는** 이라는 역할을 가진 Crew 라는 클래스를 만들게 되었을 때,제가 생각한 Crew가 가져야할 인스턴스 변수로는
두가지가 있었습니다.

**nickname**과 크루의 출석 기록을 저장하는 **List <LocalDateTime\> localDateTimes** 이었습니다.

이 두가지를 만들었을 때 nickname에 관한 기능 요구사항은 존재하지 않아 포장 하지 않아도 된다라는 생각이 들어 포장하지 않았습니다.
하지만, 시간 원시 값(LocalDateTime)에 대한 상태와 검증(주말,공휴일의 시간은 입력할 수 없다.)를 위해서 LocalDateTime을 AttendTime으로 포장하게 되었습니다.

또한 포장 된 AttendTime 의 리스트를 일급 컬렉션으로 관리하면 편할 것 같다는 생각에 List<AttendTime> 을 포장한 AttendTimes를 만들게 되었습니다.
AttendTimes에서는 출석 삭제,추가,총 지각 횟수,결석 횟수 등 을 관리하게 됩니다.

여기서 고민은 너무 깊게 포장하였나라는 고민이 되었습니다.

외부 객체가 결국 물어봐야하는 값은 crew에게 물어봐야하는데, 출석과 같은 관리는 모두 AttendTimes에서 메서드를 가지고 있어서
crew는 결국 AttendTimes의 메서드를 호출하는 메서드를 만들게 되었습니다.

~~~java
class Crew {
  
...

    public int getCrewAttendedCount() {
        return attendTimes.calculateAttendedCount();
    }

    public int getCrewLateCount() {
        return attendTimes.calculateLateCount();
    }

    public int getCrewAbsentCount() {
        return attendTimes.calculateAbsentCount();
    }
~~~

이 부분이 마음에 걸렸습니다. 원래 일급 컬렉션을 사용하면 자연스러운 현상인지,아니면 crew가 가져도 되는 로직을 일급 컬렉션에게 넘겨버려 과도하게 감싼 것 인지 고민이 되어 앤지의 생각을 들어보고 싶습니다.

이 부분이 마음에 걸리기 시작하니,enum의 메서드를 사용하여 반환하는 과정도 마음에 걸렸습니다.

~~~java
class Crew {
  
...

    public DangerousTarget getDismissalStatus() {
        return DangerousTarget.getWarningStatus(attendTimes.calculateLateCount(), attendTimes.calculateAbsentCount());
    }
~~~

이 부분 역시 DangerousTarget의 enum 메서드를 사용하는 부분이 그저 Crew 는 중계역할을 하고 있는 것인가?
에서 헷갈리기 시작했습니다. 아니면 일급 컬렉션과 enum을 잘 사용하고 있는 것인지 궁금합니다.

---

### 값들을 포장하는 앤지의 기준이 궁금합니다. 현재 이 포장 상태가 괜찮다고 생각 드시나요?

---

### 또 이번에 크루와 이야기 해보다, enum에 상수와 상태를 비교,계산 하는 로직을 넣는 것을 두려워 하지 말자라는 이야기를 해보았는데 이 부분에 대해서도 앤지의 생각이 궁금합니다!

---

### tdd를 구현하다,일급 컬렉션을 만들게 되었을 때 일급 컬렉션의 메서드를 먼저 tdd를 해야할지, 아니면 crew와 같이 일급컬렉션을 사용하는 클래스를 마저 tdd로 구현한 뒤에,나중에 일급 컬렉션을 테스트 하시는지 앤지의 생각도 궁금해요!
