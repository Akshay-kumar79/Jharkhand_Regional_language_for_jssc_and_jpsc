package ashutosh.jharkhand.regional.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ashutosh.jharkhand.regional.R
import ashutosh.jharkhand.regional.adapter.SetClickListener
import ashutosh.jharkhand.regional.adapter.SetsAdapter
import ashutosh.jharkhand.regional.databinding.FragmentSetsBinding
import ashutosh.jharkhand.regional.viewModels.MainViewModel

class SetsFragment : Fragment() {

    private val args: SetsFragmentArgs by navArgs()

    private lateinit var binding: FragmentSetsBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_graph_xml))[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.setRecyclerView.setHasFixedSize(true)

        val setsAdapter = SetsAdapter(
            SetClickListener { set ->
                findNavController().navigate(
                    SetsFragmentDirections.actionSetsFragmentToQuizFragment(
                        set,
                        args.topic,
                        args.category,
                    )
                )
            }
        )
        binding.setRecyclerView.adapter = setsAdapter

//        val emptyDataObserver = EmptyDataObserver(binding.setRecyclerView, binding.emptyTextView, binding.longClickText)
//        setsAdapter.registerAdapterDataObserver(emptyDataObserver)

        viewModel.updateSets(args.category.id, args.topic.id)
    }

}