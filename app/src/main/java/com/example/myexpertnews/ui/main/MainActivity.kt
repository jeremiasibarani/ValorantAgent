package com.example.myexpertnews.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myexpertnews.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment
        bottomNavView = findViewById(R.id.bottom_nav)
        val navController = navHostFragment.navController
        bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id){
                R.id.agentsFragment -> showBottomNav()
                R.id.favoriteAgentsFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.weaponAction -> {
                Toast.makeText(this, "ClickedWeaponAction", Toast.LENGTH_SHORT).show()
                navigateToWeaponActivity()
                return true
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun showBottomNav(){
        bottomNavView.visibility = View.VISIBLE
    }

    private fun hideBottomNav(){
        bottomNavView.visibility = View.GONE
    }

    private fun moveToWeaponActivity(){
        startActivity(Intent(this, Class.forName("com.example.weapons.ui.WeaponsActivity")))
    }

    private fun navigateToWeaponActivity(){
        try {
            installWeaponModule()
            Toast.makeText(this, "install weapon module passed", Toast.LENGTH_SHORT).show()
        }catch (ex : Exception){
            Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
            Log.e("MainActivity-TAG", "error", ex)
        }
    }

    private fun installWeaponModule(){
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val module = "weapons"
        if(splitInstallManager.installedModules.contains(module)){
            moveToWeaponActivity()
            Toast.makeText(this, "Open module", Toast.LENGTH_SHORT).show()
        }else{
            val request = SplitInstallRequest.newBuilder()
                .addModule(module)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success installing module", Toast.LENGTH_SHORT).show()
                    moveToWeaponActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error installing module", Toast.LENGTH_SHORT).show()
                }
        }
    }
}