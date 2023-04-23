package com.example.catproject.presentation.ui.tapbar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.catproject.presentation.ui.cats.CatFragment
import com.example.catproject.presentation.ui.favorites.FavoritesFragment

class TabPageAdapter(activity: FragmentActivity, private val tabCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount() = tabCount

    override fun createFragment(position: Int): Fragment = when (position) {
        1 -> FavoritesFragment.newInstance()
        else -> CatFragment.newInstance()
    }
}