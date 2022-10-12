package br.com.cursoideal.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import br.com.cursoideal.R
import br.com.cursoideal.databinding.FragmentInstitutionsBinding
import br.com.cursoideal.ui.fragment.base.AbstractSessionedFragment

class InstitutionsFragment : AbstractSessionedFragment() {

    private var _binding: FragmentInstitutionsBinding? = null
    private val binding get() = _binding!!

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
        navController.navigate(InstitutionsFragmentDirections.actionCoursesFragmentToMaintenanceCourceFragment())
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}