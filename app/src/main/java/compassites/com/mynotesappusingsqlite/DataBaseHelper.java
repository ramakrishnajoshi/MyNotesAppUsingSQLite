package compassites.com.mynotesappusingsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import compassites.com.mynotesappusingsqlite.Model.NoteDataClass;

/**
 * Created by ramakrishna on 3/5/18.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "NOTES_DB";
    public static final String NOTES_TABLE_NAME = "NOTES_TABLE_NAME";
    public static final String PASSWORD_TABLE_NAME = "Lock";
    public static final String COLUMN_NOTES_ID="NOTE_ID";
    public static final String COLUMN_NOTES_TITLE="NOTE_TITLE";
    public static final String COLUMN_NOTES_CONTENT="NOTE_CONTENT";
    public static final String COLUMN_PASSWORD_TABLE_PASSWORD_FIELD="password";
    Context context;

    static DataBaseHelper dataBaseHelper = null;

    private DataBaseHelper(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    public static DataBaseHelper getDatabaseHelperInstance(Context context)
    {
        if (dataBaseHelper == null)
            dataBaseHelper = new DataBaseHelper(context);
        return dataBaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String notes_table_create_statement =
                "CREATE TABLE `NOTES_TABLE_NAME` (\n" +
                        "\t`NOTE_ID`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                        "\t`NOTE_TITLE`\tTEXT DEFAULT 'NOTE_TEXT',\n" +
                        "\t`NOTE_CONTENT`\tTEXT DEFAULT 'NOTE_CONTENT'\n" +
                        ");";

        db.execSQL(notes_table_create_statement);
        createPasswordTable(db);
    }

    private void createPasswordTable(SQLiteDatabase db) {
        String pass = "CREATE TABLE `Lock` (\n" +
                "\t`password`\tINTEGER NOT NULL\n" +
                ")";

        db.execSQL(pass);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("dbHelper 2","");
    }

    int addNote(NoteDataClass note)
    {
        if (note.getNoteTitle().length() < 1 || note.getNoteContent().length() < 1) {
            return 1; //can't add note
        }
        else {

            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
           // contentValues.put(COLUMN_NOTES_ID,note.getNoteId());
            contentValues.put(COLUMN_NOTES_TITLE,note.getNoteTitle());
            contentValues.put(COLUMN_NOTES_CONTENT,note.getNoteContent());
            if (sqLiteDatabase.insert(NOTES_TABLE_NAME,null,contentValues) >= 0)
                return 0;

            else
                return 2;
        }
    }

    List<NoteDataClass> getAllNotes()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<NoteDataClass> noteDataClassList = new ArrayList<>();

        Cursor cursor =
        sqLiteDatabase.rawQuery("SELECT * FROM " + NOTES_TABLE_NAME , null);

        if (cursor.moveToFirst()) {
            Log.e("moveToFirst : ", "" + cursor.moveToFirst());

            do {
                NoteDataClass noteDataClass = new NoteDataClass(/*Integer.getInteger(cursor.getString(0))*/ cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                noteDataClassList.add(noteDataClass);
            } while (cursor.moveToNext());
        }
        return noteDataClassList;
    }


    int getPassword()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("SELECT * FROM " + PASSWORD_TABLE_NAME,null );
        // sqLiteDatabase.query(PASSWORD_TABLE_NAME,null,null,null,null,null,null);

        try {
            cursor.moveToFirst();
            int i = cursor.getInt(0);
            return i;
        }
        catch (Exception e)
        {
            return -9;
        }
    }

    boolean setPassword(String pass)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD_TABLE_PASSWORD_FIELD, pass);
        if (sqLiteDatabase.insert(PASSWORD_TABLE_NAME,null,contentValues)>= 0)
            return true;
        return false;
    }

    int updateNote(NoteDataClass noteDataClass)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        // contentValues.put(COLUMN_NOTES_ID, noteDataClass.getNoteId());
        contentValues.put(COLUMN_NOTES_TITLE, noteDataClass.getNoteTitle());
        contentValues.put(COLUMN_NOTES_CONTENT, noteDataClass.getNoteContent());

        int noOfRowsAffected = sqLiteDatabase.update(NOTES_TABLE_NAME,contentValues,"" + COLUMN_NOTES_ID + " =" + noteDataClass.getNoteId(),null );

        return noOfRowsAffected;
    }

     public int deleteNote(NoteDataClass noteDataClass)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int noOfRowsAffected = sqLiteDatabase.delete(NOTES_TABLE_NAME,"" + COLUMN_NOTES_ID + "=" + noteDataClass.getNoteId(),null);
        return noOfRowsAffected;
    }
}
