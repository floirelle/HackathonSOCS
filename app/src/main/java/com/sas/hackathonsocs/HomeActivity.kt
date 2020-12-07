package com.sas.hackathonsocs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sas.hackathonsocs.fragment.ContactFragment
import com.sas.hackathonsocs.fragment.HomeFragment
import com.sas.hackathonsocs.fragment.ListFragment
import com.sas.hackathonsocs.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeFragment = HomeFragment()
        val listFragment = ListFragment() //Menu 2
        val bookFragment = ContactFragment() //Menu 3
        val userFragment = ProfileFragment()

        setCurrentFragment(homeFragment, getResources().getString(R.string.home))
        nav_bottom.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home -> setCurrentFragment(homeFragment, this.getResources().getString(R.string.home))
                R.id.menu_list -> setCurrentFragment(listFragment, getResources().getString(R.string.list))
                R.id.menu_book -> setCurrentFragment(bookFragment, getResources().getString(R.string.contact))
                R.id.menu_profile -> setCurrentFragment(userFragment, "${getResources().getString(R.string.hello)} ${"Bang Oke"}")
            }
            true
        }
    }

    private fun setTitleBar(text: String){
        supportActionBar?.title = text
    }

    private fun setCurrentFragment(fragment: Fragment, text: String){
        setTitleBar(text)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_wrapper, fragment).commit()
        }
    }
}