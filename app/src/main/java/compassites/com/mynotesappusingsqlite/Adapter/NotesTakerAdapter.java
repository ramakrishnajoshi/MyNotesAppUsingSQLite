package compassites.com.mynotesappusingsqlite.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.zip.Inflater;

import compassites.com.mynotesappusingsqlite.DataBaseHelper;
import compassites.com.mynotesappusingsqlite.EditNoteActivity;
import compassites.com.mynotesappusingsqlite.MainActivity;
import compassites.com.mynotesappusingsqlite.Model.NoteDataClass;
import compassites.com.mynotesappusingsqlite.R;

/**
 * Created by ramakrishna on 3/5/18.
 */

public class NotesTakerAdapter extends RecyclerView.Adapter<NotesTakerAdapter.MyNoteTakerViewHolder> {

    List<NoteDataClass> noteDataClassList;
    Context context;

    public NotesTakerAdapter(List<NoteDataClass> noteDataClassList, Context context) {
        this.noteDataClassList = noteDataClassList;
        this.context = context;
    }

    @Override
    public MyNoteTakerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                     .inflate(R.layout.individual_note_layout, parent, false);

        MyNoteTakerViewHolder myNoteTakerViewHolder = new MyNoteTakerViewHolder(itemView);
        return myNoteTakerViewHolder;
    }

    public void update(List<NoteDataClass> tempNoteDataClassList) {
        noteDataClassList = tempNoteDataClassList;
        notifyDataSetChanged(); //this updates the adapter..so it is necessary
    }

    public class MyNoteTakerViewHolder extends RecyclerView.ViewHolder{

        TextView noteTitle, noteContent;
        ItemClickListener itemClickListener;
        View individual_note_row;
        View deleteNote;

        public MyNoteTakerViewHolder(View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteContent = itemView.findViewById(R.id.note_content);
            individual_note_row = itemView.findViewById(R.id.note_relative_layout);
            deleteNote = itemView.findViewById(R.id.deleteNote);

            itemClickListener = new ItemClickListener(null,this);
            individual_note_row.setOnClickListener(itemClickListener);
            deleteNote.setOnClickListener(itemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(MyNoteTakerViewHolder holder, int position) {
        holder.noteTitle.setText(noteDataClassList.get(position).getNoteTitle());
        holder.noteContent.setText(noteDataClassList.get(position).getNoteContent());
        holder.itemClickListener.noteDataClass = noteDataClassList.get(position);

        if (position% 7 == 0)
            holder.individual_note_row.setBackgroundColor(context.getResources().getColor(R.color.color1));
        else if (position% 6 == 0)
            holder.individual_note_row.setBackgroundColor(context.getResources().getColor(R.color.color2));
        else if (position% 5 == 0)
            holder.individual_note_row.setBackgroundColor(context.getResources().getColor(R.color.color3));
        else if (position% 4 == 0)
            holder.individual_note_row.setBackgroundColor(context.getResources().getColor(R.color.color4));
        else if (position% 3 == 0)
            holder.individual_note_row.setBackgroundColor(context.getResources().getColor(R.color.color5));
        else if (position% 2 == 0)
            holder.individual_note_row.setBackgroundColor(context.getResources().getColor(R.color.color6));
        else if (position% 1 == 0)
            holder.individual_note_row.setBackgroundColor(context.getResources().getColor(R.color.color7));
    }

    @Override
    public int getItemCount() {
        return noteDataClassList.size();
    }

    class ItemClickListener implements View.OnClickListener
    {
        NoteDataClass noteDataClass;
        MyNoteTakerViewHolder myNoteTakerViewHolder;

        ItemClickListener(NoteDataClass noteDataClass, MyNoteTakerViewHolder myNoteTakerViewHolder)
        {
            this.myNoteTakerViewHolder = myNoteTakerViewHolder;
            this.noteDataClass = noteDataClass;
        }

        @Override
        public void onClick(View v) {

            View individualView = myNoteTakerViewHolder.individual_note_row;
            View deleteNoteIcon = myNoteTakerViewHolder.deleteNote;

            if (v.getId() ==individualView.getId() ) {
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra("noteId", noteDataClass.getNoteId());
                intent.putExtra("noteTitle", noteDataClass.getNoteTitle());
                intent.putExtra("noteContent", noteDataClass.getNoteContent());
                context.startActivity(intent);
            }

            else if (v.getId() == deleteNoteIcon.getId())
            {
                DataBaseHelper dataBaseHelper = DataBaseHelper.getDatabaseHelperInstance(context);

                if (dataBaseHelper.deleteNote(noteDataClass) >0) {
                    Toast.makeText(context, "Delete successful", Toast.LENGTH_SHORT).show();
                    MainActivity mainActivity = new MainActivity();

                    noteDataClassList.remove(noteDataClass);
                    notifyDataSetChanged();
                }
                else
                    Toast.makeText(context, "Could not delete the note...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
