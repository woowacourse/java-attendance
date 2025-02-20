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
    - [작성글 링크](https://prolog.techcourse.co.kr/studylogs/3936)

## 객체지향 생활체조 요구사항을 얼마나 잘 충족했다고 생각하시나요?

### 1~5점 중에서 선택해주세요.

- [ ] 1 (전혀 충족하지 못함)
- [ ] 2
- [x] 3 (보통)
- [ ] 4
- [ ] 5 (완벽하게 충족)

### 선택한 점수의 이유를 적어주세요.

<!-- 이유 작성 -->

- mvc 패턴을 지키기 위해 노력하며

## 어떤 부분에 집중하여 리뷰해야 할까요?

<!-- 리뷰어가 효과적으로 피드백할 수 있도록 중점적으로 피드백받고 싶은 내용을 공유해주세요.  
예를 들어, 가장 고민했던 점이나 여전히 어려운 부분, 그리고 이에 대한 생각을 적을 수 있습니다. -->

1. 현재 제 도메인들의 관계는 제일 바깥에 Crews 가 있고 그 안에 Crew, 그리고 Crew 안에 histories, <br>
   Histories 안에 History 와 같은 자료 구조로 되어있습니다. <br>
   위와 같이 설계한 이유는 일급 컬렉션 형태를 지키기 위해서였습니다. <br>
   그런데 이제 문제는 워낙 예를 들어 history 내부의 값이 필요할 때 메소드를 만들고 <br>
   불필요하게 계속 꺼내는 형태로 해당 내부의 값을 꺼내는 역할만 하는 메소드들을 만들어야 했습니다. <br>
   이에 따라 뭔가 불필요한 코드들이 늘어난 것 같은데 일급 컬렉션을 사용하기 위해서는 어쩔 수 없는 부분일까요?
   아니면 좀 더 다른 설계로 했어야할까요? <br>
   예시

  ```java
    public void editHistory(LocalDateTime attendanceTime) {
    histories.editHistory(attendanceTime);
}

public LocalDateTime getHistoryDate(LocalDateTime time) {
    return histories.getHistory(time);
}

public AbsenceLevel getClassifyAbsenceLevel(LocalDateTime time) {
    return histories.classifyAbsenceLevel(time);
}
  ```

