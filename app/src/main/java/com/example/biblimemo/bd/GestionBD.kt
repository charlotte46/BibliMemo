package com.example.biblimemo.bd
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GestionBD(context:Context):SQLiteOpenHelper(context, NOM_BD, null, VERSION_BD) {
    companion object {
        /** Nom de la base de données qui contiendra les informations de l'application*/
        val NOM_BD = "biblimemo.db"

        /** Version de la base de données */
        val VERSION_BD = 1

        /** Nom de la table qui contiendra les informations des collections */
        val NOM_TABLE_COLLECTION = "tbl_collection"

        /** Nom du champ correspondant à l'identifiant d'une collection, sa clé */
        val COLLECTION_CLE = "id_collection"

        /** Nom du champ correspondant au titre d'une collection */
        val COLLECTION_TITRE = "titre"

        /** Nom du champ correspondant au nombre total de tome pour une collection */
        val COLLECTION_NB_TOME = "nb_tome"

        /** Nom du champ correspondant au statut de la collection 0 : non commencé, 1 : en cours, 2 terminé */
        val COLLECTION_STATUT = "statut"

        /** Nom de la table qui contiendra les informations des ouvrages */
        val NOM_TABLE_OUVRAGE = "tbl_ouvrage"

        /** Nom du champ correspondant à l'identifiant d'un ouvrage, sa clé */
        val OUVRAGE_CLE = "id_ouvrage"

        /** Nom du champ correspondant au chemin de stockage de la photo */
        val OUVRAGE_PATH_PHOTO = "path_photo"

        /** Nom du champ correspondant au titre d'un ouvrage */
        val OUVRAGE_TITRE = "titre"

        /** Nom du champ correspondant à l'auteur de l'ouvrage */
        val OUVRAGE_AUTEUR = "auteur"

        /** Nom du champ correspondant à l'identifiant de la collection de l'ouvrage */
        val OUVRAGE_COLLECTION = "ce_collection"

        /** Nom du champ correspondant au numéro du tome dans la collection */
        val OUVRAGE_NUMERO = "numero"

        /** Nom du champ correspondant au résumé de l'ouvrage */
        val OUVRAGE_RESUME = "resume"

        /** Nom du champ correspondant au format de l'ouvrage */
        val OUVRAGE_FORMAT = "format"

        /** Nom du champ correspondant à la quantité de l'ouvrage */
        val OUVRAGE_QTE = "quantite"

        /** Nom du champ correspondant à la lecture ou non de l'ouvrage */
        val OUVRAGE_LU = "lu"

        /** Nom du champ correspondant à la possession de l'ouvrage : 0 si non, 1 si en attente, 2 si en possession */
        val OUVRAGE_POSSEDE = "en_possession"

        /** Nom du champ correspondant à la position en favoris de l'ouvrage */
        val OUVRAGE_FAVORIS = "favoris"

        /** Nom du champ correspondant à l'identifiant de la liste de partage */
        val OUVRAGE_PARTAGE = "id_partage"

        /** Requête des champs contenus dans la table des collections */
        var CREATION_COLLECTION = (
                "( " + COLLECTION_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLLECTION_TITRE + " TEXT NOT NULL, "
                        + COLLECTION_NB_TOME + " INTEGER NOT NULL, "
                        + COLLECTION_STATUT + " NUMERIC NOT NULL "
                        + "CHECK (" + COLLECTION_NB_TOME + ">=0))")

        /** Requête des champs contenus dans la table des ouvrages */
        var CREATION_OUVRAGE = (
                "( " + OUVRAGE_CLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + OUVRAGE_PATH_PHOTO + " TEXT, "
                        + OUVRAGE_TITRE + " TEXT NOT NULL, "
                        + OUVRAGE_AUTEUR + " TEXT NOT NULL, "
                        + OUVRAGE_COLLECTION + " INTEGER NOT NULL, "
                        + OUVRAGE_NUMERO + "INTEGER NOT NULL, "
                        + OUVRAGE_RESUME + " BLOB, "
                        + OUVRAGE_FORMAT + " NUMERIC NOT NULL, "
                        + OUVRAGE_QTE + " INTEGER NOT NULL, "
                        + OUVRAGE_LU + " NUMERIC NOT NULL, "
                        + OUVRAGE_POSSEDE + " NUMERIC NOT NULL, "
                        + OUVRAGE_FAVORIS + " NUMERIC NOT NULL, "
                        + OUVRAGE_PARTAGE + " INTEGER "
                        + "FOREIGN KEY (" + OUVRAGE_COLLECTION + ") REFERENCES "
                        + NOM_TABLE_COLLECTION + "(" + COLLECTION_CLE + ")"
                        + "CHECK (" + OUVRAGE_NUMERO + ">=0))")

        /** Requête pour sélectionner tous les enregistrements d'une table */
        val REQUETE_TOUT_SELECTIONNER = "SELECT * FROM %s ORDER BY %s ;"

        /** Requête pour sélectionner des enregistrements selon des critères */
        val REQUETE_SELECTION = "SELECT * FROM %s WHERE %s ORDER BY %s ;"

        /** Requête de création de table */
        val REQUETE_CREATION_TABLE = "CREATE TABLE %s ;"

        /** Requête pour supprimer une table */
        val REQUETE_SUPPRIMER_TABLE = "DROP TABLE IF EXISTS %s ;"
    }

    override fun onCreate(bd: SQLiteDatabase) {
        // on créé la table des participants et la table des paiements
        bd.execSQL(
            String.format(
                REQUETE_CREATION_TABLE,
                NOM_TABLE_COLLECTION + " " + CREATION_COLLECTION
            )
        )
        bd.execSQL(
            String.format(
                REQUETE_CREATION_TABLE,
                NOM_TABLE_OUVRAGE + " " + CREATION_OUVRAGE
            )
        )
    }

    override fun onUpgrade(bd: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // on détruit et on recrée les tables
        bd.execSQL(String.format(REQUETE_SUPPRIMER_TABLE, NOM_TABLE_COLLECTION))
        bd.execSQL(String.format(REQUETE_SUPPRIMER_TABLE, NOM_TABLE_OUVRAGE))
        onCreate(bd)
    }

    /**
     * Permet de supprimer la base de données
     * @return true si la suppression s'est bien déroulé
     * false sinon
     */
    fun deleteBase(): Boolean {
        return this.deleteBase()
    }
}