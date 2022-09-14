package com.example.mini_projet2.data

import com.example.mini_projet2.model.Info

object DataSource {

    private val mutablelistDonne = mutableListOf<Info>()

    fun addInfo(indexNm: Int, imgUn: Int, imgDeux:Int, imgTrois:Int, montantGagne:Int, GangneOuPas:Boolean, modeChoix:Boolean, montantChoix:Int, actifNouveau:Int){
        mutablelistDonne.add(Info(indexNm, imgUn, imgDeux, imgTrois, montantGagne, GangneOuPas, modeChoix, montantChoix, actifNouveau))
    }

    fun getList(): MutableList<Info> {
        return mutablelistDonne
    }

    fun reInitialisation(){
        mutablelistDonne.clear()
    }
}