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
var PreviousMenuitem:MenuItem?=null
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
        openDashboard()
        navigation.setNavigationItemSelectedListener {
            if (PreviousMenuitem!=null){
                PreviousMenuitem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            PreviousMenuitem=it
            when(it.itemId){
                R.id.dashboard->{
                    openDashboard()
                        drawerLayout.closeDrawers()
                }
                R.id.fav->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame,FavFragment())
                        .addToBackStack("Favourites")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Favourites"
                }
                R.id.profile->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame,ProfileFragment())
                        .addToBackStack("Profile")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="Profile"
                }
                R.id.about->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame,AboutFragment())
                        .addToBackStack("About")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title="About"
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
    fun openDashboard(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame,DashboardFragment())
            .addToBackStack("Dashboard")
            .commit()
        supportActionBar?.title="Dashboard"
        navigation.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frame)
        when(frag){
            !is DashboardFragment->openDashboard()
            else ->super.onBackPressed()
        }

    }
}