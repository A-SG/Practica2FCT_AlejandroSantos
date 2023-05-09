package com.example.practica2fct_alejandrosantos.data.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.practica2fct_alejandrosantos.data.network.domain.ui.fragment.DetalleFragment
import com.example.practica2fct_alejandrosantos.data.network.domain.ui.fragment.EnergiaFragment
import com.example.practica2fct_alejandrosantos.data.network.domain.ui.fragment.InstalacionFrangment

class ViewPagerAdapter(fm: FragmentManager, var tabCount: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> InstalacionFrangment()
            1 -> EnergiaFragment()
            2 -> DetalleFragment()
            else -> InstalacionFrangment()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "Tab " + (position + 1)
    }
}
