package com.example.biblimemo.ui.communication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.biblimemo.R

/**
 * Lancer l'impression d'un ouvrage ou d'une liste d'ouvrages.
 */
class ImprimerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_imprimer, container, false)
    }
}
