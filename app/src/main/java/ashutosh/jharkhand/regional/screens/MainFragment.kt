package ashutosh.jharkhand.regional.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ashutosh.jharkhand.regional.R
import ashutosh.jharkhand.regional.models.Category
import ashutosh.jharkhand.regional.adapter.CategoryAdapter
import ashutosh.jharkhand.regional.adapter.CategoryClickListener
import ashutosh.jharkhand.regional.databinding.FragmentMainBinding
import ashutosh.jharkhand.regional.viewModels.MainViewModel


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(findNavController().getViewModelStoreOwner(R.id.nav_graph_xml))[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.categoryRecyclerView.setHasFixedSize(true)

        val categoryAdapter = CategoryAdapter(CategoryClickListener { category ->
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToTopicFragment(category))
        })
        binding.categoryRecyclerView.adapter = categoryAdapter
    }

}