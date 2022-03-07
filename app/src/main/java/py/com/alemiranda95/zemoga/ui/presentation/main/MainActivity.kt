package py.com.alemiranda95.zemoga.ui.presentation.main

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import py.com.alemiranda95.zemoga.R
import py.com.alemiranda95.zemoga.databinding.ActivityMainBinding
import py.com.alemiranda95.zemoga.ui.interfaces.IBasePostsFragment
import py.com.alemiranda95.zemoga.ui.presentation.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navController: NavController

    override fun setViews() {
        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_posts, R.id.navigation_favorites
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun setListeners() {
        binding.fabDeleteAll.setOnClickListener {
            if (currentFragment is IBasePostsFragment) {
                (currentFragment as IBasePostsFragment).deleteAll()
            }
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.navigation_posts)
                binding.fabDeleteAll.text = getString(R.string.delete_all_label)

            if (destination.id == R.id.navigation_favorites)
                binding.fabDeleteAll.text = getString(R.string.delete_favorites_label)
        }
    }

    override fun setObservers() {
        //Nothing to set
    }

}