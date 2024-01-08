package com.jyujyu.dayonetest;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MyCalculatorRepeatableTest {

  @DisplayName("덧셈을 5번 반복하며 테스트")
  @RepeatedTest(5) // 파라미터의 값만큼 테스트 반복 수행
  public void repeatedAddTest() {
    // given
    MyCalculator myCalculator = new MyCalculator();

    // when
    myCalculator.add(10.0);

    // then
    Assertions.assertEquals(10.0, myCalculator.getResult());
  }

  @DisplayName("덧셈을 5번 파라미터를 넣어주며 반복하며 테스트")
  @ParameterizedTest // 테스트 메소드가 파라미터를 가질 수 있도록 함
  @MethodSource("parameterizedTestParameters") // 미리 만들어둔 파라미터를 반환하는 메소드의 이름을 파라미터로 받아 반환값을 파라미터로 주입
  public void parameterizedTest(Double addValue, Double expectValue) {
    // given
    MyCalculator myCalculator = new MyCalculator(0.0);

    // when
    myCalculator.add(addValue);

    // then
    Assertions.assertEquals(expectValue, myCalculator.getResult());
  }

  // MethodSource 어노테이션의 파라미터로 메소드명을 삽입
  // org.junit.jupiter.params.provider.Arguments
  public static Stream<Arguments> parameterizedTestParameters() {
    return Stream.of(
        Arguments.of(10.0, 10.0),
        Arguments.of(8.0, 8.0),
        Arguments.of(4.0, 4.0),
        Arguments.of(16.0, 16.0),
        Arguments.of(13.0, 13.0));
  }

  @DisplayName("파라미터를 넣어주며 사칙연산 2번 테스트")
  @ParameterizedTest
  @MethodSource("parameterizedComplicatedCalculatorTestParameters")
  public void parameterizedComplicatedCalculatorTest(
      Double addValue,
      Double minusValue,
      Double multiplyValue,
      Double divideValue,
      Double expectValue) {
    // given
    MyCalculator myCalculator = new MyCalculator();

    // when
    Double result =
        myCalculator
            .add(addValue)
            .minus(minusValue)
            .multiply(multiplyValue)
            .divide(divideValue)
            .getResult();

    // then
    Assertions.assertEquals(expectValue, result);
  }

  public static Stream<Arguments> parameterizedComplicatedCalculatorTestParameters() {
    return Stream.of(Arguments.of(10.0, 4.0, 2.0, 3.0, 4.0), Arguments.of(4.0, 2.0, 4.0, 4.0, 2.0));
  }
}
