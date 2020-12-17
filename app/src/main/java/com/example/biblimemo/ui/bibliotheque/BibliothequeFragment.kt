package com.example.biblimemo.ui.bibliotheque

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.biblimemo.R
import com.example.biblimemo.ui.OuvrageDetailsActivity
import com.example.biblimemoapp.Ouvrage
import com.example.biblimemoapp.OuvragesAdapter
import kotlinx.android.synthetic.main.fragment_bibliotheque.*

/**
 * Ecran principal présentant la liste des ouvrages gérés par l'application.
 */
class BibliothequeFragment : Fragment(), OuvragesAdapter.OnOuvrageClickListener {

    companion object {
        /* Clé de l'extra correcpondant à l'ouvrage sélectionné */
        const val SELECTED_OUVRAGE = "com.example.biblimemo.ui.bibliotheque.SELECTED_OUVRAGE"
    }


    /** Liste des ouvrages remplie en dur avec des ouvrages par défaut */
    private var ouvrages = listOf<Ouvrage>(
        Ouvrage(1L, "Le rouge et le noir", "Stendhal", 0,
            "Fils de charpentier, Julien Sorel est trop sensible et trop ambitieux pour " +
                    "suivre la carrière familiale dans la scierie d’une petite ville de province. " +
                    "En secret, il rêve d’une ascension similaire à celle de Napoléon Bonaparte. " +
                    "Julien trouve une place de précepteur dans la maison du maire, Monsieur de Rénal, " +
                    "et noue une relation interdite avec son épouse. Jusqu’au bout, Julien Sorel verra " +
                    "ses ambitions contrecarrées par ses sentiments, qui les conduiront à sa perte...",
            R.mipmap.lerougeetlenoir, "Papier", 1, true, false,
            listOf<String>(), "Classiques"),

        Ouvrage(2L, "Detective Conan - Tome 1", "Gosho Aoyama", 1,
            "Shinichi Kudo est élève de première au lycée " +
                    "Tivedétec. Pour avoir résolu plusieurs enquêtes difficiles, beaucoup le considèrent " +
                    "comme «l'aide la plus précieuse que la police pouvait espérer». Un jour, alors que " +
                    "Shinichi se baladait avec sa petite amie Ran Mouri, il fait la rencontre d'hommes " +
                    "étranges vêtus de noir. Par curiosité et intuition, Shinichi les suit et comprend que " +
                    "ce sont des maîtres chanteurs. Découvert, on lui fait boire un poison expérimental " +
                    "pour le faire taire et l'effet est inattendu...: il rajeunit. Shinichi, aidé par son " +
                    "voisin le Dr.Agasa, inventeur génial et farfelu, décide de partir à la recherche de " +
                    "l'organisation secrète dont il a été victime. Il cache sa véritable identité sous " +
                    "le pseudonyme de Conan Edogawa, et se réfugie chez Ran, dont le père est détective.\n" +
                    "Les bases du manga sont posées: Conan habite chez Ran et il se sert de Kogoro pour " +
                    "résoudre ses enquêtes incognito. Ses gadgets lui donnent un côté \"James Bond\".",
            R.mipmap.detectiveconantome1, "Papier", 1, true, true,
            listOf<String>("Enquêtes"), "Détective Conan"),

        Ouvrage(3L, "Fullmetal Alchemist - Tome 1 : ",
            "Hiromu Arakawa", 1,
            "En voulant ressusciter leur mère, Edward et Alphonse Elric utilisent une " +
                    "technique alchimique interdite : la transmutation humaine. Seulement, l'expérience" +
                    " tourne mal : Edward perd un bras et une jambe et Alphonse, son corps, son esprit " +
                    "se retrouvant prisonnier d'une armure. Devenu un alchimiste d'État, Edward, " +
                    "surnommé le Fullmetal Alchemist, se lance, avec l'aide de son frère, à la recherche " +
                    "de la pierre philosophale, seule chance de retrouver leurs corps. Les deux frères " +
                    "commencent à enquêter sur un homme étrange, \" le fondateur \", qui passe pour un " +
                    "faiseur de miracles...",
            R.mipmap.fmatome1, "Papier", 1, false, false,
            listOf<String>(), "Mangas"),

        Ouvrage(4L, "Le crime de l'Orient-Express",
            "Agatha Christie", 0,
            "Dans l'Orient-Express bloqué par la neige, le fameux détective Hercule Poirot " +
                    "mène l'enquête. Puisque le criminel ne peut être que dans le wagon, il lui faut " +
                    "examiner tous les éléments: les douze voyageurs de nationalités différentes, les " +
                    "douze coups de poignard, et les alibis de chacun...",
            R.mipmap.lecrimedelorientexpress, "Papier", 1, true, true,
            listOf<String>("Mes classiques préférés", "Enquêtes"), "Classiques"),

        Ouvrage(5L, "Les Chevaliers d'Émeraude - Tome 1",
            "Anne Robillard", 1,
            "Apprenant que l'Empereur Noir s'apprête à envahir le continent de nouveau, le Roi d'Émeraude," +
                    "soucieux de protéger tous les peuples d'Enkidiev, ressuscite un ancien ordre de " +
                    "chevalerie. Choisis pour leurs dons particuliers, dotés de pouvoirs magiques, les " +
                    "nouveaux Chevaliers d'Émeraude sont au nombre de sept: six hommes et une femme." +
                    "Au moment où les compagnons d'armes se disent enfin prêts à combattre, la Reine " +
                    "Fan de Shola demande audience à Émeraude Ier et lui confie Kira, alors âgée de deux ans " +
                    "et encore inconsciente du rôle qu'elle sera appelée à jouer dans le futur des hommes. " +
                    "Ce jour-là, Wellan, le grand chef des Chevaliers, tombe profondément amoureux de la reine. " +
                    "Malheureusement, le Royaume de Shola subira les attaques féroces des dragons de l'Empereur " +
                    "Noir, et tous les Sholiens, y compris la reine, seront massacrés. Le cœur brisé, Wellan devra " +
                    "organiser la défense d'Enkidiev et repousser les forces du Mal... ",
            R.mipmap.chevaliersemeraudetome1, "Audio", 1, true, true,
            listOf<String>(), "")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bibliotheque, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Attacher l'adaptateur à la liste des ouvrages */
        val adapter = OuvragesAdapter(this)
        adapter.ouvrages = ouvrages
        liste_ouvrages.adapter = adapter
    }


    override fun onOuvrageClick(position: Int) {
        /* Obtention de l'ouvrage sélectionné et ouverture de sa fiche détail */
        val ouvrage : Ouvrage = ouvrages.get(position)

        val intent = Intent(activity, OuvrageDetailsActivity::class.java)
        intent.putExtra(SELECTED_OUVRAGE, ouvrage)
        startActivity(intent)
    }
}

