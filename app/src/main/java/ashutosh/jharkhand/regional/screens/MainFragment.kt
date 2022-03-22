package ashutosh.jharkhand.regional.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ashutosh.jharkhand.regional.models.Category
import ashutosh.jharkhand.regional.adapter.CategoryAdapter
import ashutosh.jharkhand.regional.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        val categories = ArrayList<Category>()
        categories.add(Category("id", "Maths", "asdd"))
        categories.add(Category("id", "English", "asdd"))
        categories.add(Category("id", "Science", "asdd"))
        categories.add(Category("id", "Sanskrit", "asdd"))
        categories.add(Category("id", "Hindi", "asdd"))
        categories.add(Category("id", "SST", "asdd"))
        categories.add(Category("id", "Physics", "asdd"))
        categories.add(Category("id", "Chemistry", "asdd"))
        categories.add(Category("id", "Biology", "asdd"))
        categories.add(Category("id", "Physical education", "asdd"))
        categories.add(Category("id", "Computer science", "asdd"))
        categories.add(Category("id", "Computer graphics", "asdd"))
        categories.add(Category("id", "Linux OS", "asdd"))


        binding.categoryRecyclerView.setHasFixedSize(true)

        val categoryAdapter = CategoryAdapter()
        binding.categoryRecyclerView.adapter = categoryAdapter
        categoryAdapter.setData(categories)

        return binding.root
    }

}