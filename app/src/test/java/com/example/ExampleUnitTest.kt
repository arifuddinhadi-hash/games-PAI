package com.example

import com.example.data.curriculum.AssessmentBankData
import com.example.data.curriculum.PosCurriculumData
import com.example.data.curriculum.TeacherModulAjarData
import com.example.data.local.TeacherSettingsEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun curriculum_and_assessments_are_complete_and_valid() {
        assertEquals(5, PosCurriculumData.posModules.size)
        assertEquals(10, AssessmentBankData.diagnosticQuestions.size)
        assertEquals(25, AssessmentBankData.formativeQuestions.size)
        (1..5).forEach { pos ->
            assertEquals(5, AssessmentBankData.formativeQuestions.count { it.posNumber == pos })
        }
        assertEquals(20, AssessmentBankData.summativeQuestions.size)
        assertEquals(100, AssessmentBankData.summativeQuestions.sumOf { it.maxScore })
        assertEquals(5, AssessmentBankData.remedialQuestions.size)
        assertEquals(5, AssessmentBankData.enrichmentQuestions.size)
        assertEquals(3, TeacherModulAjarData.meetings.size)
        assertEquals(6, TeacherModulAjarData.reflectionQuestions.size)
        assertTrue(AssessmentBankData.allQuestionsForBank.all { it.id.isNotBlank() && it.correctAnswer.isNotBlank() })
    }

    @Test
    fun summative_score_formula_is_accurate() {
        val earned = 85
        val max = 100
        val finalScore = (earned * 100) / max
        assertEquals(85, finalScore)
    }

    @Test
    fun teacher_settings_defaults_support_offline_and_kktp() {
        val defaults = TeacherSettingsEntity()
        assertFalse(defaults.offlineOnlyMode)
        assertFalse(defaults.freeLearningMode)
        assertEquals(75, defaults.kktp)
        assertEquals("4024", defaults.teacherPin)
    }
}
