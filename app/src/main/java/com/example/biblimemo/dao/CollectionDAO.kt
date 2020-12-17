package com.example.biblimemo.dao

import com.example.biblimemo.bd.GestionBD

class CollectionDAO {
    /** Nom de la table qui contiendra les informations des collections */
    val NOM_TABLE_COLLECTION = "tbl_collection"

    /** Nom du champ correspondant à l'identifiant d'une collection, sa clé */
    val COLLECTION_CLE = "id_collection"

    /** Nom du champ correspondant au titre d'une collection */
    val COLLECTION_TITRE = "titre"

    /** Nom du champ correspondant au nombre total de tome pour une collection */
    val COLLECTION_NB_TOME = "nb_tome"

    companion object {
        /** Numéro de la colonne contenant l'identifiant d'une collection, sa clé */
        val COLONNE_COLLECTION_CLE = 0

        /** Numéro de la colonne contenant le titre d'une collection */
        val COLONNE_COLLECTION_TITRE = 1

        /** Numéro de la colonne contenant le nombre de tome d'une collection */
        val COLONNE_COLLECTION_NB_TOME = 2

        /** Numéro de la colonne contenant le statut d'une collection */
        val COLONNE_COLLECTION_STATUT = 3
    }
}