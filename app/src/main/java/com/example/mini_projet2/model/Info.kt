package com.example.mini_projet2.model

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes

data class Info(
    @IntegerRes val indexNum:Int,
    @DrawableRes val imgUn:Int,
    @DrawableRes val imgDeux:Int,
    @DrawableRes val imgTrois:Int,
    @IntegerRes val montantGagne:Int,
    val GagneOuPas:Boolean,
    val modeChoix:Boolean,
    @IntegerRes val montantChoix:Int,
    @IntegerRes val actifNouveau:Int
)