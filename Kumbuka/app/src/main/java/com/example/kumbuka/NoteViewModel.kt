package com.example.kumbuka

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kumbuka.database.NoteDatabase
import com.example.kumbuka.database.NoteEntity
import kotlinx.coroutines.launch

class NoteViewModel(context : Application) : AndroidViewModel(context) {

    val dao = NoteDatabase.getDatabase(context).dao()

    val selectedNotes = mutableStateListOf<NoteEntity>()

    fun updateNote(note: NoteEntity){
        viewModelScope.launch{
            dao.updateNote(note)
        }
    }

    fun deleteNotes(){
        viewModelScope.launch{
            dao.deleteNotes(selectedNotes)
        }
    }

    fun getData() = dao.getNotes()

    fun onChangeSelection(note : NoteEntity){
        if(selectedNotes.contains(note)){
            selectedNotes.remove(note)
        }
        else{
            selectedNotes.add(note)
        }
    }

    fun clearSelectedList(){
        selectedNotes.clear()
    }
}