package com.example.rickandmortyapp

import com.example.rickandmortyapp.databinding.ModelLoadingBinding
import com.example.rickandmortyapp.epoxy.ViewBindingKotlinModel

class LoadingEpoxyModel : ViewBindingKotlinModel<ModelLoadingBinding>(R.layout.model_loading) {
    override fun ModelLoadingBinding.bind() {
        //nothing to do
    }
    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}