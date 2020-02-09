package com.example.myaplictionexamplemodel3;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepoistory;
    private LiveData<List<Note>> noteAll;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepoistory =new NoteRepository(application);
        noteAll=noteRepoistory.getAllNotes();
    }
    public void insert(Note note ){
        noteRepoistory.insert(note);
    }
    public void update(Note note){
        noteRepoistory.update(note);
    }
    public void delete(Note note){
        noteRepoistory.delete(note);
    }
    public void deleteallnotes(){
        noteRepoistory.deleteAllnotes();
    }
    public LiveData<List<Note>> getAllnotes(){
        return noteAll;
    }
}
