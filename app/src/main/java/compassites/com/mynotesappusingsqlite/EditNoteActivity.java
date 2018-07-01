package compassites.com.mynotesappusingsqlite;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.IdentityHashMap;

import compassites.com.mynotesappusingsqlite.Model.NoteDataClass;

public class EditNoteActivity extends AppCompatActivity {

    EditText editTitle, editContent;
    Button editNoteButton;
    int noteId;
    String noteTitle,noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        editTitle = findViewById(R.id.title_edit_text);
        editContent = findViewById(R.id.content_edit_text);
        editNoteButton = findViewById(R.id.save_note_button);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-9);
        noteTitle = intent.getStringExtra("noteTitle");
        noteContent = intent.getStringExtra("noteContent");

        Toast.makeText(this, "" + noteId + noteTitle, Toast.LENGTH_SHORT).show();

        editTitle.setText(noteTitle);
        editContent.setText(noteContent);

        editNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String noteTitle = editTitle.getText().toString();
                String noteContent = editContent.getText().toString();

                NoteDataClass noteDataClass = new NoteDataClass(noteId, noteTitle, noteContent);

                DataBaseHelper dataBaseHelper = DataBaseHelper.getDatabaseHelperInstance(EditNoteActivity.this);
                int noOfRowsAffected = dataBaseHelper.updateNote(noteDataClass);
                Toast.makeText(EditNoteActivity.this, "Note Edited...No of rows affected: " + noOfRowsAffected, Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
