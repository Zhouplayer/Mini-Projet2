package com.example.mini_projet2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.mini_projet2.data.DataSource
import com.example.mini_projet2.databinding.ActivityMainBinding
import com.example.mini_projet2.model.Info

class MainActivity : AppCompatActivity(){

    private lateinit var bindingMain: ActivityMainBinding
    private var monData = DataSource()
    private val imageArray = arrayOf(
        R.drawable.banane, R.drawable.cgodin,
        R.drawable.charbon,R.drawable.diamant,R.drawable.emeraude,R.drawable.img7,
        R.drawable.piece,R.drawable.rubis,R.drawable.sacargent,R.drawable.tresor)

    private var imageUn = 0
    private var imageDeux = 0
    private var imageTrois = 0
    private var actifs = 0
    private var prixChoisie = 0
    private var prixGagne = 0
    private var booCocheCasse = false
    private var booGagneOuPas = false
    private var strCodeSecret = ""
    private var nbCou = 0
    private val CLE_Actifs = "Actifs"
    private val CLE_ImageUn = "ImageUn"
    private val CLE_ImageDeux = "ImageDeux"
    private val CLE_ImageTrois = "ImageTrois"
    private val CLE_PrixChoisie = "PrixChoisie"
    private val CLE_ChoixCasse = "Casse"
    private val CLE_CodeSecretSaisie = "CodeSecretSaisie"

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        //Activer toolbar
        setSupportActionBar(bindingMain.toolbar)

        //Verifie actif des le depart
        actif()

        bindingMain.btnJouer?.setOnClickListener{

            // Random les images
            imageUn = (0 .. (imageArray.size-1)).random()
            imageDeux = (0 .. (imageArray.size-1)).random()
            imageTrois = (0 .. (imageArray.size-1)).random()
            bindingMain.imageViewUn?.setImageResource(imageArray[imageUn])
            bindingMain.imageViewDeux?.setImageResource(imageArray[imageDeux])
            bindingMain.imageViewTrois?.setImageResource(imageArray[imageTrois])

            //Montant choisie
            when
            {
                bindingMain.radioButtonUn?.isChecked == true -> prixChoisie = 1
                bindingMain.radioButtonDeux?.isChecked == true -> prixChoisie = 2
                bindingMain.radioButtonTrois?.isChecked == true -> prixChoisie = 5
            }

            //Verifie si joueur gagne ou perte
            if (bindingMain.checkBoxCaisseSous?.isChecked == true)
            {
                when
                {
                    imageUn == 1 && imageDeux == 1 && imageTrois == 1 ->
                    {
                        prixGagne = prixChoisie * 100
                        Toast.makeText(this, "Joueur gangne " + prixGagne + "$", Toast.LENGTH_SHORT).show()
                    }
                    imageUn == 1 && imageDeux == 1 || imageUn == 1 && imageTrois == 1 || imageDeux == 1 && imageTrois == 1 ->
                    {
                        prixGagne = prixChoisie * 10
                        Toast.makeText(this, "Joueur gangne " + prixGagne + "$", Toast.LENGTH_SHORT).show()
                    }
                    else ->
                    {
                        prixGagne = -prixChoisie
                    }
                }

            }
            else
            {
                when
                {
                    imageUn == imageDeux && imageDeux == imageTrois ->
                    {
                        prixGagne = prixChoisie * 25
                        Toast.makeText(this, "Joueur gangne " + prixGagne + "$", Toast.LENGTH_SHORT).show()
                    }
                    imageUn == imageDeux || imageUn == imageTrois || imageDeux == imageTrois ->
                    {
                        prixGagne = prixChoisie * 1
                        Toast.makeText(this, "Joueur gangne " + prixGagne + "$", Toast.LENGTH_SHORT).show()
                    }
                    else ->
                    {
                        prixGagne = -prixChoisie
                    }
                }
            }

            actifs += prixGagne
            bindingMain.tvActif?.setText(getString(R.string.tvActif) + " " + actifs + "$")

            actif()

            //Garde les infos
            booGagneOuPas = prixGagne > 0
            nbCou += 1
            monData.addInfo(nbCou, imageArray[imageUn], imageArray[imageDeux], imageArray[imageTrois], prixGagne, booGagneOuPas, booCocheCasse, prixChoisie, actifs)
        }

        // Voir si le mot de passe est correct
        bindingMain.editTextTextCodeSecret?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                when (p0.toString())
                {
                    "passe" -> {
                        actifs += 100;
                        bindingMain.tvActif?.setText(getString(R.string.tvActif) + " " + actifs + "$");
                        bindingMain.editTextTextCodeSecret?.setText("")
                    }
                }

                actif()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    override fun onResume() {
        super.onResume()

        bindingMain.tvActif?.setText(getString(R.string.tvActif) + " " + actifs + "$")
        bindingMain.imageViewUn?.setImageResource(imageArray[imageUn])
        bindingMain.imageViewDeux?.setImageResource(imageArray[imageDeux])
        bindingMain.imageViewTrois?.setImageResource(imageArray[imageTrois])
        when (prixChoisie)
        {
            1 -> bindingMain.radioButtonUn?.isChecked = true
            2 -> bindingMain.radioButtonDeux?.isChecked = true
            5 -> bindingMain.radioButtonTrois?.isChecked = true
        }
        when(booCocheCasse)
        {
            true -> bindingMain.checkBoxCaisseSous?.isChecked = true
        }
        bindingMain.editTextTextCodeSecret?.setText(strCodeSecret)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //Enregistre des valeurs
        outState.putInt(CLE_Actifs, actifs)
        outState.putInt(CLE_ImageUn, imageUn)
        outState.putInt(CLE_ImageDeux, imageDeux)
        outState.putInt(CLE_ImageTrois, imageTrois)
        outState.putInt(CLE_PrixChoisie, prixChoisie)

        if (bindingMain.checkBoxCaisseSous?.isChecked == true)
        {
            outState.putBoolean(CLE_ChoixCasse, true)
        }
        else
        {
            outState.putBoolean(CLE_ChoixCasse, false)
        }

        outState.putString(CLE_CodeSecretSaisie, bindingMain.editTextTextCodeSecret.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        actifs = savedInstanceState.getInt(CLE_Actifs)
        imageUn = savedInstanceState.getInt(CLE_ImageUn)
        imageDeux = savedInstanceState.getInt(CLE_ImageDeux)
        imageTrois = savedInstanceState.getInt(CLE_ImageTrois)
        prixChoisie = savedInstanceState.getInt(CLE_PrixChoisie)
        booCocheCasse = savedInstanceState.getBoolean(CLE_ChoixCasse)
        strCodeSecret = savedInstanceState.getString(CLE_CodeSecretSaisie).toString()
    }

    //Si actif change, verifie
    fun actif()
    {
        when
        {
            actifs < 1 -> {
                bindingMain.radioButtonUn?.isEnabled=false;
                bindingMain.radioButtonDeux?.isEnabled=false;
                bindingMain.radioButtonTrois?.isEnabled=false;
                bindingMain.btnJouer?.isEnabled=false;
            }
            actifs < 2 -> {
                bindingMain.radioButtonDeux?.isEnabled=false;
                bindingMain.radioButtonTrois?.isEnabled=false;
            }
            actifs < 5 -> {
                bindingMain.radioButtonTrois?.isEnabled=false
            }
            else -> {
                bindingMain.radioButtonUn?.isEnabled=true;
                bindingMain.radioButtonDeux?.isEnabled=true;
                bindingMain.radioButtonTrois?.isEnabled=true;
                bindingMain.btnJouer?.isEnabled=true;
                when (prixChoisie)
                {
                    0 -> bindingMain.radioButtonUn.isChecked = true
                }
            }
        }
    }

    //Creer les menuItems
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_Item_Rafraichir ->
            {
                DataSource().reInitialisation()
                Toast.makeText(this, "Les statistique sont effacer", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_Item_Static ->
            {
                when(nbCou)
                {
                    0 -> Toast.makeText(this, "Aucune statistique disponible a l'heure actuelle", Toast.LENGTH_SHORT).show()
                    else ->{
                        var intent = Intent(this@MainActivity, StatsActivity::class.java)
                        //intent.putExtra("mutList", monData.getList())
                        startActivity(intent)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}