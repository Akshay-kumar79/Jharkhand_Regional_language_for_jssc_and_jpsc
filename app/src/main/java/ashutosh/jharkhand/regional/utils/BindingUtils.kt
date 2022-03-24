package ashutosh.jharkhand.regional.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.jharkhand.regional.adapter.CategoryAdapter
import ashutosh.jharkhand.regional.adapter.SetsAdapter
import ashutosh.jharkhand.regional.adapter.TopicAdapter
import ashutosh.jharkhand.regional.models.Category
import ashutosh.jharkhand.regional.models.Set
import ashutosh.jharkhand.regional.models.Topic


/**
 * Main Fragment
 */
@BindingAdapter("listCategories")
fun listCategories(recyclerView: RecyclerView, categories: List<Category>?){
    if (categories != null){
        val adapter = recyclerView.adapter as CategoryAdapter
        adapter.setData(categories)
    }
}

/**
 *  Topics Fragment
 */
@BindingAdapter("listTopics")
fun listTopics(recyclerView: RecyclerView, topics: List<Topic>?){
    if (topics != null){
        val adapter = recyclerView.adapter as TopicAdapter
        adapter.setData(topics)
    }
}

/**
 *  Sets Fragment
 */
@BindingAdapter("listSets")
fun listSets(recyclerView: RecyclerView, sets: List<Set>?){
    if (sets != null){
        val adapter = recyclerView.adapter as SetsAdapter
        adapter.setData(sets)
    }
}