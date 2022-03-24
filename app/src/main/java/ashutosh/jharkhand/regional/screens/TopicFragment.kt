package ashutosh.jharkhand.regional.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ashutosh.jharkhand.regional.R
import ashutosh.jharkhand.regional.adapter.TopicAdapter
import ashutosh.jharkhand.regional.adapter.TopicClickListener
import ashutosh.jharkhand.regional.databinding.FragmentTopicBinding
import ashutosh.jharkhand.regional.viewModels.MainViewModel
import ashutosh.jharkhand.regional.viewModels.TopicViewModel

class TopicFragment : Fragment() {

    private val args: TopicFragmentArgs by navArgs()

    private lateinit var binding: FragmentTopicBinding
    private lateinit var viewModel: TopicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopicBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[TopicViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.topicRecyclerView.setHasFixedSize(true)

        val topicAdapter = TopicAdapter(args.category.categoryImage,
            TopicClickListener { topic ->
                findNavController().navigate(TopicFragmentDirections.actionTopicFragmentToSetsFragment(topic, args.category, topic.topicName))
            }
        )
        binding.topicRecyclerView.adapter = topicAdapter

        viewModel.getTopicsFromFirebase(args.category.id)
    }

}