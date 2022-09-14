package com.example.mini_projet2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mini_projet2.databinding.ActivityStatsBinding
import com.example.mini_projet2.adapter.ItemAdapter
import com.example.mini_projet2.data.DataSource

class StatsActivity : AppCompatActivity() {

    private lateinit var bindingStat: ActivityStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingStat = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(bindingStat.root)

        //toolbar settings
        setSupportActionBar(bindingStat.toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_arrow_back_24)

        //demande recyclerView a utiliser des donnes
        val myList = DataSource.getList()
        val ryView = bindingStat.rvInfos
        ryView.adapter = ItemAdapter(this, myList)
    }
}