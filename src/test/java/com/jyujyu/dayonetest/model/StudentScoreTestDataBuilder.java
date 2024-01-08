package com.jyujyu.dayonetest.model;

public class StudentScoreTestDataBuilder {

  // StudentScore가 가질 수 있는 상태 2가지 : passed, failed
  // builder 클래스를 리턴
  public static StudentScore.StudentScoreBuilder passed() {
    return StudentScore.builder()
        .korScore(80)
        .englishScore(100)
        .mathScore(90)
        .studentName("defaultName")
        .exam("defaultExam");
  }

  public static StudentScore.StudentScoreBuilder failed() {
    return StudentScore.builder()
        .korScore(50)
        .englishScore(40)
        .mathScore(30)
        .studentName("defaultName")
        .exam("defaultExam");
  }
}
