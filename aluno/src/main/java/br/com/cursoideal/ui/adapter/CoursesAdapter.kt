package br.com.cursoideal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.cursoideal.databinding.CourseItemBinding
import br.com.cursoideal.transferobject.TOCourse

class CoursesAdapter(
    var onItemClick: (toCourse: TOCourse) -> Unit = { },
    courses: List<TOCourse> = emptyList()
) : RecyclerView.Adapter<CoursesAdapter.ViewHolder>() {

    private val courses = courses.toMutableList()

    class ViewHolder(
        private val binding: CourseItemBinding,
        private val onItemClick: (toCourse: TOCourse) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                binding.toCourse?.let(onItemClick)
            }
        }

        fun bind(toCourse: TOCourse) {
            binding.toCourse = toCourse
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClick)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int = courses.size

    // Método apenas para teste, substituir posteriormente por um método que receba
    // Apenas uma instituição
    fun insert(courses: List<TOCourse>) {
        this.notifyItemRangeRemoved(0, this.courses.size)
        this.courses.clear()
        this.courses.addAll(courses)
        this.notifyItemInserted(this.courses.size)
    }
}