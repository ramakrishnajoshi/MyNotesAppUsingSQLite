package compassites.com.mynotesappusingsqlite;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import compassites.com.mynotesappusingsqlite.Model.NoteDataClass;

public class AddNewNoteActivity extends AppCompatActivity {

    EditText noteTitle, noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        noteTitle= findViewById(R.id.title_edit_text);
        noteContent = findViewById(R.id.content_edit_text);

        Button newNoteButton = findViewById(R.id.save_note_button);
        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noteTitle.getText()!= null || noteContent.getText()!=null)
                    saveTheNewNote();
                else
                {
                    Toast.makeText(AddNewNoteActivity.this,"Note Title and Content should not be empty...",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void saveTheNewNote() {

        NoteDataClass noteDataClass = new NoteDataClass(1, noteTitle.getText().toString(), noteContent.getText().toString());

        DataBaseHelper dataBaseHelper = /*new DataBaseHelper(this);*/ DataBaseHelper.getDatabaseHelperInstance(this);
        int temp = dataBaseHelper.addNote(noteDataClass);
        if (temp == 0) {
            Toast.makeText(AddNewNoteActivity.this, "Note Added", Toast.LENGTH_LONG).show();
            onBackPressed();
        } else if (temp == 1)
            Toast.makeText(AddNewNoteActivity.this, "Note Title/Content should not be empty!!!", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(AddNewNoteActivity.this, "Note Could not be saved...Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
