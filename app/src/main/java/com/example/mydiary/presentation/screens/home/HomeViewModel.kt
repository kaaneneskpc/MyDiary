package com.example.mydiary.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydiary.core.data.repository.Diaries
import com.example.mydiary.core.data.repository.MongoDB
import com.example.mydiary.core.model.RequestState
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    var diaries: MutableState<Diaries> = mutableStateOf(RequestState.Idle)

    init {
        observeAllDiaries()
    }

    private fun observeAllDiaries() {
        viewModelScope.launch {
            MongoDB.getAllDiaries().collect {
                diaries.value = it
            }
        }
    }
}