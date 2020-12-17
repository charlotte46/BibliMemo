package com.example.biblimemo.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.biblimemo.R
import com.example.biblimemo.ui.bibliotheque.BibliothequeFragment
import com.example.biblimemoapp.Ouvrage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

/**
 * Affiche les informations détaillées d'un ouvrage et permet de les modifier en activant
 * la modification.
 */
class OuvrageDetailsActivity : AppCompatActivity() {

    companion object {
        /** Code pour l'intention de la sélection de l'image */
        const val REQ_CODE_GET_MINIATURE = 0
    }

    /** Ouvrage manipulé par l'activité */
    private lateinit var ouvrage: Ouvrage

    /** Informations du détail de l'ouvrage à saisir */
    private lateinit var saisieTitre: EditText
    private lateinit var saisieAuteur: EditText
    private lateinit var saisieTome: EditText
    private lateinit var saisieDescription: EditText

    /** Informations du détail de l'ouvrage à sélectionner (boites de dialogue) */
    private lateinit var saisieCollection: TextView
    private lateinit var listePartage: TextView
    private lateinit var saisieFormat: TextView
    private lateinit var saisieQuantite: TextView


    /** Ligne du numéro de tome non obligatoire */
    private lateinit var ligneTome: TableRow


    /** Miniature de l'ouvrage */
    private lateinit var miniatureOuvrage: ImageView


    /** Image sélectionnée pour la miniature */
    private var bitmap: Bitmap? = null


    /** Etat lu ou non de l'ouvrage */
    private lateinit var switchLecture: Switch
    private lateinit var etatLecture: TextView


    /** Etat de favoris de l'ouvrage */
    private lateinit var btnFavoris: FloatingActionButton


    /** Boutons flottants d'action sur l'ouvrage (edition, suppression, enregistrement) */
    private lateinit var btnEdition: FloatingActionButton
    private lateinit var btnEnregistrer: FloatingActionButton
    private lateinit var btnSupprimer: FloatingActionButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ouvrage_details)

        /* Personnaliser la barre d'action */
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        /* Récupérer l'ouvrage sélectionné et ses informations dans les views */
        ouvrage = getIntent().getParcelableExtra(BibliothequeFragment.SELECTED_OUVRAGE)!!


        /* Initialisation des différents champs */
        initiliserInformationsOuvrage()
    }


    /**
     * Initialise les différents champs de la vue avec les informations récupérées dans l'ouvrage.
     */
    private fun initiliserInformationsOuvrage() {

        /* Récupérer les Views avec les différents identifiants */
        saisieTitre         = findViewById(R.id.ouvrage_details_titre)
        saisieAuteur        = findViewById(R.id.ouvrage_details_auteur)
        saisieTome          = findViewById(R.id.ouvrage_details_tome)
        ligneTome           = findViewById(R.id.row_tome)
        saisieDescription   = findViewById(R.id.ouvrage_details_description)
        saisieCollection    = findViewById(R.id.ouvrage_details_collection)
        listePartage        = findViewById(R.id.ouvrage_details_liste_partage)
        miniatureOuvrage    = findViewById(R.id.ouvrage_details_miniature)
        switchLecture       = findViewById(R.id.switch_lecture)
        etatLecture         = findViewById(R.id.ouvrage_details_lecture)
        btnFavoris          = findViewById(R.id.ouvrage_details_favoris)
        saisieFormat        = findViewById(R.id.ouvrage_details_format)
        saisieQuantite      = findViewById(R.id.ouvrage_details_quantite)


        /* Récupérer les boutons flottants */
        btnEdition          = findViewById(R.id.ouvrage_details_btn_editer)
        btnEnregistrer      = findViewById(R.id.ouvrage_details_btn_enregistrer)
        btnSupprimer        = findViewById(R.id.ouvrage_details_btn_supprimer)


        /* Récupérer les données de l'ouvrage et les placer dans les champs */
        saisieTitre.setText(ouvrage.titre)
        saisieAuteur.setText(ouvrage.auteur)
        saisieDescription.setText(ouvrage.description)
        saisieDescription.isEnabled = false
        saisieCollection.setText(ouvrage.collection)
        saisieFormat.text = ouvrage.format
        saisieQuantite.text = ouvrage.quantite.toString()


        // Tome de l'ouvrage (non visible si non renseigné)
        if (ouvrage.numTome != 0) {
            saisieTome.setText(ouvrage.numTome.toString())
        } else {
            ligneTome.visibility = View.GONE
        }

        // Liste de partage formatées
        formaterListePartage()


        // Miniature de l'ouvrage, non clickable
        miniatureOuvrage.setImageResource(ouvrage.miniature)
        miniatureOuvrage.isEnabled = false


        // Statut favori ou non de l'ouvrage
        btnFavoris.setImageResource(when (ouvrage.favori) {
            false -> R.drawable.ic_favoris_vide
            true -> R.drawable.ic_favoris
        })
        btnFavoris.isClickable = false


        // Etat de lecture de l'ouvrage et listener de click sur le bouton de lecture
        etatLecture.setText(when (ouvrage.lu) {
            false -> R.string.toggle_non_lu
            true -> R.string.toggle_lu
        })

        switchLecture.setChecked(ouvrage.lu)
        switchLecture.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                etatLecture.setText(R.string.toggle_lu)
            } else {
                etatLecture.setText(R.string.toggle_non_lu)
            }
        }
    }


    /**
     * Active la fenêtre d'édition dans le détail de l'ouvrage.
     */
    fun activerEdition(view: View) {
        saisieTitre.isEnabled = true
        saisieAuteur.isEnabled = true
        saisieTome.isEnabled = true
        saisieDescription.isEnabled = true
        saisieCollection.isEnabled = true
        listePartage.isEnabled = true
        saisieFormat.isEnabled = true
        saisieQuantite.isEnabled = true

        switchLecture.isEnabled = true

        btnFavoris.isClickable = true
        btnEnregistrer.visibility = View.VISIBLE
        btnSupprimer.visibility = View.VISIBLE

        btnEdition.visibility = View.GONE
        miniatureOuvrage.isEnabled = true
    }

    /**
     * Désactive l'édition des informations de l'ouvrage et enregistre celui-ci en BDD.
     */
    fun enregistrerEdition(view: View) {
        // Désactiver le mode edition
        saisieTitre.isEnabled = false
        saisieAuteur.isEnabled = false
        saisieTome.isEnabled = false
        saisieDescription.isEnabled = false
        saisieCollection.isEnabled = false
        listePartage.isEnabled = false
        saisieFormat.isEnabled = false
        saisieQuantite.isEnabled = false

        switchLecture.isEnabled = false

        btnFavoris.isClickable = false
        btnEnregistrer.visibility = View.GONE
        btnSupprimer.visibility = View.GONE

        btnEdition.visibility = View.VISIBLE
        miniatureOuvrage.isEnabled = false

        // Enregistrement de l'ouvrage en base de données
        Toast.makeText(this, R.string.tempo_enregistrer_ouvrage, Toast.LENGTH_SHORT)
            .show()
    }


    /**
     * Supprime l'ouvrage.
     */
    fun supprimerOuvrage(view: View) {
        Toast.makeText(this, R.string.tempo_suppression_ouvrage, Toast.LENGTH_SHORT)
            .show()
    }

    /**
     * Modifie l'état favoris de l'ouvrage.
     */
    fun toggleFavoris(view: View) {
        if (btnFavoris.isClickable) {
            ouvrage.favori = !ouvrage.favori

            btnFavoris.setImageResource(when (ouvrage.favori) {
                false -> R.drawable.ic_favoris_vide
                true -> R.drawable.ic_favoris
            })
        }
    }

    /**
     * Sélection d'une image présente dans le smartphone.
     */
    fun choisirPhoto(view: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        startActivityForResult(intent, REQ_CODE_GET_MINIATURE)
    }


    /**
     * Résultat de la sélection d'une image.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var stream: InputStream? = null
        if (requestCode == REQ_CODE_GET_MINIATURE && resultCode == Activity.RESULT_OK) try {
            // Recycler les bitmaps inutilisés
            if (bitmap != null) {
                bitmap!!.recycle()
            }
            if (data != null) {
                stream = contentResolver.openInputStream(data.data!!)
            }
            bitmap = BitmapFactory.decodeStream(stream)
            miniatureOuvrage.setImageBitmap(bitmap)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            if (stream != null) try {
                stream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    /**
     * Affiche une fenêtre de dialogue pour réaliser le choix d'une collection.
     */
    fun selectionnerCollection(view: View) {
        val collections: Array<String> = resources.getStringArray(R.array.liste_collections)

        // Boite de dialogue contenant les collections gérées par l'application
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialog_collection_titre)
            .setNegativeButton(R.string.btn_annuler, null)
            .setItems(collections) { dialog, which ->
                saisieCollection.setText(collections[which])
            }
        builder.create()
        builder.show()
    }


    /**
     * Affiche une fenêtre de dialogue pour réaliser le choix d'une ou plusieurs listes de partage.
     */
    fun selectionnerListesPartage(view: View) {
        val listesPartage : Array<String> =                     // listes de partage de l'application
            resources.getStringArray(R.array.liste_partage)     // insérées par défaut ici

        val checkedItems = ArrayList<Boolean>()          // Items correspondant aux listes
                                                         // sélectionnées avant l'ouverture de la boite

        val listesPartageSelectionnees = ArrayList<String>()    // Listes sélectionnées dans la boite
                                                                // de dialogue


        /* Initialiser les index des items (noms des listes de partage) à sélectionner ou non */
        for (partage in listesPartage) {
            if (ouvrage.listesPartage.contains(partage)) {
                listesPartageSelectionnees.add(partage)
                checkedItems.add(true)
            } else {
                checkedItems.add(false)
            }
        }

        /* Création d'une boite de dialogue à choix multiple */
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialog_partage_titre)
            .setMultiChoiceItems(listesPartage, checkedItems.toBooleanArray()) {dialog, which, isChecked ->
                // listener de click sur un item de la boite de dialogue à choix multiple
                if (isChecked) {
                    listesPartageSelectionnees.add(listesPartage[which])

                } else if (listesPartageSelectionnees.contains(listesPartage[which])) {
                    listesPartageSelectionnees.remove(listesPartage[which])
                }
            }
            .setPositiveButton(R.string.btn_ok) { dialog, which ->
                // listener pour valider la sélection
                ouvrage.listesPartage = listesPartageSelectionnees

                // modification du champ présentant les listes de partage de l'ouvrage
                formaterListePartage()
            }
            .setNegativeButton(R.string.btn_annuler, null)
            .create()
            .show()
    }

    /**
     * Formate les listes de partage pour que chaque liste soit séparée par " - ".
     */
    private fun formaterListePartage() {
        listePartage.setText("")

        if (ouvrage.listesPartage.size > 0) {
            var nomsListesPartage = ""

            for (partage in ouvrage.listesPartage) {
                nomsListesPartage += partage + " - "
            }
            nomsListesPartage = nomsListesPartage.substring(0, nomsListesPartage.length - 3)

            listePartage.setText(nomsListesPartage)
        }
    }


    /**
     * Affiche une fenêtre de dialogue pour sélectionner un format de livre.
     */
    fun selectionnerFormat(view: View) {
        val formats: Array<String> =                        // Formats de livre insérés par défaut
            resources.getStringArray(R.array.formats_livre)

        // setup the alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialog_formats_titre)
            .setNegativeButton(R.string.btn_annuler, null)
            .setItems(formats) { dialog, which ->
                // listener pour valider le choix du format
                saisieFormat.text = formats[which]
            }
        builder.create()
        builder.show()
    }


    /**
     * Affiche une fenêtre de dialogue pour sélectionner une quantité.
     */
    fun selectionnerQuantite(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater

        // Vue personnalisé d'une boite de dialogue avec un numberpicker
        val dialogView = inflater.inflate(R.layout.number_picker_dialog, null)

        builder.setTitle(R.string.dialog_quantite_titre)
            .setView(dialogView)

        // Personnaliser le numberpicker de la boite de dialogue
        val numberPicker = dialogView.findViewById<View>(R.id.dialog_number_picker) as NumberPicker
        numberPicker.maxValue = 50
        numberPicker.minValue = 1
        numberPicker.wrapSelectorWheel = false

        builder.setPositiveButton(R.string.btn_ok) { dialogInterface, i ->
            // listenner pour valider la sélection d'une quantité
            saisieQuantite.text = numberPicker.value.toString()
        }

        builder.setNegativeButton(R.string.btn_annuler, null)
            .create()
            .show()
    }
}
