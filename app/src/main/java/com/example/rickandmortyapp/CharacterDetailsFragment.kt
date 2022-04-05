package com.example.rickandmortyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmortyapp.epoxy.CharacterDetailsEpoxyController
import com.example.rickandmortyapp.viewmodel.SharedViewModel


class CharacterDetailsFragment : Fragment() {
    private val args: CharacterDetailsFragmentArgs by navArgs()
    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }
    private val epoxyController = CharacterDetailsEpoxyController()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshCharacter(characterId = args.characterSelected)
        viewModel.characterByIdLiveData.observe(viewLifecycleOwner) { character ->
            epoxyController.character = character
            if (character == null) {
                Toast.makeText(requireContext(), "Unsuccesful Network Call", Toast.LENGTH_SHORT)
                    .show()
                return@observe
            }
        }
        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.recycler_view)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

}