package br.com.cursoideal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.cursoideal.databinding.CompleteInfosCourseItemBinding
import br.com.cursoideal.transferobject.TOCourse
import br.com.cursoideal.transferobject.TOCourseComplete

class CoursesCompleteAdapter(
    var onItemClick: (toCourseComplete: TOCourseComplete) -> Unit = { },
    courses: List<TOCourseComplete> = emptyList()
) : RecyclerView.Adapter<CoursesCompleteAdapter.ViewHolder>() {

    private val courses = courses.toMutableList()

    class ViewHolder(
        private val binding: CompleteInfosCourseItemBinding,
        private val onItemClick: (toCourseComplete: TOCourseComplete) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                binding.toCourseComplete?.let(onItemClick)
            }
        }

        fun bind(toCourseComplete: TOCourseComplete) {
            binding.toCourseComplete = toCourseComplete
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CompleteInfosCourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClick)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int = courses.size

    fun insert(toCourseCompletes: List<TOCourseComplete>) {
        this.notifyItemRangeRemoved(0, this.courses.size)
        this.courses.clear()
        this.courses.addAll(toCourseCompletes)
        this.notifyItemInserted(this.courses.size)
    }

}