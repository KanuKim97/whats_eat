package com.kanukim97.detail.screen.action

sealed interface DetailUiAction {
    data object OnBackBtnClick: DetailUiAction

    data object OnAddCollection : DetailUiAction

    data object OnShareBtnClick: DetailUiAction

    data object OnSeeFullMenuClick: DetailUiAction

    data object OnSeeAllReviewBtnClick: DetailUiAction

    data object OnGetDirectionsBtnClick: DetailUiAction

    data object OnDialIconBtnClick: DetailUiAction
}

