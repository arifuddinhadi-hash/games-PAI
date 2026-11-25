package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.AdventureMapScreen
import com.example.ui.screens.BerandaScreen
import com.example.ui.screens.DiagnosticAssessmentScreen
import com.example.ui.screens.FinalChallengeScreen
import com.example.ui.screens.PosDetailScreen
import com.example.ui.screens.ReflectionScreen
import com.example.ui.screens.RemedialPengayaanScreen
import com.example.ui.screens.ResultsAndCertificateScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.SummativeAssessmentScreen
import com.example.ui.screens.TeacherDashboardScreen
import com.example.ui.theme.PetualanganBalighTheme
import com.example.viewmodel.BalighViewModel
import com.example.viewmodel.TeacherDashboardViewModel

object AppRoutes {
    const val SPLASH = "splash"
    const val BERANDA = "beranda"
    const val MAP = "adventure_map"
    const val DIAGNOSTIC = "diagnostic"
    const val POS = "pos/{posNumber}"
    const val FINAL_CHALLENGE = "final_challenge"
    const val SUMMATIVE = "summative"
    const val RESULTS = "results_certificate"
    const val REFLECTION = "reflection"
    const val REMEDIAL_ENRICHMENT = "remedial_enrichment"
    const val TEACHER_DASHBOARD = "teacher_dashboard"

    fun posRoute(posNumber: Int) = "pos/$posNumber"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val balighViewModel: BalighViewModel = viewModel()
            val textScale by balighViewModel.textScale.collectAsStateWithLifecycle()

            PetualanganBalighTheme(textScale = textScale) {
                PetualanganBalighNavApp(balighViewModel = balighViewModel)
            }
        }
    }
}

@Composable
fun PetualanganBalighNavApp(balighViewModel: BalighViewModel) {
    val navController = rememberNavController()
    val isMuted by balighViewModel.isMuted.collectAsStateWithLifecycle()
    val settings by balighViewModel.settings.collectAsStateWithLifecycle()
    val activeStudent by balighViewModel.activeStudent.collectAsStateWithLifecycle()
    val allStudents by balighViewModel.allStudents.collectAsStateWithLifecycle()
    val formativeResults by balighViewModel.activeFormativeResults.collectAsStateWithLifecycle()
    val latestSummative by balighViewModel.activeLatestSummative.collectAsStateWithLifecycle()
    val latestReflection by balighViewModel.activeLatestReflection.collectAsStateWithLifecycle()

    val teacherViewModel: TeacherDashboardViewModel = viewModel(
        factory = remember { TeacherDashboardViewModel.Factory(balighViewModel.repository) }
    )

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH
    ) {
        composable(AppRoutes.SPLASH) {
            SplashScreen(
                onFinish = {
                    navController.navigate(AppRoutes.BERANDA) {
                        popUpTo(AppRoutes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.BERANDA) {
            BerandaScreen(
                existingStudents = allStudents,
                activeStudent = activeStudent,
                isMuted = isMuted,
                onToggleMute = { balighViewModel.toggleMute() },
                onCycleTextSize = { balighViewModel.cycleTextScale() },
                onStartNewStudent = { nickname, cls, grp, avatarId ->
                    balighViewModel.startStudentSession(nickname, cls, grp, avatarId) {
                        navController.navigate(AppRoutes.MAP)
                    }
                },
                onResumeStudent = { student ->
                    balighViewModel.switchStudent(student.studentId) {
                        navController.navigate(AppRoutes.MAP)
                    }
                },
                onOpenTeacherDashboard = {
                    navController.navigate(AppRoutes.TEACHER_DASHBOARD)
                }
            )
        }

        composable(AppRoutes.MAP) {
            val student = activeStudent
            if (student == null) {
                LaunchedEffect(Unit) { navController.popBackStack(AppRoutes.BERANDA, false) }
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            } else {
                AdventureMapScreen(
                    student = student,
                    settings = settings,
                    isMuted = isMuted,
                    onToggleMute = { balighViewModel.toggleMute() },
                    onCycleTextSize = { balighViewModel.cycleTextScale() },
                    onBackToHome = { navController.popBackStack(AppRoutes.BERANDA, false) },
                    onOpenDiagnostic = { navController.navigate(AppRoutes.DIAGNOSTIC) },
                    onOpenPos = { posNum -> navController.navigate(AppRoutes.posRoute(posNum)) },
                    onOpenFinalChallenge = { navController.navigate(AppRoutes.FINAL_CHALLENGE) },
                    onOpenSummative = { navController.navigate(AppRoutes.SUMMATIVE) },
                    onOpenResultsAndCertificate = { navController.navigate(AppRoutes.RESULTS) },
                    onOpenReflection = { navController.navigate(AppRoutes.REFLECTION) },
                    onOpenRemedialEnrichment = { navController.navigate(AppRoutes.REMEDIAL_ENRICHMENT) }
                )
            }
        }

        composable(AppRoutes.DIAGNOSTIC) {
            val student = activeStudent ?: return@composable
            DiagnosticAssessmentScreen(
                student = student,
                isMuted = isMuted,
                onToggleMute = { balighViewModel.toggleMute() },
                onCycleTextSize = { balighViewModel.cycleTextScale() },
                onPlayCorrect = { balighViewModel.soundManager.playCorrect(isMuted) },
                onPlayWrong = { balighViewModel.soundManager.playWrongGentle(isMuted) },
                onSubmitDiagnostic = { answers, onDone ->
                    balighViewModel.submitDiagnostic(answers, onDone)
                },
                onBackToMap = { navController.popBackStack() }
            )
        }

        composable(
            route = AppRoutes.POS,
            arguments = listOf(navArgument("posNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val posNum = backStackEntry.arguments?.getInt("posNumber") ?: 1
            val student = activeStudent ?: return@composable
            PosDetailScreen(
                posNumber = posNum,
                student = student,
                isMuted = isMuted,
                onToggleMute = { balighViewModel.toggleMute() },
                onCycleTextSize = { balighViewModel.cycleTextScale() },
                onPlayCorrect = { balighViewModel.soundManager.playCorrect(isMuted) },
                onPlayWrong = { balighViewModel.soundManager.playWrongGentle(isMuted) },
                onAwardGameXp = { xp, activity -> balighViewModel.awardGameXp(posNum, xp, activity) },
                onSubmitFormative = { answers, onResult ->
                    balighViewModel.submitFormative(posNum, answers, onResult)
                },
                onBackToMap = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.FINAL_CHALLENGE) {
            val student = activeStudent ?: return@composable
            FinalChallengeScreen(
                student = student,
                isMuted = isMuted,
                onToggleMute = { balighViewModel.toggleMute() },
                onCycleTextSize = { balighViewModel.cycleTextScale() },
                onPlayCorrect = { balighViewModel.soundManager.playCorrect(isMuted) },
                onPlayWrong = { balighViewModel.soundManager.playWrongGentle(isMuted) },
                onFinishChallenge = { bonusXp, onDone ->
                    balighViewModel.submitFinalChallenge(bonusXp, onDone)
                },
                onBackToMap = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.SUMMATIVE) {
            val student = activeStudent ?: return@composable
            SummativeAssessmentScreen(
                student = student,
                settings = settings,
                isMuted = isMuted,
                onToggleMute = { balighViewModel.toggleMute() },
                onCycleTextSize = { balighViewModel.cycleTextScale() },
                onPlayCorrect = { balighViewModel.soundManager.playCorrect(isMuted) },
                onPlayWrong = { balighViewModel.soundManager.playWrongGentle(isMuted) },
                onSubmitSummative = { answers, onResult ->
                    balighViewModel.submitSummative(answers, onResult)
                },
                onOpenCertificate = {
                    navController.navigate(AppRoutes.RESULTS) {
                        popUpTo(AppRoutes.MAP)
                    }
                },
                onOpenRemedial = {
                    navController.navigate(AppRoutes.REMEDIAL_ENRICHMENT) {
                        popUpTo(AppRoutes.MAP)
                    }
                },
                onBackToMap = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.RESULTS) {
            val student = activeStudent ?: return@composable
            ResultsAndCertificateScreen(
                student = student,
                formativeResults = formativeResults,
                latestSummative = latestSummative,
                settings = settings,
                isMuted = isMuted,
                onToggleMute = { balighViewModel.toggleMute() },
                onCycleTextSize = { balighViewModel.cycleTextScale() },
                onBackToMap = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.REFLECTION) {
            val student = activeStudent ?: return@composable
            ReflectionScreen(
                student = student,
                latestReflection = latestReflection,
                isMuted = isMuted,
                onToggleMute = { balighViewModel.toggleMute() },
                onCycleTextSize = { balighViewModel.cycleTextScale() },
                onSaveReflection = { mood, summary, onDone ->
                    balighViewModel.submitReflection(mood, summary, onDone)
                },
                onBackToMap = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.REMEDIAL_ENRICHMENT) {
            val student = activeStudent ?: return@composable
            RemedialPengayaanScreen(
                student = student,
                isMuted = isMuted,
                onToggleMute = { balighViewModel.toggleMute() },
                onCycleTextSize = { balighViewModel.cycleTextScale() },
                onPlayCorrect = { balighViewModel.soundManager.playCorrect(isMuted) },
                onPlayWrong = { balighViewModel.soundManager.playWrongGentle(isMuted) },
                onAwardXp = { xp, name -> balighViewModel.awardGameXp(0, xp, name) },
                onBackToMap = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.TEACHER_DASHBOARD) {
            TeacherDashboardScreen(
                viewModel = teacherViewModel,
                isMuted = isMuted,
                onToggleMute = { balighViewModel.toggleMute() },
                onCycleTextSize = { balighViewModel.cycleTextScale() },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
