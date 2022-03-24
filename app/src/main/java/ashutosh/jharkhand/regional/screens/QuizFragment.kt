package ashutosh.jharkhand.regional.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ashutosh.jharkhand.regional.R
import ashutosh.jharkhand.regional.databinding.FragmentQuizBinding
import ashutosh.jharkhand.regional.viewModels.QuizViewModel
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class QuizFragment : Fragment() {

    private val args: QuizFragmentArgs by navArgs()
    private lateinit var binding: FragmentQuizBinding
    private lateinit var quizViewModel: QuizViewModel
    private var mInterstitialAd: InterstitialAd? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        quizViewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.quiz_nav_graph))[QuizViewModel::class.java]

        quizViewModel.getQuestionsFromFirebase(args.category.id, args.topic.id, args.set.id)

        binding.quizViewModel = quizViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        readyInterstitialAd()
        setObservers()

        return binding.root
    }

    private fun setObservers() {

        quizViewModel.showScore.observe(viewLifecycleOwner) {
            if (it) {
                showInterstitialAd()

                val dialog = ScoreFragmentDialog()
                dialog.show(requireActivity().supportFragmentManager, dialog.tag)
                quizViewModel.showScoreDone()
            }
        }

        quizViewModel.isQuizDone.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            }
        }
    }

    private fun readyInterstitialAd(){
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            requireContext(),
            getString(R.string.interstitial_ad_unit_id),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd

                    mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            Log.d("TAG", "Ad was dismissed.")
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                            Log.d("TAG", "Ad failed to show.")
                        }

                        override fun onAdShowedFullScreenContent() {
                            Log.d("TAG", "Ad showed fullscreen content.")
                            mInterstitialAd = null
                        }
                    }
                }
            }
        )

    }

    private fun showInterstitialAd(){

        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

}