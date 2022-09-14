package com.example.mini_projet2

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mini_projet2.databinding.ActivityStatsBinding
import com.example.mini_projet2.adapter.ItemAdapter
import com.example.mini_projet2.data.DataSource
import com.example.mini_projet2.model.Info

class StatsActivity : AppCompatActivity() {

    private lateinit var bindingStat: ActivityStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingStat = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(bindingStat.root)

        setSupportActionBar(bindingStat.toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_white_arrow_back_24)
        //supportActionBar?.setDisplayShowTitleEnabled(false)

        val myList = DataSource.getList()
        val ryView = bindingStat.rvInfos
        ryView.adapter = ItemAdapter(this, myList)
    }
}