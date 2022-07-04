package com.example.keetab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

lateinit var toolbar:Toolbar
lateinit var drawerLayout:DrawerLayout
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar=findViewById(R.id.toolbar)
        drawerLayout=findViewById(R.id.drawerlayout)
        setmytoolbar()
        val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity, drawerLayout,R.string.drawer_open,R.string.drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
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