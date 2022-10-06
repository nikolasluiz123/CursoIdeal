package br.com.cursoideal.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.cursoideal.R
import br.com.cursoideal.databinding.ActivityMainBinding
import br.com.cursoideal.ui.viewmodel.AppStateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val controller by lazy { findNavController(R.id.nav_host_fragment_content_main) }

    private val appStateViewModel: AppStateViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(controller.graph)
        setupActionBarWithNavController(controller, appBarConfiguration)

        controller.addOnDestinationChangedListener { _, destination, _ ->

            appStateViewModel.components.observe(this) {
                it?.let {  viewControll ->
                    if(viewControll.showAppBar) { supportActionBar?.show() } else { supportActionBar?.hide() }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        controller.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}