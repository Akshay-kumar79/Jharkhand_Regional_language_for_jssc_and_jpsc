package ashutosh.jharkhand.regional.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.jharkhand.regional.databinding.ListItemTopicBinding
import ashutosh.jharkhand.regional.models.Topic
import ashutosh.jharkhand.regional.utils.decodeImage

class TopicAdapter(
    private val categoryImage: String,
    private val topicClickListener: TopicClickListener
) : RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    private var topics: List<Topic> = ArrayList()

    fun setData(data: List<Topic>) {
        topics = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(topics[position], categoryImage, topicClickListener)
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    class ViewHolder(private val binding: ListItemTopicBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemTopicBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(topic: Topic, categoryImage: String, topicClickListener: TopicClickListener) {
            binding.topic = topic
            binding.topicClickListener = topicClickListener
            binding.topicName.text = topic.topicName
            if (categoryImage.isNotEmpty()) {
                binding.topicImage.setImageBitmap(decodeImage(categoryImage))
            }
            binding.executePendingBindings()
        }
    }
}

class TopicClickListener(val clickListener: (topic: Topic) -> Unit) {
    fun onClick(topic: Topic) = clickListener(topic)
}
