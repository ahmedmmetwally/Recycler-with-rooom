package com.example.myaplictionexamplemodel3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNoteActivity extends AppCompatActivity {
    public static final String extra_ID = "com.example.architectureexample.Extra_Id";
    public static final String extra_Title = "com.example.architectureexample.Extra_Title";
    public static final String extra_Description = "com.example.architectureexample.Extra_Description";
    public static final String extra_Priority = "com.example.architectureexample.Extra_Priority";

    @BindView(R.id.edit_text_title_)
    EditText editTextTitle;
    @BindView(R.id.edit_text_Description_)
    EditText editTextDescription;
    @BindView(R.id.number_picker_priority)
    NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);

        numberPickerPriority.setMaxValue(10);
        numberPickerPriority.setMinValue(1);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_black_24dp);
        Intent data = getIntent();
        if (data.hasExtra(extra_ID)) {
            setTitle("update note");
            editTextTitle.setText(data.getStringExtra(extra_Title));
            editTextDescription.setText(data.getStringExtra(extra_Description));
            numberPickerPriority.setValue(data.getIntExtra(extra_Priority, 1));

        }else
        setTitle("add note");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = editTextTitle.getText().toString().trim();
        String descroption = editTextDescription.getText().toString().trim();
        int priority = numberPickerPriority.getValue();
        if (title.isEmpty() || descroption.isEmpty()) {
            Toast.makeText(this, "please inter value", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(extra_Title, title);
        intent.putExtra(extra_Description, descroption);
        intent.putExtra(extra_Priority, priority);
        if(getIntent().getIntExtra(extra_ID,-1)!=-1){
            intent.putExtra(extra_ID,getIntent().getIntExtra(extra_ID,-1));
        }
        setResult(RESULT_OK, intent);
        finish();


    }
}
