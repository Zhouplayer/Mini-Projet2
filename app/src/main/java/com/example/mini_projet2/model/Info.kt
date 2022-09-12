package com.example.mini_projet2.model

import androidx.annotation.IntegerRes

data class Info(
    @IntegerRes val imgUn:Int,
    @IntegerRes val imgDeux:Int,
    @IntegerRes val imgTrois:Int,
    @IntegerRes val montantGagne:Int,
    val GagneOuPas:Boolean,
    val modeChoix:Boolean,
    @IntegerRes val montantChoix:Int,
    @IntegerRes val actifNouveau:Int
)