package com.example.myaplictionexamplemodel3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
public MyClickListener myClickListener;
private List<Note> notes=new ArrayList<>();

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_items, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
     Note currentNote=notes.get(position);
     holder.textViewTitle.setText(currentNote.getTitle());
     holder.textViewDescription.setText(currentNote.getDescription());
     holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    public void setNotes(List<Note> note){
        this.notes=note;
       // notifyDataSetChanged();
    }
    public Note getAllnote(int position){
        return notes.get(position);
    }

    public class NoteHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_priority)
        TextView textViewPriority;
        @BindView(R.id.text_view_title)
        TextView textViewTitle;
        @BindView(R.id.text_view_description)
        TextView textViewDescription;
        private View view;
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            ButterKnife.bind(this,view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int postion=getAdapterPosition();
                    if(myClickListener!=null&&postion!=RecyclerView.NO_POSITION){
                        myClickListener.onClick(notes.get(postion));
                    }
                }
            });
        }
    }
    public interface MyClickListener{
        void onClick(Note note);
    }
    public void setOnClicccccck(MyClickListener myClickListener){
        this.myClickListener=myClickListener;
    }
}
