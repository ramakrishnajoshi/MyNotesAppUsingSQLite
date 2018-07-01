package compassites.com.mynotesappusingsqlite.Model;

import android.support.annotation.NonNull;

/**
 * Created by ramakrishna on 3/5/18.
 */

public class NoteDataClass {


    int noteId;
    String noteTitle;
    String noteContent;

    public NoteDataClass(int noteId, String noteTitle, String noteContent) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(@NonNull int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    @Override
    public String toString() {
        return "NoteDataClass{" +
                "noteId=" + noteId +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteContent='" + noteContent + '\'' +
                '}';
    }
}
