package com.jyujyu.dayonetest.controller.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode // 객체간의 레퍼런스 비교시 동일한 값에 대해 true를 반환하도록 함(@Getter + @EqualsAndHashCode or @Data)
@AllArgsConstructor
public class ExamPassStudentResponse {
	private final String studentName;
	private final Double avgScore;
}
