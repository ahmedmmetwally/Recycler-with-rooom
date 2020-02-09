package com.example.myaplictionexamplemodel3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final int Add_Note_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllnotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
                noteAdapter.notifyDataSetChanged();

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getAllnote(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Note  deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);
        noteAdapter.setOnClicccccck(new NoteAdapter.MyClickListener() {
            @Override
            public void onClick(Note note) {
                String title = note.getTitle();
                String des = note.getDescription();
                int pro = note.getPriority();
                int id = note.getId();
                Intent aa = new Intent(MainActivity.this, AddNoteActivity.class);
                aa.putExtra(AddNoteActivity.extra_Title, title);
                aa.putExtra(AddNoteActivity.extra_Description, des);
                aa.putExtra(AddNoteActivity.extra_Priority, pro);
                aa.putExtra(AddNoteActivity.extra_ID, id);
                startActivityForResult(aa, EDIT_NOTE_REQUEST);


            }
        });
    }

    @OnClick(R.id.button_add_note)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, Add_Note_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Add_Note_REQUEST && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra(AddNoteActivity.extra_Title);
            String description = data.getStringExtra(AddNoteActivity.extra_Description);
            int priority = data.getIntExtra(AddNoteActivity.extra_Priority, 1);

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "  saved", Toast.LENGTH_LONG).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getIntExtra(AddNoteActivity.extra_ID, -1) != -1) {
                String title = data.getStringExtra(AddNoteActivity.extra_Title);
                String description = data.getStringExtra(AddNoteActivity.extra_Description);
                int priority = data.getIntExtra(AddNoteActivity.extra_Priority, 1);
                int id = (data.getIntExtra(AddNoteActivity.extra_ID, -1));
                Note note = new Note(title, description, priority);
                note.setId(id);
                noteViewModel.update(note);
                Toast.makeText(this, "  note update", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "  note can not be saved", Toast.LENGTH_LONG).show();
                return;
            }

        } else Toast.makeText(this, "Note  saved", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu:
                noteViewModel.deleteallnotes();
                Toast.makeText(this, " all notes deleted", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
