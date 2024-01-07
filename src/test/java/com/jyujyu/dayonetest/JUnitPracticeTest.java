package com.jyujyu.dayonetest;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

// default : 테스트 메소드명을 그대로 출력
// ReplaceUnderscores : 테스트 메소드에 있는 언더스코어(_)를 공백으로 대체
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JUnitPracticeTest {

	@Test // 테스트 코드 위에 항상 붙어야 하는 어노테이션
	@DisplayName("Assert Equals 메소드 테스트") // 직접 이름을 지정(DisplayNameGeneration과 충돌시, DisplayName 우선적용)
	public void assert_equals_test() {
		String expect = "Something";
		String actual = "Something";

		// assertEquals() : 두 값이 동일한지 확인
		Assertions.assertEquals(expect, actual);
	}

	@Test
	@DisplayName("Assert Not Equals 메소드 테스트")
	public void assertNotEqualsTest() {
		String expect = "Something";
		String actual = "Hello";

		// assertNotEquals() : 두 값이 다른지 확인
		Assertions.assertNotEquals(expect, actual);
	}

	@Test
	@DisplayName("Assert True 메소드 테스트")
	public void assertTrueTest() {
		Integer a = 10;
		Integer b = 10;

		// assertTrue() : 조건이 참인지 확인
		Assertions.assertTrue(a.equals(b));
	}

	@Test
	@DisplayName("Assert False 메소드 테스트")
	public void assertFalseTest() {
		Integer a = 10;
		Integer b = 20;

		// assertFalse() : 조건이 거짓인지 확인
		Assertions.assertFalse(a.equals(b));
	}

	@Test
	@DisplayName("Assert Throws 메소드 테스트")
	public void assertThrowsTest() {
		// assertThrows() : 예외가 발생하는지 확인(어떤 예외타입인지도 확인가능)
		Assertions.assertThrows(RuntimeException.class, () -> {
			throw new RuntimeException("임의로 발생시킨 예외");
		});
	}

	@Test
	@DisplayName("Assert Not Null 메소드 테스트")
	public void assertNotNullTest() {
		String value = "Hello";

		// assertNotNull() : 값이 null이 아닌지 확인
		Assertions.assertNotNull(value);
	}

	@Test
	@DisplayName("Assert Null 메소드 테스트")
	public void assertNullTest() {
		String value = null;

		// assertNull() : 값이 null인지 확인
		Assertions.assertNull(value);
	}

	@Test
	@DisplayName("Assert Iterable 메소드 테스트")
	public void assertIterableEqualsTest() {
		List<Integer> list1 = List.of(1, 2);
		List<Integer> list2 = List.of(1, 2);

		// assertIterableEquals() : 두 Iterable 객체가 동일한 요소를 포함하고 있고 순서도 같은지 확인
		Assertions.assertIterableEquals(list1, list2);
	}

	@Test
	@DisplayName("Assert All 메소드 테스트")
	public void assertAllTest() {
		// 1st 테스트
		String expect = "Something";
		String actual = "Something";

		// 2nd 테스트
		List<Integer> list1 = List.of(1, 2);
		List<Integer> list2 = List.of(1, 2);

		// assertAll() : 여러 assertion을 그룹화하여 모두 실행하고, 실패한 경우에도 나머지 assertion들을 계속 실행함
		Assertions.assertAll("Assert All", List.of(
			() -> { Assertions.assertEquals(expect, actual); },
			() -> { Assertions.assertIterableEquals(list1, list2); }
		));
	}
}
