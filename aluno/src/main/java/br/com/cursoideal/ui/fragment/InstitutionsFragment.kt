package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.cursoideal.R
import br.com.cursoideal.databinding.FragmentInstitutionsBinding
import br.com.cursoideal.ui.adapter.InstitutionsAdapter
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment
import br.com.cursoideal.ui.viewmodel.InstitutionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstitutionsFragment : AbstractSessionedFragment() {

    private var _binding: FragmentInstitutionsBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { InstitutionsAdapter() }

    private val institutionViewModel: InstitutionViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInstitutionsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureMenu()

        binding.institutionsFragmentRecyclerview.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val itemId = adapter.getItemIdBy(position)

                adapter.delete(position)
                itemId?.let(institutionViewModel::delete)
            }

        }).attachToRecyclerView(binding.institutionsFragmentRecyclerview)

        adapter.onItemClick = { toInstitution ->
            navController.navigate(InstitutionsFragmentDirections.actionInstitutionsFragmentToMaintenanceCourceFragment(toInstitution.id))
        }

        institutionViewModel.findAll().observe(viewLifecycleOwner) { response -> response.data?.let(adapter::insert) }

    }

    private fun configureMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_lista_cursos, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_item_add_institution -> onAddCourse()
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onAddCourse(): Boolean {
        navController.navigate(InstitutionsFragmentDirections.actionInstitutionsFragmentToMaintenanceCourceFragment())
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}