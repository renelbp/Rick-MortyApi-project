package com.example.rickandmortyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmortyapp.model.characters.CharactersViewModel
class CharacterListFragmet : Fragment() {
    private val epoxyController = CharactersListPagingEpoxyController(::onCharacterSelected)

    val viewModel: CharactersViewModel by lazy{
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.charactersPagedListLiveData.observe(viewLifecycleOwner){pagedList ->
            epoxyController.submitList(pagedList)
        }
        view.findViewById<EpoxyRecyclerView>(R.id.epoxy_recyclerview).setController(epoxyController)
    }

    private fun onCharacterSelected(characterId: Int){
/*
        val intent = Intent(this, CharacterDetailsActivity::class.java)
        intent.putExtra(Constants.INTENT_EXTRA_CHARACTER_ID, characterId)
        startActivity(intent)
*/
        val directions = CharacterListFragmetDirections
            .actionCharacterListFragmetToCharacterDetailsFragment(
            characterSelected = characterId
        )
        findNavController().navigate(directions)
    }


}