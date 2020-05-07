package pwr.edu.mysensors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pwr.edu.mysensors.databinding.ListItemSensorBinding

data class SensorPretty(
    val name: String,
    val typeString: String,
    val vendor: String,
    val version: String
)

class SensorAdapter() : ListAdapter<SensorPretty, SensorAdapter.SensorViewHolder>(SensorAdapter) {

    class SensorViewHolder(itemContributionBinding: ListItemSensorBinding) :
        RecyclerView.ViewHolder(itemContributionBinding.root) {

        private var binding: ListItemSensorBinding = itemContributionBinding

        fun bind(sensor: SensorPretty) {
            binding.sensor = sensor
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object ContributiontDiffCallback : DiffUtil.ItemCallback<SensorPretty>() {
        override fun areItemsTheSame(oldItem: SensorPretty, newItem: SensorPretty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SensorPretty, newItem: SensorPretty): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        return SensorViewHolder(
            ListItemSensorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }
}