package br.com.cursoideal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import br.com.cursoideal.R
import br.com.cursoideal.databinding.CourseItemBinding
import br.com.cursoideal.transferobject.TOCourse

class CoursesAdapter(
    var onItemClick: (toCourse: TOCourse) -> Unit = { },
    var onRemoveItemClick: (id: String, position: Int) -> Boolean = { _: String, _: Int -> false },
    courses: List<TOCourse> = emptyList()
) : RecyclerView.Adapter<CoursesAdapter.ViewHolder>() {

    private val courses = courses.toMutableList()

    class ViewHolder(
        private val binding: CourseItemBinding,
        private val onItemClick: (toCourse: TOCourse) -> Unit,
        private val onRemoveItemClick: (id: String, position: Int) -> Boolean
    ) : RecyclerView.ViewHolder(binding.root) {

        private val menu: PopupMenu? = null
            get() {
                return if (field != null) {
                    field
                } else {
                    val popupMenu = PopupMenu(itemView.context, binding.courseItemButtonMenu)
                    popupMenu.inflate(R.menu.context_menu_course_item)
                    popupMenu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.context_menu_course_item_delete -> {
                                binding.toCourse?.id?.let { id ->
                                    onRemoveItemClick(id, adapterPosition)
                                }
                            }
                        }
                        false
                    }

                    popupMenu
                }
            }

        init {
            itemView.setOnClickListener {
                binding.toCourse?.let(onItemClick)
            }
        }

        fun bind(toCourse: TOCourse) {
            binding.toCourse = toCourse
            binding.executePendingBindings()
            binding.courseItemButtonMenu.setOnClickListener { menu?.show() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClick, onRemoveItemClick)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int = courses.size

    fun insert(toCourses: List<TOCourse>) {
        this.notifyItemRangeRemoved(0, this.courses.size)
        this.courses.clear()
        this.courses.addAll(toCourses)
        this.notifyItemInserted(this.courses.size)
    }

    fun insert(toCourse: TOCourse) {
        this.notifyItemInserted(this.courses.size)
        this.courses.add(toCourse)
    }

    fun update(toCourse: TOCourse) {
        this.notifyItemChanged(this.courses.indexOf(toCourse))
        this.courses[this.courses.indexOf(toCourse)] = toCourse
    }

    fun delete(position: Int) {
        this.notifyItemRemoved(position)
        this.courses.removeAt(position)
    }
}