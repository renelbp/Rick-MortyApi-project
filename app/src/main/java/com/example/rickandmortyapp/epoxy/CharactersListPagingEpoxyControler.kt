package com.example.rickandmortyapp

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.rickandmortyapp.databinding.ModelCharacterListItemBinding
import com.example.rickandmortyapp.databinding.ModelCharacterListTitleBinding
import com.example.rickandmortyapp.epoxy.ViewBindingKotlinModel
import com.example.rickandmortyapp.model.network.response.GetCharacterByIdResponse
import com.squareup.picasso.Picasso
import java.util.*

class CharactersListPagingEpoxyController(
   private val onCharacterSelected: (Int) ->  Unit
): PagedListEpoxyController<GetCharacterByIdResponse>() {
    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?,
    ): EpoxyModel<*> {
        return CharactersGridItemEpoxyModel(
            characterId = item!!.id,
            imageUrl = item.image,
            name = item.name,
            onCharacterSelected = onCharacterSelected
        ).id(item.id)
    }

   override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()){
            LoadingEpoxyModel().id("loading").addTo(this)

            return
        }
        CharactersGridTitleEpoxyModel("Main Family").id("main_family_header").addTo(this)
        super.addModels(models.subList(0,5))

        (models.subList(5,models.size)as List<CharactersGridItemEpoxyModel>).groupBy {
            it.name[0]
        }.forEach{mapEntry ->
            val character = mapEntry.key.toString()
            CharactersGridTitleEpoxyModel(title = character).id(character)
                .addTo(this)
            super.addModels(mapEntry.value)
        }

  }

//region Data Classes
    data class CharactersGridItemEpoxyModel(
        val characterId: Int,
        val imageUrl: String,
        val name: String,
        val onCharacterSelected: (Int) ->  Unit
    ):ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {
        override fun ModelCharacterListItemBinding.bind() {
            Picasso.get().load(imageUrl).into(ivCharacterMini)
            tvCharacterName.text = name
            root.setOnClickListener {
                onCharacterSelected(characterId)
            }
        }
    }

    data class CharactersGridTitleEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title){
        override fun ModelCharacterListTitleBinding.bind() {
            tvListTitle.text = title
        }
        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }

    }
}