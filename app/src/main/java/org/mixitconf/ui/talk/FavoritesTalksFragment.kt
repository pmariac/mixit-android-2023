package org.mixitconf.ui.talk

import android.view.View

class FavoritesTalksFragment : TalksFragment() {

    override fun search() {
        viewModel.searchFavorites().observe(viewLifecycleOwner, { favorites ->
            viewBinding.txtNoFavorite.visibility = if(favorites.isEmpty()) View.VISIBLE else View.INVISIBLE
            viewBinding.rvTalks.visibility = if(favorites.isNotEmpty()) View.VISIBLE else View.INVISIBLE
            talksAdpater.setItems(favorites)
        })
    }
}