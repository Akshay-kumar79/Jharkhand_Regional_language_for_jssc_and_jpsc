package ashutosh.jharkhand.regional.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ashutosh.jharkhand.regional.R
import ashutosh.jharkhand.regional.databinding.FragmentQuizBinding
import ashutosh.jharkhand.regional.viewModels.QuizViewModel

class QuizFragment : Fragment() {

    private val args: QuizFragmentArgs by navArgs()
    private lateinit var binding: FragmentQuizBinding
    private lateinit var quizViewModel: QuizViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        quizViewModel = ViewModelProvider(this)[QuizViewModel::class.java]

        quizViewModel.getQuestionsFromFirebase(args.category.id, args.topic.id, args.set.id)

        binding.quizViewModel = quizViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

}