package com.example.rickandmortyapp.model.characters

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmortyapp.CharactersListPagingEpoxyController
import com.example.rickandmortyapp.Constants
import com.example.rickandmortyapp.MainActivity
import com.example.rickandmortyapp.R

class CharacterListActivity: AppCompatActivity() {
    private val epoxyController = CharactersListPagingEpoxyController(::onCharacterSelected)

    val viewModel: CharactersViewModel by lazy{
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?,) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)
        viewModel.charactersPagedListLiveData.observe(this){pagedList ->
            epoxyController.submitList(pagedList)
        }
        findViewById<EpoxyRecyclerView>(R.id.epoxy_recyclerview).setController(epoxyController)
    }

    private fun onCharacterSelected(characterId: Int){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.INTENT_EXTRA_CHARACTER_ID, characterId)
        startActivity(intent)
    }
}