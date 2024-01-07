package com.jyujyu.dayonetest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * AAA 패턴(GWT) 실습
 * MyCalculator 클래스의 코드에 대한 직접 테스트(테스트 코드 사용 O)
 */
class MyCalculatorTest {

	@Test
	@DisplayName("MyCalculator 덧셈 테스트")
	void addTest() {
		// AAA 패턴

		// Arrange - 준비
		MyCalculator myCalculator = new MyCalculator();

		// Act - 행동
		myCalculator.add(10.0);

		// Assert - 단언/검증
		Assertions.assertEquals(10.0, myCalculator.getResult());
	}

	@Test
	@DisplayName("MyCalculator 뺄셈 테스트")
	void minusTest() {
		// GWT 패턴

		// Given - Arrange
		MyCalculator myCalculator = new MyCalculator(10.0);

		// When - Act
		myCalculator.minus(5.0);

		// Then - Assert
		Assertions.assertEquals(5.0, myCalculator.getResult());
	}

	@Test
	@DisplayName("MyCalculator 곱셈 테스트")
	void multiplyTest() {
		// 일반적으로 회사에서 사용하는 테스트 관련 주석처리

		// given
		MyCalculator myCalculator = new MyCalculator(2.0);

		// when
		myCalculator.multiply(2.0);

		// then
		Assertions.assertEquals(4.0, myCalculator.getResult());
	}

	@Test
	@DisplayName("MyCalculator 나누기 테스트")
	void divideTest() {
		MyCalculator myCalculator = new MyCalculator(10.0);

		myCalculator.divide(2.0);

		Assertions.assertEquals(5.0, myCalculator.getResult());
	}

	@Test
	@DisplayName("MyCalculator 사칙연산 테스트")
	void complicatedCalculateTest() {
		// given
		MyCalculator myCalculator = new MyCalculator();

		// when
		Double result = myCalculator
			.add(10.0)
			.minus(4.0)
			.multiply(2.0)
			.divide(3.0)
			.getResult();

		// then
		Assertions.assertEquals(4.0, result);
	}

	@Test
	// 특정 규칙을 담은 테스트에 대해서는 자세하게 아래와 같이 이름을 지정해주는 것 권장
	@DisplayName("MyCalculator 0으로 나누었을 때에는 ZeroDivisionException을 발생시켜야 합니다")
	void divideZeroTest() {
		// given
		MyCalculator myCalculator = new MyCalculator(10.0);

		// when & then
		Assertions.assertThrows(MyCalculator.ZeroDivisionException.class, () -> {
			myCalculator.divide(0.0);
		});
	}
}
