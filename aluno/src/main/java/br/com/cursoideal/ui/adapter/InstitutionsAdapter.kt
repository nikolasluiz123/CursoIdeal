package br.com.cursoideal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.cursoideal.databinding.InstitutionItemBinding
import br.com.cursoideal.transferobject.TOInstitution

class InstitutionsAdapter(
    var onItemClick: (toInstitution: TOInstitution) -> Unit = { },
    institutions: List<TOInstitution> = emptyList()
) : RecyclerView.Adapter<InstitutionsAdapter.ViewHolder>() {

    private val institutions = institutions.toMutableList()

    class ViewHolder(
        private val binding: InstitutionItemBinding,
        private val onItemClick: (toInstitution: TOInstitution) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var toInstitution: TOInstitution

        init {
            itemView.setOnClickListener {
                if (::toInstitution.isInitialized) {
                    onItemClick(toInstitution)
                }
            }
        }

        fun bind(toInstitution: TOInstitution) {
            this.toInstitution = toInstitution

            binding.institutionItemName.text = toInstitution.name
            binding.institutionItemAddress.text = toInstitution.toAddress.getCompleteAddress()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(InstitutionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClick)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(institutions[position])
    }

    override fun getItemCount(): Int = institutions.size

    // Método apenas para teste, substituir posteriormente por um método que receba
    // Apenas uma instituição
    fun insert(institutions: List<TOInstitution>) {
        this.notifyItemRangeRemoved(0, this.institutions.size)
        this.institutions.clear()
        this.institutions.addAll(institutions)
        this.notifyItemInserted(this.institutions.size)
    }
}