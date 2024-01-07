package com.jyujyu.dayonetest;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JUnitPracticeTest {

	@Test // 테스트 코드 위에 항상 붙어야 하는 어노테이션
	public void assertEqualsTest() {
		String expect = "Something";
		String actual = "Something";

		// assertEquals() : 두 값이 동일한지 확인
		Assertions.assertEquals(expect, actual);
	}

	@Test
	public void assertNotEqualsTest() {
		String expect = "Something";
		String actual = "Hello";

		// assertNotEquals() : 두 값이 다른지 확인
		Assertions.assertNotEquals(expect, actual);
	}

	@Test
	public void assertTrueTest() {
		Integer a = 10;
		Integer b = 10;

		// assertTrue() : 조건이 참인지 확인
		Assertions.assertTrue(a.equals(b));
	}

	@Test
	public void assertFalseTest() {
		Integer a = 10;
		Integer b = 20;

		// assertFalse() : 조건이 거짓인지 확인
		Assertions.assertFalse(a.equals(b));
	}

	@Test
	public void assertThrowsTest() {
		// assertThrows() : 예외가 발생하는지 확인(어떤 예외타입인지도 확인가능)
		Assertions.assertThrows(RuntimeException.class, () -> {
			throw new RuntimeException("임의로 발생시킨 예외");
		});
	}

	@Test
	public void assertNotNullTest() {
		String value = "Hello";

		// assertNotNull() : 값이 null이 아닌지 확인
		Assertions.assertNotNull(value);
	}

	@Test
	public void assertNullTest() {
		String value = null;

		// assertNull() : 값이 null인지 확인
		Assertions.assertNull(value);
	}

	@Test
	public void assertIterableEqualsTest() {
		List<Integer> list1 = List.of(1, 2);
		List<Integer> list2 = List.of(1, 2);

		// assertIterableEquals() : 두 Iterable 객체가 동일한 요소를 포함하고 있고 순서도 같은지 확인
		Assertions.assertIterableEquals(list1, list2);
	}

	@Test
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
