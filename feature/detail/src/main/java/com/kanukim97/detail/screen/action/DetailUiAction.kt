package com.kanukim97.detail.screen.action

sealed interface DetailUiAction {
    data object OnBackBtnClick: DetailUiAction

    data class OnLikeBtnClick(
        val id: String,
        val name: String,
        val latLng: String,
        val imageUrl: String
    ): DetailUiAction

    data object OnShareBtnClick: DetailUiAction

    data object OnSeeFullMenuClick: DetailUiAction

    data object OnSeeAllReviewBtnClick: DetailUiAction

    data object OnGetDirectionsBtnClick: DetailUiAction

    data object OnCallBtnClick: DetailUiAction
}

