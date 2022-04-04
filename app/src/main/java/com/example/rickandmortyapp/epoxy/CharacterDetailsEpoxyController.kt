package com.example.rickandmortyapp.epoxy

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortyapp.LoadingEpoxyModel
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.*
import com.example.rickandmortyapp.domain.Character
import com.example.rickandmortyapp.model.Episode
import com.squareup.picasso.Picasso

class CharacterDetailsEpoxyController : EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var character: Character? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading) {
            //show loading state
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }
        if (character == null) {
            //todo error state
            return
        }
        //add the Header model
         HeaderEpoxyModel(
            name = character!!.name,
            status = character!!.status,
            gender = character!!.gender,
        ).id("header").addTo(this)

        //add header model
        ImageEpoxyModel(imageUrl = character!!.image).id("header").addTo(this)

        //carousel's title
        TitleCarouselEpoxyModel(
            title = "Episodes"
        ).id("carousel_title").addTo(this)

        //Episode carousel list section
        if (character!!.episodeList.isNotEmpty()) {
            val items = character!!.episodeList.map {
                EpisodeCarouselItemEpoxyModel(it).id(it.id)
            }
            CarouselModel_()
                .id("episodes_carousel")
                .models(items)
                .numViewsToShowOnScreen(1.3f)
                .addTo(this)
        }

        //Add details models
        DetailsEpoxyModel(
            title = "Origin",
            description = character!!.origin.name
        ).id("origin").addTo(this)
        DetailsEpoxyModel(
            title = "Species",
            description = character!!.species
        ).id("species").addTo(this)

    }


    data class ImageEpoxyModel(
        val imageUrl: String,
    ) : ViewBindingKotlinModel<ModelCharacterImageBinding>(R.layout.model_character_image) {
        override fun ModelCharacterImageBinding.bind() {
                Picasso.get().load(imageUrl).into(ivHeader)
        }
    }

    data class HeaderEpoxyModel(
        val name: String,
        val status: String,
        val gender: String,
    ):ViewBindingKotlinModel<ModelCharacterHeaderBinding>(R.layout.model_character_header) {
        override fun ModelCharacterHeaderBinding.bind() {
            tvName.text = name
            tvAlive.text = status
            if (gender.contentEquals("Male")) {
                ivGender.setImageResource(R.drawable.ic_gender_male_24)
            } else ivGender.setImageResource(R.drawable.ic_gender_female_24)
        }
    }


    data class DetailsEpoxyModel(
        val title: String,
        val description: String,
    ) : ViewBindingKotlinModel<ModelCharacterDetailsBinding>(R.layout.model_character_details) {
        override fun ModelCharacterDetailsBinding.bind() {
            tvLabel.text = title
            tvDescription.text = description
        }
    }

    data class EpisodeCarouselItemEpoxyModel(
        val episode: Episode
    ): ViewBindingKotlinModel<ModelEpisodeCarouselItemBinding>(R.layout.model_episode_carousel_item) {
        override fun ModelEpisodeCarouselItemBinding.bind() {
            tvEpisode.text = episode.episode
            episodeDetailsTextView.text = "${episode.name}\n${episode.airDate}"
        }
    }

    data class TitleCarouselEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelCarouselTitleBinding>(R.layout.model_carousel_title) {
        override fun ModelCarouselTitleBinding.bind() {
            tvLabel.text = title
        }
    }


}




