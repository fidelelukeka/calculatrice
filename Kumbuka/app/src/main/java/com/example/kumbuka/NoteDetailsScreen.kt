package com.example.kumbuka

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.kumbuka.database.NoteEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    note : NoteEntity,
    onNavigateBack : () -> Unit,
    onUpdateNote : (NoteEntity) -> Unit,
){

    var title by rememberSaveable { mutableStateOf(note.title) }
    var desc by rememberSaveable { mutableStateOf(note.desc) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { onNavigateBack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                           onUpdateNote(
                               NoteEntity(
                                   note.id,
                                   title,
                                   desc
                               )
                           )
                            onNavigateBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Mise à jour"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)

        ){
            MyTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                textStyle = MaterialTheme.typography.titleLarge,
                placeholder = {
                    Text("Titre de la note")
                },
                modifier = Modifier.fillMaxWidth()
            )

            MyTextField(
                value = desc,
                onValueChange = {
                    desc = it
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text("Description de la note")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    value : String,
    onValueChange : (String) -> Unit,
    textStyle : TextStyle,
    placeholder : @Composable (() -> Unit)
){
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        placeholder = placeholder,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}


