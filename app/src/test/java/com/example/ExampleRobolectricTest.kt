package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.curriculum.AssessmentBankData
import com.example.data.curriculum.PosCurriculumData
import com.example.data.curriculum.TeacherModulAjarData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `app name matches Petualangan Baligh`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Petualangan Baligh", appName)
    }

    @Test
    fun `curriculum and assessment bank counts meet specification`() {
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
}
