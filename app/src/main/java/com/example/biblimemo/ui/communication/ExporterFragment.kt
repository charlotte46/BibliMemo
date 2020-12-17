package com.example.biblimemo.ui.communication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.biblimemo.R

/**
 * Exporter un ouvrage / une liste d'ouvrages sous différents formats.
 */
class ExporterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exporter, container, false)
    }

}
