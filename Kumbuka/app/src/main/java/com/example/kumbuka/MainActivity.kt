package com.example.kumbuka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kumbuka.database.NoteEntity
import com.example.kumbuka.ui.theme.KumbukaTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KumbukaTheme {
                val viewModel by viewModels<NoteViewModel>()

                val notes = viewModel.getData().collectAsStateWithLifecycle(emptyList())

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ListNotes
                ){
                    composable<ListNotes>{
                        NoteListScreen(
                            notes = notes.value,
                            selectedList = viewModel.selectedNotes,
                            onDeleteNote = {
                                viewModel.deleteNotes()
                            },
                            onClickAddNote = {
                                navController.navigate(DetailsNote())
                            },
                            onChangeSelection = {note ->
                                viewModel.onChangeSelection(note)
                            },
                            onNavigateToNoteDetailsScreen = {note ->
                                navController.navigate(
                                    DetailsNote(
                                        id = note.id,
                                        title = note.title,
                                        desc = note.desc
                                    )
                                )
                            },
                            onClearSelection = {
                                viewModel.clearSelectedList()
                            }
                        )
                    }
                    composable<DetailsNote>{
                        val args = it.toRoute<DetailsNote>()
                        val note = NoteEntity(
                            id = args.id,
                            title = args.title,
                            desc = args.desc
                        )
                        NoteDetailsScreen(
                            note = note,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            onUpdateNote = { note ->
                                viewModel.updateNote(note)
                            }
                        )
                    }
                }

            }
        }
    }
}


@Serializable
object ListNotes

@Serializable
data class DetailsNote( val id : Int? = null, val title : String = "", val desc : String = "")