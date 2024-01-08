package com.jyujyu.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** 롬복 라이브러리가 정상적으로 동작하는지 확인하기 위해 롬복 기능을 사용한 테스트 수행 */
public class LombokTestDataTest {

  @Test
  public void testDataTest() {
    TestData testData = new TestData();

    testData.setName("jyujyu");

    Assertions.assertEquals("jyujyu", testData.getName());
  }
}
