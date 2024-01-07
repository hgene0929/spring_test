package com.jyujyu.dayonetest.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jyujyu.dayonetest.controller.response.ExamFailStudentResponse;
import com.jyujyu.dayonetest.controller.response.ExamPassStudentResponse;
import com.jyujyu.dayonetest.model.StudentFail;
import com.jyujyu.dayonetest.model.StudentPass;
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

	@Test
	@DisplayName("합격자 명단 가져오기 검증")
	public void getPassStudentListTest() {
		// given
		StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
		StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
		StudentFailRepsitory studentFailRepsitory = Mockito.mock(StudentFailRepsitory.class);

		StudentPass expectStudent1 = StudentPass.builder().id(1L).studentName("jyujyu").exam("testexam").avgScore(70.0).build();
		StudentPass expectStudent2 = StudentPass.builder().id(2L).studentName("test").exam("testexam").avgScore(80.0).build();
		StudentPass notExpectStudent = StudentPass.builder().id(3L).studentName("imnot").exam("secondexam").avgScore(90.0).build();

		// Mockito.when().thenReturn() : when 파라미터의 동작이 호출되면, thenReturn() 파라미터의 내용이 무조건 수행됨
		Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
			expectStudent1,
			expectStudent2,
			notExpectStudent
		));

		StudentScoreService studentScoreService = new StudentScoreService(
			studentScoreRepository,
			studentPassRepository,
			studentFailRepsitory
		);

		String givenTestExam = "testexam";

		// when
		var expectResponses = List.of(expectStudent1, expectStudent2).stream()
			.map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
			.toList();
		List<ExamPassStudentResponse> responses = studentScoreService.getPassStudentsList(givenTestExam);

		// then
		Assertions.assertIterableEquals(expectResponses, responses);
	}

	@Test
	@DisplayName("불합격자 명단 가져오기 검증")
	public void getFailStudnetsListTest() {
		// given
		StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
		StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
		StudentFailRepsitory studentFailRepsitory = Mockito.mock(StudentFailRepsitory.class);

		StudentFail notExpectStudent = StudentFail.builder().id(1L).studentName("jyujyu").exam("secondexam").avgScore(70.0).build();
		StudentFail expectStudent1 = StudentFail.builder().id(2L).studentName("test").exam("testexam").avgScore(80.0).build();
		StudentFail expectStudent2 = StudentFail.builder().id(3L).studentName("imnot").exam("testexam").avgScore(90.0).build();

		// Mockito.when().thenReturn() : when 파라미터의 동작이 호출되면, thenReturn() 파라미터의 내용이 무조건 수행됨
		Mockito.when(studentFailRepsitory.findAll()).thenReturn(List.of(
			notExpectStudent,
			expectStudent1,
			expectStudent2
		));

		StudentScoreService studentScoreService = new StudentScoreService(
			studentScoreRepository,
			studentPassRepository,
			studentFailRepsitory
		);

		String givenTestExam = "testexam";

		// when
		var expectFailList = List.of(expectStudent1, expectStudent2).stream()
			.map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
			.toList();
		List<ExamFailStudentResponse> responses = studentScoreService.getFailStudentsList(givenTestExam);

		// then
		Assertions.assertIterableEquals(expectFailList, responses);
	}
}
