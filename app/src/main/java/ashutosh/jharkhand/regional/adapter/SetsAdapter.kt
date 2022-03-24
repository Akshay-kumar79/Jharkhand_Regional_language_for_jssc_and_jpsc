package ashutosh.jharkhand.regional.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.jharkhand.regional.databinding.ListItemSetBinding
import ashutosh.jharkhand.regional.models.Set

class SetsAdapter(
    private val setClickListener: SetClickListener
) : RecyclerView.Adapter<SetsAdapter.ViewHolder>() {

    private var sets: List<Set> = ArrayList()

    fun setData(data: List<Set>) {
        sets = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sets[position], setClickListener)
    }

    override fun getItemCount(): Int {
        return sets.size
    }

    class ViewHolder(private val binding: ListItemSetBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemSetBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(set: Set, setClickListener: SetClickListener) {
            binding.set = set
            binding.setClickListener = setClickListener
            val setText = "Set-${set.number}"
            binding.topicName.text = setText

            binding.executePendingBindings()
        }
    }
}

class SetClickListener(val clickListener: (set: Set) -> Unit) {
    fun onClick(set: Set) = clickListener(set)
}
