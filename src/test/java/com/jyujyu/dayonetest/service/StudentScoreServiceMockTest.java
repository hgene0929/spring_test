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

	@Test
	@DisplayName("성적 저장 로직 검증 / 60점 이상인 경우")
	public void saveScoreMockTest() {
		// given : 평균점수가 60점 이상인 경우
		StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
		StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
		StudentFailRepsitory studentFailRepsitory = Mockito.mock(StudentFailRepsitory.class);

		StudentScoreService studentScoreService = new StudentScoreService(
			studentScoreRepository,
			studentPassRepository,
			studentFailRepsitory
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

		// then
		// Mockito.verify() : 1st 파라미터의 XX 메소드가 Mockito.times() 의 파라미터값만큼 호출되어야 함
		Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(studentPassRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(studentFailRepsitory, Mockito.times(0)).save(Mockito.any());
	}

	@Test
	@DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
	public void saveScoreMockTest2() {
		// given : 평균점수가 60점 미만인 경우
		StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
		StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
		StudentFailRepsitory studentFailRepsitory = Mockito.mock(StudentFailRepsitory.class);

		StudentScoreService studentScoreService = new StudentScoreService(
			studentScoreRepository,
			studentPassRepository,
			studentFailRepsitory
		);

		String givenStudentName = "jyujyu";
		String givenExam = "testexam";
		Integer givenKorScore = 40;
		Integer givenEnglishScore = 40;
		Integer givenMathScore = 60;

		// when
		studentScoreService.saveScore(
			givenStudentName,
			givenExam,
			givenKorScore,
			givenEnglishScore,
			givenMathScore
		);

		// then
		Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
		Mockito.verify(studentFailRepsitory, Mockito.times(1)).save(Mockito.any());
	}
}
