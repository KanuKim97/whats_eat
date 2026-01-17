package com.kanukim97.collection.screen.action

sealed interface CollectionUiAction {
    data object OnBackBtnClick: CollectionUiAction

    data class OnSearchQuery(val query: String): CollectionUiAction

    data class OnDeleteCollection(val id: String): CollectionUiAction

    data class OnClickCollection(val id: String): CollectionUiAction
}