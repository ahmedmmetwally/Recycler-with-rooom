package com.example.myaplictionexamplemodel3;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    NoteRepository(Application application){
        NoteDatabase database=NoteDatabase.getInstance(application);
        noteDao=database.noteDao();
        allNotes=noteDao.getAllNotes();
    }
    public void insert(Note note){
new InsertAsyncTask(noteDao).execute(note);
    }
    public void update(Note note){
new UpdateAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note){
        new DeleteAsyncTask(noteDao).execute(note);

    }
    public void deleteAllnotes(){
      new  DeltetAllAsyncTask(noteDao).execute();
    }
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
    private static class InsertAsyncTask extends AsyncTask<Note ,Void,Void>{
        private NoteDao noteDao;
        InsertAsyncTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        UpdateAsyncTask(NoteDao no){
            this.noteDao=no;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.udpte(notes[0]);
            return null;
        }
    }
    private static class DeleteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        DeleteAsyncTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
  private static class DeltetAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;
      DeltetAllAsyncTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }
      @Override
      protected Void doInBackground(Void... voids) {
          noteDao.deleteAll();
          return null;
      }
  }
}
