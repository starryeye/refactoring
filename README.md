# refactoring
리팩토링 연습

## subject
- 추상화(추상화 레벨 나누기) 수단
  - 네이밍(메서드 이름, 파라미터)
  - 메서드 추출
- 가독성
  - 상수 추출 (일종의 추상화)
  - 얼리 리턴
  - 중첩 반복문 -> 스트림
  - 부정연산자 피하기
  - 의도한 예외와 의도하지 않은 예외 구분 처리
- 객체로 추상화 (높은 응집도와 낮은 결합도)
  - 도메인 객체 추출
  - SRP, OCP, LSP, ISP, DIP
  - Value Object (vs Entity)
  - 일급 컬렉션
  - Enum
- 의존성
  - 상속 구조에서 조합 구조로 변경
  - template callback pattern
  - 패키지 단위 의존성
- 코드 스타일
  - 메서드 나열 순서
  - sonarlint
  - IDE 정렬
