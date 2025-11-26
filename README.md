# TDD로 다시보는 문자열 계산기

---

## 🎯 TDD가 제공한 실질적 이점

### 1. 요구사항 탐색 도구로서의 TDD

#### 이스케이프 시퀀스 버그 사전 발견

**문제 상황**

- 사용자가 콘솔에 `//;\n1;2;3` 입력 시 파싱 실패
- `CUSTOM_SUFFIX`가 개행문자(1글자)로 처리되어 실제 입력의 역슬래시+n(2글자)을 찾지 못함

**원인: 초기 요구사항을 제대로 파악하지 못하고 가볍게 이해하고 넘어감**

- Java 리터럴 `"\n"`(개행, 1글자)과 콘솔 입력 `"\\n"`(역슬래시+n, 2글자)의 차이를 고려하지 못함
- 잘못된 가정: 사용자도 `\n`을 입력하겠지 → 사용자는 `\\n` (두 글자)을 입력함

#### 배운 점

- 가볍게 이해한 요구사항을 자세하게 확인 후 검증 필요
- 단위 테스트만으론 부족할 수 있으며 통합 테스트 필수
- "만들면서 테스트"가 아니라 **"테스트하면서 발견"**
- 작은 단위로 검증하며 진행하므로 큰 실수를 조기에 발견
- "이렇게 입력하겠지" → 잘못된 이해 / "실제로 확인해보자" → 확실한 이해와 검증

---

### 2. 리팩토링 안전망으로서의 TDD

#### Law of Demeter 위반 발견

**개선 전 코드**

```java
public class StringCalculator {
    public int calculate(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        Expression expression = Expression.from(input);

        String[] tokens = expression.getDelimiter()
                .split(expression.getNumbersText()); // 개선 사항 인지!

        Numbers numbers = Numbers.from(tokens);
        return numbers.sum();
    }
}
```

- `expression`에 물어보고 (`getDelimiter`)
- 받은 `Delimiter`에 다시 물어봄 (`split`)
- Expression 내부 구조 노출
- 나중에 Expression 구조가 바뀌면 StringCalculator도 수정 필요

#### 새로운 메서드 테스트로 설계

- StringCalculator 입장에서 어떻게 쓰고 싶은가?
- 내부를 몰라도 쓸 수 있게 하려면? → `expression.toNumbers()`

**개선 후 코드**

```java
public int calculate(String input) {
    Expression expression = Expression.from(input);
    Numbers numbers = expression.toNumbers();
    return numbers.sum();
}
```

#### 배운 점

- TDD 사이클 내에 리팩토링이 자연스럽게 포함되어 점진적 개선 가능
- 프로덕션 코드와 테스트 코드가 동시에 개발되니 테스트를 신뢰할 수 있어 **리팩토링 두려움이 사라짐**
- 테스트가 바로 피드백을 줘서 확신이 생김
- 테스트가 수정의 족쇄가 아닌 **구조 변경의 안전망**
- 코드의 정확성뿐만 아니라 **심리적 안정**도 제공

---

### 3. 설계 피드백 도구로서의 TDD

#### 값 객체 equals() 구현 필요성 발견

**테스트 작성 시점**

```java

@Test
void 커스텀_구분자_형식을_파싱한다() {
    String input = "//;\\n1;2;3";
    Expression expression = Expression.from(input);

    assertThat(expression.getDelimiter())
            .isEqualTo(Delimiter.custom(";")); // 커스텀 파싱 확인
}
```

**테스트 실행 후 실패**

```
Expected: calculator.domain.Delimiter@6caf0677
Actual:   calculator.domain.Delimiter@645aa696
```

→ 같은 Delimiter라고 예상했지만 해시코드가 다름! 객체 주소를 비교하고 있었음!

**해결: equals() 구현**

```java

@Override
public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
        return false;
    }
    Delimiter delimiter = (Delimiter) o;
    return Objects.equals(pattern, delimiter.pattern);
}

@Override
public int hashCode() {
    return Objects.hashCode(pattern);
}
```

#### 배운 점

- 테스트를 작성하는 순간 비교가 필요하구나 설계 개선 발견  !
    - 이전에는 Delimiter만든 뒤, 잘 작동하니 equals의 필요성 조차 인식 하지 않고 지나침
    - 버그의 가능성 증가, 이후 디버깅 시간 증가
- 값 객체는 equals() 필수임을 경험으로 체득
    - 과거 : 그냥 규칙이니, 일단 equal 구현하자 !( 종종 뺴먹음)
    - TDD : 비교를 해보니 equal가 없으니 버그가 생기네 !
- 설계 결정 과정의 변화
    - 과거 : `Delimiter` 는 String 필드가 있고 상황에 맞는 `Delimiter` 를 생성하고 반환해주는 메서드가 필요하네 ! 객체지향적으로 역할과 메시지와 같은 추상적 설계만 해서 필요성
      인식조차 못하고, 불명확하고 미완성 상태.
    - TDD : `Delimiter` 를 어떻게 사용하지 ? 테스트 작성 후 아 ! 비교가 필요하네 ! 즉시 인식 후 설계 와 구현 진행 최종적으로 안전상 테스트 생성

---

# TDD 학습 회고: 고민과 결정

## 🤔 어떤 고민이 있었고, 왜 그런 결정을 했는지

---

## 1. TDD 과정에서의 Getter 사용 vs 행위 테스트

### 테스트 초기

```java

@Test
void 기본_구분자_형식을_파싱한다() {
    // given
    String input = "1,2:3";

    // when
    Expression expression = Expression.from(input);

    // then
    assertThat(expression.getNumbersText()).isEqualTo("1,2:3");
}
```

- `Expression.from()`이 파싱한다
- 파싱이 잘 됐는지 확인하고 싶다. 뭘 검증하지? 일단 데이터가 잘 저장됐는지 확인하자!
- 필드 값을 확인하기 위해 getter 사용

→ 테스트 통과 후 명확하게 검증되었으니 다음 기능으로 넘어감

---

### 필드명 수정 리팩토링 시도

```java
public class Expression {
    private final String numbersText; // 현재 변수명    
    private final String rawNumbers;  // 더 명확한 이름!

    public String getNumbersText() {
        return rawNumbers;
    }
}
```

- 필드명을 `rawNumbers`로 변경
- getter 이름도 바꿔야 하나?

| 옵션   | 방식                       | 문제점                                        |
|------|--------------------------|--------------------------------------------|
| 옵션 1 | `getRawNumbers()` - 일관성  | 모든 테스트에서 `getNumbersText()` 사용 중, 전부 수정 필요 |
| 옵션 2 | `getNumbersText()` - 호환성 | 필드명과 getter명 불일치, 혼란스러운 코드                 |

> "필드명 하나 바꾸는데 테스트를 다 고쳐야 하는데 이게 맞나..? 내부 구조 변경이 이렇게 어렵나? 뭔가 잘못된 것 같은데..."

---

### 내부 로직 개선 리팩토링 시도

```java
public static Expression from(String input) {
    return new Expression(input);        // 현재 로직
    return new Expression(input.trim()); // trim 추가
}
```

```java

@Test
void 기본_구분자_형식을_파싱한다() {
    Expression expression = Expression.from("1,2:3");
    assertThat(expression.getNumbersText()).isEqualTo("1,2:3");
}
```

- trim을 추가해 내부 구현을 개선했지만 **기존 테스트들이 모두 깨짐**

---

### 문제점 분석

- **테스트가 내부 구조에 의존**
    - delimiter 필드 존재 가정
    - numbersText 필드 존재 가정

- **리팩토링 시 테스트 깨짐**
    - 필드명 변경 → 모든 테스트 수정 필요

- **내부 구조 노출**
    - Expression이 어떻게 구현됐는지 알아야 함
    - 캡슐화 위반

- **불필요한 public API**
    - 테스트만을 위한 getter
    - 실제로는 사용하지 않는 메서드

---

### 최종 결정: 구체적인 상태가 아니라 행위를 테스트하자!

- Expression의 진짜 목적은 뭐지? 최종 결과는?
    - **숫자들을 추출하는 것! Numbers 객체!**

> "내부 상태(numbersText)를 검증하는 게 아니라 최종 행위(toNumbers)를 검증하면 되는구나!"

**행위 테스트의 장점**

| 항목          | 설명                                          |
|-------------|---------------------------------------------|
| 테스트가 행위에 집중 | "Expression이 올바른 Numbers를 생성하는가" - 내부 구조 무관 |
| 리팩토링 내성 강함  | 내부 구조 자유롭게 변경 가능, 테스트 수정 불필요                |
| 캡슐화 유지      | 내부 구조 숨김, 필요한 API만 노출                       |
| 명확한 의도      | "무엇을 하는지" 명확, "어떻게 하는지"는 구현 세부사항            |

---

### 배운 점

#### What vs How 테스트 비교

| 구분 | How 테스트 (기존)   | What 테스트 (최종) |
|----|----------------|---------------|
| 질문 | 어떻게 저장했어?      | 무엇을 만드나?      |
| 대상 | 내부 상태(필드)      | 최종 결과(행위)     |
| 의존 | 구현 세부사항        | 인터페이스/계약      |
| 변경 | 구현 바뀌면 테스트도 깨짐 | 리팩토링 내성       |

#### 캡슐화와 테스트의 관계

**Getter로 캡슐화 깨짐**

- 내부 구조 노출: "numbersText 필드가 있다"는 걸 알림
- 변경의 파급효과: 필드 바꾸면 getter도 바뀜 → 모든 사용처 영향
- Tell, Don't Ask 위반: 데이터를 꺼내서 밖에서 처리

**행위로 캡슐화 유지**

- 내부 구조 숨김: numbersText의 존재 몰라도 됨
- 변경의 격리: 내부 바뀌어도 외부 영향 없음
- Tell, Don't Ask 준수: "Numbers 만들어줘" (시킴)

---

### 💡 인상 깊었던 깨달음

> **잘못된 테스트가 리팩토링을 방해할 수도 있다!**
> **올바른 테스트는 날개다!**

| 과거 이해                     | TDD 후 이해                                                  |
|---------------------------|-----------------------------------------------------------|
| "상태 테스트는 나쁘고 행위 테스트는 좋다"  | "상태 테스트는 리팩토링을 방해하고 행위 테스트는 리팩토링을 돕는다"                    |
| "How가 아니라 What을 테스트하라"    | "How를 테스트하면 구현 바꿀 때마다 테스트도 바뀜, What을 테스트하면 구현 자유롭게 변경 가능" |
| 왜? 구체에 의존하면 수정 힘들어서? 오키오키 | 구체적 이유: 상태는 구현 세부사항, 행위는 인터페이스/계약. 구현은 자주 바뀜, 계약은 안정적     |

---

## 2. 행위 테스트로 전환 후 Expression 테스트에서 Numbers.from() 사용

### 인식

```java

@Test
void 커스텀_구분자로_Numbers를_생성한다() {
    Expression expression = Expression.from("//;\\n1;2;3");

    Numbers expected = Numbers.from(new String[]{"1", "2", "3"});
    assertThat(expression.toNumbers()).isEqualTo(expected);
}
```

- Expression을 테스트하면서 `Numbers.from()`을 사용
- `Numbers.from()`에 버그가 있으면 Expression 테스트도 실패하지 않을까?
- 이게 진짜 단위 테스트가 맞나? Mock을 사용해야 하는 건 아닐까?
- Expression을 테스트하는데 Numbers에 의존하고 있다는 사실이 찜찜하고 불안함
- 단위 테스트라면 테스트 대상만 검증해야 하는 것 아닌가?

---

### 고민

- 만약 `Numbers.from()`에 버그가 있다면 어떻게 될까?
- expected 값도 잘못되고, actual 값도 잘못되어 테스트가 통과할 수도 있지 않을까?
- 어느 쪽이든 Expression의 문제인지 Numbers의 문제인지 구분하기 어려울 것 같음
- Numbers를 Mock으로 만들면 Expression만 순수하게 테스트할 수 있지 않을까?
    - 하지만 값 객체를 Mock으로 만들면 다시 리팩토링 내성이 낮아질 것 같은데...

---

### 최종 결정: 고전파 스타일 채택

#### 이유 1: Numbers는 도메인 객체(값 객체)

- Numbers는 외부 시스템이 아니라 **같은 도메인 레이어의 값 객체**
- String이나 Integer를 테스트에서 사용할 때 Mock으로 만들지 않는 것처럼, Numbers도 실제 객체를 사용하는 것이 자연스러움
- Numbers는 협력자가 아니라 **결과물의 일부**

#### 이유 2: 각자의 테스트가 책임 보장

- `NumbersTest`에서 `Numbers.from()`의 정확성을 검증
- `ExpressionTest`에서는 올바른 Numbers 생성을 검증
- NumbersTest가 통과하면 `Numbers.from()`을 신뢰할 수 있고, 그 신뢰 위에서 ExpressionTest가 사용 가능 → **신뢰 가능!**

#### 이유 3: Mock은 외부 의존성에만

- 외부 의존성은 느리고, 불안정하며, 제어하기 어려움
- 도메인 객체는 빠르고, 안정적이며, 제어 가능 → **실제 객체 사용!**

---

### 배운 점

1. **모든 의존성을 Mock할 필요는 없다**
    - 도메인 객체는 실제 사용해도 된다
    - 중요한 것은 **"무엇을 격리해야 하는가"**를 판단하는 것

2. **테스트의 목적을 명확히 해야 한다**
    - ExpressionTest의 목적: Expression이 올바른 Numbers를 생성하는지 검증
    - `Numbers.from()`이 올바른지는 NumbersTest의 책임

3. **신뢰의 체인을 구축해야 한다**
    - 하위 레벨 테스트가 통과하면 상위 레벨에서는 그것을 신뢰하고 사용
    - 모든 것을 의심하고 Mock으로 격리하면 테스트가 복잡해지고 유지보수가 어려워짐

