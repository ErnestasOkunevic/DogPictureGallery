package com.ernokun.dogphotostask.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val url = savedStateHandle.get<String>(URL_KEY)!!

    private companion object {
        const val URL_KEY = "url"
    }
}