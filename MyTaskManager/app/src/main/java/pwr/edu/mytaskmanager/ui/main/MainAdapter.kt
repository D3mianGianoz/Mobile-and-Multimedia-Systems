package pwr.edu.mytaskmanager.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pwr.edu.mytaskmanager.Task
import pwr.edu.mytaskmanager.databinding.ListItemTaskBinding

class TasksAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Task, TasksAdapter.TasksViewHolder>(TasksAdapter) {

    class TasksViewHolder(itemContributionBinding: ListItemTaskBinding) :
        RecyclerView.ViewHolder(itemContributionBinding.root) {

        private var binding: ListItemTaskBinding = itemContributionBinding

        fun bind(
            task: Task,
            onClickListener: OnClickListener
        ) {
            binding.task = task
            binding.clickListener = onClickListener
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object ContributiontDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(
            ListItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = getItem(position)

//        // Lo lascio qui perchè senno non mi funziona il clicklistener
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(task)
//        }
        holder.bind(task, onClickListener)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items. Passes the [Project]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Project]
     */
    class OnClickListener(val clickListener: (task: Task) -> Unit) {
        fun onClick(task: Task) = clickListener(task)
    }
}