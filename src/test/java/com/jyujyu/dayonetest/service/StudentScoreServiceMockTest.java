package com.jyujyu.dayonetest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jyujyu.dayonetest.repository.StudentFailRepsitory;
import com.jyujyu.dayonetest.repository.StudentPassRepository;
import com.jyujyu.dayonetest.repository.StudentScoreRepository;

public class StudentScoreServiceMockTest {

	@Test
	@DisplayName("첫번째 Mock 테스트")
	public void firstSaveScoreMockTest() {
		// given
		// Mockito.mock() : 서비스 클래스가 의존성주입 받아야하는 인터페이스의 Mock 객체 생성
		StudentScoreService studentScoreService = new StudentScoreService(
			Mockito.mock(StudentScoreRepository.class),
			Mockito.mock(StudentPassRepository.class),
			Mockito.mock(StudentFailRepsitory.class)
		);
		String givenStudentName = "jyujyu";
		String givenExam = "testexam";
		Integer givenKorScore = 80;
		Integer givenEnglishScore = 100;
		Integer givenMathScore = 60;

		// when
		studentScoreService.saveScore(
			givenStudentName,
			givenExam,
			givenKorScore,
			givenEnglishScore,
			givenMathScore
		);
	}
}
