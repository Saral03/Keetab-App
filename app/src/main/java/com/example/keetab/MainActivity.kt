package com.example.keetab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

lateinit var toolbar:Toolbar
lateinit var drawerLayout:DrawerLayout
lateinit var navigation:NavigationView
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar=findViewById(R.id.toolbar)
        drawerLayout=findViewById(R.id.drawerlayout)
        navigation=findViewById(R.id.navigation)
        setmytoolbar()
        val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity, drawerLayout,R.string.drawer_open,R.string.drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigation.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.dashboard->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame,DashboardFragment())
                        .commit()
                        drawerLayout.closeDrawers()
                }
                R.id.fav->{
                    Toast.makeText(this@MainActivity, "Favourite is clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.profile->{
                    Toast.makeText(this@MainActivity, "Profile is clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.about->{
                    Toast.makeText(this@MainActivity, "About is clicked", Toast.LENGTH_SHORT).show()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }
    fun setmytoolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="Keetab"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       val id=item.itemId
        if (id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)

    }
}