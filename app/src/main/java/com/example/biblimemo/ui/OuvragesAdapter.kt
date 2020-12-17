package com.example.biblimemoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.biblimemo.R

/**
 * Adaptateur personalisé pour l'affichage d'une liste d'ouvrages.
 */
class OuvragesAdapter (onOuvrageClickListener: OnOuvrageClickListener): RecyclerView.Adapter<OuvragesAdapter.OuvragesViewHolder>() {

    companion object {
        /**
         * Longueurs maximales pour les titres et description d'un ouvrage dans un item de la liste
         * des ouvrages
         */
        const val LONGUEUR_TITRE = 30
        const val LONGUEUR_DESC = 90
    }

    /** Liste des ouvrages */
    var ouvrages = listOf<Ouvrage>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /** Listener pour le clic sur un item de la liste des ouvrages */
    private val onOuvrageClickListener : OnOuvrageClickListener

    init {
        this.onOuvrageClickListener = onOuvrageClickListener
    }

    /**
     * Informe RecyclerView sur comment créer un ViewHolder personnalisé.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuvragesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.list_item_ouvrage, parent, false)

        return OuvragesViewHolder(view, onOuvrageClickListener)
    }

    /**
     * Informe RecyclerView sur la manière de dessiner un item de la liste des ouvrages.
     * La longueur du titre et de la description d'un ouvrage sont limitées.
     */
    override fun onBindViewHolder(holder: OuvragesViewHolder, position: Int) {
        val item = ouvrages[position]

        // Titre avec un nombre de caractères maximum
        var titre = item.titre
        if (titre.length > LONGUEUR_TITRE && titre.length != LONGUEUR_TITRE) {
            titre = titre.substring(0, LONGUEUR_TITRE - 4)
            titre += "..."
        }
        holder.titreOuvrage.text = titre

        // Description avec un nombre de caractères maximum
        var description = item.description
        if (description.length > LONGUEUR_DESC && description.length != LONGUEUR_DESC) {
            description = description.substring(0, LONGUEUR_DESC - 4)
            description += "..."
        }
        holder.descOuvrage.text = description

        // Miniature de l'ouvrage
        holder.miniatureOuvrage.setImageResource(item.miniature)

        // Statut favori ou non de l'ouvrage
        holder.favori.setImageResource(when (item.favori) {
            false -> R.drawable.ic_favoris_vide
            true -> R.drawable.ic_favoris
        })
    }

    /**
     * Informe RecyclerView du nombre d'ouvrages contenus dans la liste.
     */
    override fun getItemCount() = ouvrages.size

    /**
     * ViewHolder personnalisé contenant quelques informations sur un ouvrage :
     *  - la miniature
     *  - le titre (longueur restreinte)
     *  - la description (longueur restreinte)
     *  - s'il est dans les favoris ou non
     */
    class OuvragesViewHolder(itemView: View, onOuvrageClickListener: OnOuvrageClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val titreOuvrage : TextView = itemView.findViewById(R.id.ouvrage_titre)
        val descOuvrage : TextView = itemView.findViewById(R.id.ouvrage_desc)
        val miniatureOuvrage : ImageView = itemView.findViewById(R.id.ouvrage_miniature)
        val favori: ImageView = itemView.findViewById(R.id.ouvrage_favoris)

        /** Listener pour le click sur l'item de la liste des ouvrages */
        val onOuvrageClickListener : OnOuvrageClickListener

        init {
            this.onOuvrageClickListener = onOuvrageClickListener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onOuvrageClickListener.onOuvrageClick(adapterPosition)
        }
    }

    interface OnOuvrageClickListener {
        fun onOuvrageClick(position: Int)
    }


}