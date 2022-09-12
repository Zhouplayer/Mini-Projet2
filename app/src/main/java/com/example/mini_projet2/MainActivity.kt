package com.example.mini_projet2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.mini_projet2.databinding.ActivityMainBinding
import com.example.mini_projet2.model.Info

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val imageArray = arrayOf(R.drawable.banane,R.drawable.cgodin,
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
    private val CLE_Actifs = "Actifs"
    private val CLE_ImageUn = "ImageUn"
    private val CLE_ImageDeux = "ImageDeux"
    private val CLE_ImageTrois = "ImageTrois"
    private val CLE_PrixChoisie = "PrixChoisie"
    private val CLE_ChoixCasse = "Casse"
    private val CLE_CodeSecretSaisie = "CodeSecretSaisie"
    private var mutablelistDonne = mutableListOf<Info>()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Activer toolbar
        setSupportActionBar(binding.toolbar)

        //Verifie actif des le depart
        actif()

        binding.btnJouer?.setOnClickListener{

            // Random les images
            imageUn = (0 .. (imageArray.size-1)).random()
            imageDeux = (0 .. (imageArray.size-1)).random()
            imageTrois = (0 .. (imageArray.size-1)).random()
            binding.imageViewUn?.setImageResource(imageArray[imageUn])
            binding.imageViewDeux?.setImageResource(imageArray[imageDeux])
            binding.imageViewTrois?.setImageResource(imageArray[imageTrois])

            //Montant choisie
            when
            {
                binding.radioButtonUn?.isChecked == true -> prixChoisie = 1
                binding.radioButtonDeux?.isChecked == true -> prixChoisie = 2
                binding.radioButtonTrois?.isChecked == true -> prixChoisie = 5
            }

            //Verifie si joueur gagne ou perte
            if (binding.checkBoxCaisseSous?.isChecked == true)
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
            binding.tvActif?.setText(getString(R.string.tvActif) + " " + actifs + "$")

            actif()

            //Garde les infos
            booGagneOuPas = prixGagne > 0
            mutablelistDonne.add(Info(mutablelistDonne.size, imageArray[imageUn], imageArray[imageDeux], imageArray[imageTrois], prixGagne, booGagneOuPas, booCocheCasse, prixChoisie, actifs))
        }

        // Voir si le mot de passe est correct
        binding.editTextTextCodeSecret?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                when (p0.toString())
                {
                    "passe" -> {
                        actifs += 100;
                        binding.tvActif?.setText(getString(R.string.tvActif) + " " + actifs + "$");
                        binding.editTextTextCodeSecret?.setText("")
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

        binding.tvActif?.setText(getString(R.string.tvActif) + " " + actifs + "$")
        binding.imageViewUn?.setImageResource(imageArray[imageUn])
        binding.imageViewDeux?.setImageResource(imageArray[imageDeux])
        binding.imageViewTrois?.setImageResource(imageArray[imageTrois])
        when (prixChoisie)
        {
            1 -> binding.radioButtonUn?.isChecked = true
            2 -> binding.radioButtonDeux?.isChecked = true
            5 -> binding.radioButtonTrois?.isChecked = true
        }
        when(booCocheCasse)
        {
            true -> binding.checkBoxCaisseSous?.isChecked = true
        }
        binding.editTextTextCodeSecret?.setText(strCodeSecret)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //Enregistre des valeurs
        outState.putInt(CLE_Actifs, actifs)
        outState.putInt(CLE_ImageUn, imageUn)
        outState.putInt(CLE_ImageDeux, imageDeux)
        outState.putInt(CLE_ImageTrois, imageTrois)
        outState.putInt(CLE_PrixChoisie, prixChoisie)

        if (binding.checkBoxCaisseSous?.isChecked == true)
        {
            outState.putBoolean(CLE_ChoixCasse, true)
        }
        else
        {
            outState.putBoolean(CLE_ChoixCasse, false)
        }

        outState.putString(CLE_CodeSecretSaisie, binding.editTextTextCodeSecret.text.toString())
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
                binding.radioButtonUn?.isEnabled=false;
                binding.radioButtonDeux?.isEnabled=false;
                binding.radioButtonTrois?.isEnabled=false;
                binding.btnJouer?.isEnabled=false;
            }
            actifs < 2 -> {
                binding.radioButtonDeux?.isEnabled=false;
                binding.radioButtonTrois?.isEnabled=false;
            }
            actifs < 5 -> {
                binding.radioButtonTrois?.isEnabled=false
            }
            else -> {
                binding.radioButtonUn?.isEnabled=true;
                binding.radioButtonDeux?.isEnabled=true;
                binding.radioButtonTrois?.isEnabled=true;
                binding.btnJouer?.isEnabled=true;
                when (prixChoisie)
                {
                    0 -> binding.radioButtonUn.isChecked = true
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
                mutablelistDonne.clear()
                Toast.makeText(this, "Les statistique sont effacer", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_Item_Static ->
            {
                when(mutablelistDonne.size)
                {
                    0 -> Toast.makeText(this, "Aucune statistique disponible a l'heure actuelle", Toast.LENGTH_SHORT).show()
                    else ->{
                        startActivity(Intent(this@MainActivity, StatsActivity::class.java))
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}