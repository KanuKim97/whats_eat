package com.example.whats_eat.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainBannerAdapter(mainBanner: Fragment): FragmentStateAdapter(mainBanner) {
    private val mainBannerList: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = mainBannerList.size

    override fun createFragment(position: Int): Fragment = mainBannerList[position]

    fun addMainBanner(mainBanner: Fragment) {
        mainBannerList.add(mainBanner)
        notifyItemInserted(mainBannerList.size - 1)
    }
}