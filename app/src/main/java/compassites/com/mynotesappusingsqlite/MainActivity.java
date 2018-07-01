package compassites.com.mynotesappusingsqlite;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import compassites.com.mynotesappusingsqlite.Adapter.NotesTakerAdapter;
import compassites.com.mynotesappusingsqlite.Model.NoteDataClass;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();
    DataBaseHelper dataBaseHelper;

    List<NoteDataClass> noteDataClassList;
    RecyclerView recyclerView;
    NotesTakerAdapter notesTakerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        dataBaseHelper = /*new DataBaseHelper(this);*/ DataBaseHelper.getDatabaseHelperInstance(this );

        /*if(Integer.toString(dataBaseHelper.getPassword()) != null)
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));
        else
                startActivity(new Intent(this, CreatePasswordActivity.class));*/


        initialiseFields();

        findViewById(R.id.add_new_note_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddNewNoteActivity.class));
            }
        });

        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
               /* List<NoteDataClass> noteDataClassList = dataBaseHelper.getAllNotes();

                List<NoteDataClass> tempNoteDataClassList = new ArrayList<>();
                for (NoteDataClass noteDataClass: noteDataClassList)
                {
                    if ((noteDataClass.getNoteTitle().toLowerCase()).contains(query.toLowerCase()))
                        tempNoteDataClassList.add(noteDataClass);
                }
                notesTakerAdapter.update(tempNoteDataClassList);
                return true;*/
               return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<NoteDataClass> noteDataClassList = dataBaseHelper.getAllNotes();

                List<NoteDataClass> tempNoteDataClassList = new ArrayList<>();
                for (NoteDataClass noteDataClass: noteDataClassList)
                {
                    if (noteDataClass.getNoteTitle().toLowerCase().contains(newText.toLowerCase()))
                        tempNoteDataClassList.add(noteDataClass);
                }
                notesTakerAdapter.update(tempNoteDataClassList);
                return true;
            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                List<NoteDataClass> temp =dataBaseHelper.getAllNotes();
                notesTakerAdapter.update(temp);
                return false;
            }
        });
    }

    private void initialiseFields() {
        boolean result;

        /*NoteDataClass noteDataClass = new NoteDataClass(1,"Title1","Content1");
        result = dataBaseHelper.addNote(noteDataClass);
        Log.e(TAG,"Add Note:" + noteDataClass + " Result: "+ result);

        noteDataClass = new NoteDataClass(2,"Title2","Content2");
        result = dataBaseHelper.addNote(noteDataClass);
        Log.e(TAG,"Add Note:" + noteDataClass + " Result: "+ result);*/


        noteDataClassList = dataBaseHelper.getAllNotes();
        Log.e(TAG,"All Notes:" + noteDataClassList );

        /*Iterator iterator = noteDataClassList.iterator();
        while (iterator.hasNext())
        {
            NoteDataClass noteDataClass2 = (NoteDataClass) iterator.next();
            Log.e("All Notes Iterator:", "" + noteDataClass2.toString());
        }*/

         recyclerView = findViewById(R.id.recycler_view_notes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (noteDataClassList != null) {
            notesTakerAdapter = new NotesTakerAdapter(noteDataClassList,this);
            recyclerView.setAdapter(notesTakerAdapter);
        }
        else
            Toast.makeText(this,"noteDataClassList is null",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseFields();
    }
}
