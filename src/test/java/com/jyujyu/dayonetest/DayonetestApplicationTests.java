package com.jyujyu.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyujyu.dayonetest.model.StudentScoreFixture;
import com.jyujyu.dayonetest.repository.StudentScoreRepository;

import jakarta.persistence.EntityManager;

class DayonetestApplicationTests extends IntegrationTest {

	@Autowired
	private StudentScoreRepository studentScoreRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	void contextLoads() {
		// given
		var studentScore = StudentScoreFixture.passed();
		var savedStudentScore = studentScoreRepository.save(studentScore);

		entityManager.flush();
		entityManager.clear();

		// when
		var queryStudentScore = studentScoreRepository.findById(savedStudentScore.getId()).orElseThrow();

		// then
		Assertions.assertEquals(savedStudentScore.getId(), queryStudentScore.getId());
		Assertions.assertEquals(savedStudentScore.getStudentName(), queryStudentScore.getStudentName());
		Assertions.assertEquals(savedStudentScore.getMathScore(), queryStudentScore.getMathScore());
		Assertions.assertEquals(savedStudentScore.getEnglishScore(), queryStudentScore.getEnglishScore());
		Assertions.assertEquals(savedStudentScore.getKorScore(), queryStudentScore.getKorScore());
	}

}
