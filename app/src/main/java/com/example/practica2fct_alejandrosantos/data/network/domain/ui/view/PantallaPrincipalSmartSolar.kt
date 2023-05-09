package com.example.practica2fct_alejandrosantos.data.network.domain.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.data.adapter.ViewPagerAdapter
import com.example.practica2fct_alejandrosantos.databinding.ActivityPantallaPrincipalSmartSolarBinding
import com.google.android.material.tabs.TabLayout

class PantallaPrincipalSmartSolar : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaPrincipalSmartSolarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPantallaPrincipalSmartSolarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pantallaSmartSolarToolbarTituloBoton.setOnClickListener(){
            val intent = Intent(this, PantallaInicio::class.java).apply {
            }
            startActivity(intent)
        }

        setupTabLayout()
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.pantallaSmartSolarViewPager.apply {
            adapter = ViewPagerAdapter(supportFragmentManager, binding.pantallaSmartSolarTablayout.tabCount)
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.pantallaSmartSolarTablayout))
        }
    }

    private fun setupTabLayout() {
        binding.pantallaSmartSolarTablayout.apply {
            addTab(this.newTab().setText(R.string.pantallaPrincipalSmartSolar_nombretab1))
            addTab(this.newTab().setText(R.string.pantallaPrincipalSmartSolar_nombretab2))
            addTab(this.newTab().setText(R.string.pantallaPrincipalSmartSolar_nombretab3))

            // tabGravity = TabLayout.GRAVITY_FILL

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let {
                        binding.pantallaSmartSolarViewPager.currentItem = it
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }

}