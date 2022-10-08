package br.com.cursoideal.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.cursoideal.NavGraphDirections
import br.com.cursoideal.R
import br.com.cursoideal.databinding.ActivityMainBinding
import br.com.cursoideal.ui.viewmodel.AppStateViewModel
import br.com.cursoideal.ui.viewmodel.AuthenticationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val navController by lazy { findNavController(R.id.nav_host_fragment_content_main) }
    private val appStateViewModel: AppStateViewModel by viewModel()
    private val authenticationViewModel: AuthenticationViewModel by viewModel()

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this@MainActivity, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menu_item_user_profile -> onUserProfileSelected()
                R.id.menu_item_courses_evaluation -> onCoursesEvaluationSelected()
                R.id.menu_item_favorite_courses -> onFavoriteCoursesSelected()
                R.id.menu_item_cursos -> onCursosSelected()
                R.id.menu_item_cadastro_cursos -> onCadastroCursosSelected()
                R.id.menu_item_logout -> onLogoutSelected()
                R.id.menu_item_settings -> onSettingsSelected()
                else -> true
            }
        }

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, _, _ ->
            appStateViewModel.components.observe(this) {
                it?.let {  viewControll ->
                    if(viewControll.showAppBar) { supportActionBar?.show() } else { supportActionBar?.hide() }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private fun onUserProfileSelected(): Boolean {
        return true
    }

    private fun onCoursesEvaluationSelected(): Boolean {
        return true
    }

    private fun onFavoriteCoursesSelected(): Boolean {
        return true
    }

    private fun onCursosSelected(): Boolean {
        return true
    }

    private fun onCadastroCursosSelected(): Boolean {
        return true
    }

    private fun onLogoutSelected(): Boolean {
        binding.drawerLayout.close()

        Handler(Looper.getMainLooper()).postDelayed({
            authenticationViewModel.logout()
            navController.navigate(NavGraphDirections.actionGlobalLoginFragment())
        }, 200)

        return true
    }

    private fun onSettingsSelected(): Boolean {
        return true
    }


    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}