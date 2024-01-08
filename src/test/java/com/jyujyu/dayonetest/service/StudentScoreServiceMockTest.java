package com.jyujyu.dayonetest.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.jyujyu.dayonetest.controller.response.ExamFailStudentResponse;
import com.jyujyu.dayonetest.controller.response.ExamPassStudentResponse;
import com.jyujyu.dayonetest.model.StudentFail;
import com.jyujyu.dayonetest.model.StudentFailFixture;
import com.jyujyu.dayonetest.model.StudentPass;
import com.jyujyu.dayonetest.model.StudentPassFixture;
import com.jyujyu.dayonetest.model.StudentScore;
import com.jyujyu.dayonetest.model.StudentScoreFixture;
import com.jyujyu.dayonetest.model.StudentScoreTestDataBuilder;
import com.jyujyu.dayonetest.repository.StudentFailRepository;
import com.jyujyu.dayonetest.repository.StudentPassRepository;
import com.jyujyu.dayonetest.repository.StudentScoreRepository;

public class StudentScoreServiceMockTest {

	// 리팩토링 : 테스트 메소드 내부 중복 로직 클래스 단위로 추출
	private StudentScoreService studentScoreService;
	private StudentScoreRepository studentScoreRepository;
	private StudentPassRepository studentPassRepository;
	private StudentFailRepository studentFailRepository;

	@BeforeEach // 모든 테스트가 각각 실행되기 이전에 1번씩 수행됨
	public void beforeEach() {
		// Mockito.mock() : 서비스 클래스가 의존성주입 받아야하는 인터페이스의 Mock 객체 생성
		studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
		studentPassRepository = Mockito.mock(StudentPassRepository.class);
		studentFailRepository = Mockito.mock(StudentFailRepository.class);
		studentScoreService = new StudentScoreService(studentScoreRepository, studentPassRepository,
			studentFailRepository);
	}

	@Test
	@DisplayName("첫번째 Mock 테스트")
	public void firstSaveScoreMockTest() {
		// given
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
		// TestData Builder 패턴 : 빌더 패턴이기 때문에 오버라이딩하여 사용가능함
		StudentScore expectStudentScore = StudentScoreTestDataBuilder.passed().build();
		StudentPass expectStudentPass = StudentPassFixture.create(expectStudentScore);

		// ArgumentCaptor : Mock 객체로 로직 확인시, 특정 메소드 호출할 때의 파라미터 타입을 지정 -> 내부에 접근가능하도록 해줌
		ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
		ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

		// when
		studentScoreService.saveScore(
			expectStudentScore.getStudentName(),
			expectStudentScore.getExam(),
			expectStudentScore.getKorScore(),
			expectStudentScore.getEnglishScore(),
			expectStudentScore.getMathScore()
		);

		// then
		// Mockito.verify() : 1st 파라미터의 XX 메소드가 Mockito.times() 의 파라미터값만큼 호출되어야 함
		Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
		StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
		Assertions.assertEquals(expectStudentScore.getStudentName(), capturedStudentScore.getStudentName());
		Assertions.assertEquals(expectStudentScore.getExam(), capturedStudentScore.getExam());
		Assertions.assertEquals(expectStudentScore.getKorScore(), capturedStudentScore.getKorScore());
		Assertions.assertEquals(expectStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore());
		Assertions.assertEquals(expectStudentScore.getMathScore(), capturedStudentScore.getMathScore());

		Mockito.verify(studentPassRepository, Mockito.times(1)).save(studentPassArgumentCaptor.capture());
		StudentPass capturedStudentPass = studentPassArgumentCaptor.getValue();
		Assertions.assertEquals(expectStudentPass.getStudentName(), capturedStudentPass.getStudentName());
		Assertions.assertEquals(expectStudentPass.getExam(), capturedStudentPass.getExam());
		Assertions.assertEquals(expectStudentPass.getAvgScore(), capturedStudentPass.getAvgScore());

		Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
	}

	@Test
	@DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
	public void saveScoreMockTest2() {
		// given : 평균점수가 60점 미만인 경우
		// TestData Fixture 패턴 : 오버라이딩 불가, 고정된 값
		StudentScore expectStudentScore = StudentScoreFixture.failed();
		StudentFail expectStudentFail = StudentFailFixture.create(expectStudentScore);

		ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
		ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);

		// when
		studentScoreService.saveScore(
			expectStudentScore.getStudentName(),
			expectStudentScore.getExam(),
			expectStudentScore.getKorScore(),
			expectStudentScore.getEnglishScore(),
			expectStudentScore.getMathScore()
		);

		// then
		Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
		StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
		Assertions.assertEquals(expectStudentScore.getStudentName(), capturedStudentScore.getStudentName());
		Assertions.assertEquals(expectStudentScore.getExam(), capturedStudentScore.getExam());
		Assertions.assertEquals(expectStudentScore.getKorScore(), capturedStudentScore.getKorScore());
		Assertions.assertEquals(expectStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore());
		Assertions.assertEquals(expectStudentScore.getMathScore(), capturedStudentScore.getMathScore());

		Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());

		Mockito.verify(studentFailRepository, Mockito.times(1)).save(studentFailArgumentCaptor.capture());
		StudentFail capturedStudentFail = studentFailArgumentCaptor.getValue();
		Assertions.assertEquals(expectStudentFail.getStudentName(), capturedStudentFail.getStudentName());
		Assertions.assertEquals(expectStudentFail.getExam(), capturedStudentFail.getExam());
		Assertions.assertEquals(expectStudentFail.getAvgScore(), capturedStudentFail.getAvgScore());
	}

	@Test
	@DisplayName("합격자 명단 가져오기 검증")
	public void getPassStudentListTest() {
		// given
		String givenTestExam = "testexam";
		StudentPass expectStudent1 = StudentPassFixture.create("jyujyu", givenTestExam);
		StudentPass expectStudent2 = StudentPassFixture.create("testName", givenTestExam);
		StudentPass notExpectStudent = StudentPassFixture.create("anotherStudent", "anotherExam");

		// Mockito.when().thenReturn() : when 파라미터의 동작이 호출되면, thenReturn() 파라미터의 내용이 무조건 수행됨
		Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
			expectStudent1,
			expectStudent2,
			notExpectStudent
		));

		StudentScoreService studentScoreService = new StudentScoreService(
			studentScoreRepository,
			studentPassRepository,
			studentFailRepository
		);

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
		String givenTestExam = "testexam";
		StudentFail notExpectStudent = StudentFailFixture.create("jyujyu", "anotherTest");
		StudentFail expectStudent1 = StudentFailFixture.create("testName", givenTestExam);
		StudentFail expectStudent2 = StudentFailFixture.create("testName2", givenTestExam);

		// Mockito.when().thenReturn() : when 파라미터의 동작이 호출되면, thenReturn() 파라미터의 내용이 무조건 수행됨
		Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
			notExpectStudent,
			expectStudent1,
			expectStudent2
		));

		StudentScoreService studentScoreService = new StudentScoreService(
			studentScoreRepository,
			studentPassRepository,
			studentFailRepository
		);

		// when
		var expectFailList = List.of(expectStudent1, expectStudent2).stream()
			.map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
			.toList();
		List<ExamFailStudentResponse> responses = studentScoreService.getFailStudentsList(givenTestExam);

		// then
		Assertions.assertIterableEquals(expectFailList, responses);
	}
}
