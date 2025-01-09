package com.example.kumbuka

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kumbuka.database.NoteEntity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteListScreen(
    notes: List<NoteEntity>,
    selectedList: List<NoteEntity>,
    onDeleteNote : () -> Unit,
    onClickAddNote : () -> Unit,
    onChangeSelection : (NoteEntity) -> Unit,
    onNavigateToNoteDetailsScreen : (NoteEntity) -> Unit,
    onClearSelection : () -> Unit,
){
    BackHandler(enabled = selectedList.isNotEmpty()) {
        onClearSelection()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Kumbuka")
                },
                actions = {
                    if(selectedList.isEmpty()){
                        IconButton(
                            onClick = {
                                onDeleteNote()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Supprimer la note"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onClickAddNote()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Ajouter une notes"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues).padding(16.dp)
        ){
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(15.dp),
                modifier = Modifier.fillMaxSize(),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notes){note ->
                    OutlinedCard(
                        border = if(selectedList.contains(note)) BorderStroke(3.dp, MaterialTheme.colorScheme.tertiary) else CardDefaults.outlinedCardBorder(),
                        modifier = Modifier
                            .fillMaxSize()
                            .combinedClickable(
                                onLongClick = {
                                    onChangeSelection(note)
                                },
                                onClick = {
                                    if(selectedList.isNotEmpty()){
                                        onChangeSelection(note)
                                    }else{
                                        onNavigateToNoteDetailsScreen(note)
                                    }
                                }
                            )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)){
                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                text = note.desc
                            )
                        }
                    }
                }
            }
        }
    }
}