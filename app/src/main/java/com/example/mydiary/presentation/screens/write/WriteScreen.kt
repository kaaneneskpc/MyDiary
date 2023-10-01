package com.example.mydiary.presentation.screens.write

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.mydiary.core.model.Diary
import com.example.mydiary.core.model.GalleryState
import com.example.mydiary.core.model.Mood

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WriteScreen(
    uiState: UiState,
    pagerState: PagerState,
    moodName: () -> String,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDeleteConfirmed: () -> Unit,
    onBackPressed: () -> Unit,
    onSaveClicked: (Diary) -> Unit,
) {

    LaunchedEffect(key1 = uiState.mood) {
        pagerState.scrollToPage(Mood.valueOf(uiState.mood.name).ordinal)
    }

    Scaffold(
        topBar = {
            WriteTopBar(
                selectedDiary = uiState.selectedDiary,
                moodName = moodName,
                onDeleteConfirmed = onDeleteConfirmed,
                onBackPressed = onBackPressed
            )
        },
        content = {
            WriteContent(
                uiState = uiState,
                pagerState = pagerState,
                // galleryState = galleryState,
                title = uiState.title,
                onTitleChanged = onTitleChanged,
                description = uiState.description,
                onDescriptionChanged = onDescriptionChanged,
                paddingValues = it,
                onSaveClicked = onSaveClicked,
               /* onImageSelect = {},
                onImageClicked = {} */
            )
        }
    )
}