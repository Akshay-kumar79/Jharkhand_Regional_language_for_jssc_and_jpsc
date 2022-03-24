package ashutosh.jharkhand.regional.screens

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ashutosh.jharkhand.regional.R
import ashutosh.jharkhand.regional.databinding.FragmentQuizBinding
import ashutosh.jharkhand.regional.databinding.FragmentScoreBinding
import ashutosh.jharkhand.regional.viewModels.QuizViewModel

class ScoreFragmentDialog : DialogFragment() {

    private lateinit var binding: FragmentScoreBinding
    private lateinit var quizViewModel: QuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScoreBinding.inflate(inflater, container, false)
        quizViewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.quiz_nav_graph))[QuizViewModel::class.java]

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes?.windowAnimations = R.style.CustomDialogAnimation

        val totalQuesText = "Total Question : " + quizViewModel.currentQuestions.size.toString()
        val rightAnsText = "right answer : " + quizViewModel.currentScore.toString()
        val wrongAnsText = "wrong answer : " + (quizViewModel.currentQuestions.size - quizViewModel.currentScore).toString()

        binding.totalQues.text = totalQuesText
        binding.rightAns.text = rightAnsText
        binding.wrongAns.text = wrongAnsText

        binding.okTextView.setOnClickListener {
            quizViewModel.setQuizDone()
            dismiss()
        }

        binding.restartTextView.setOnClickListener {
            quizViewModel.restartQuiz()
            dismiss()
        }

        return binding.root
    }

}