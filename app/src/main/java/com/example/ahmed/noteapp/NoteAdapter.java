package com.example.ahmed.noteapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ahmed on 1/17/2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteAdapterViewHolder> {

    private String[] Titles, Dates;
    private final NoteAdapterAdapterOnClickHandler mClickHandler;

    public NoteAdapter(NoteAdapterAdapterOnClickHandler clickHandler){

        mClickHandler = clickHandler;
    }
    public interface NoteAdapterAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    @Override
    public NoteAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layout = R.layout.note_add_layout;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layout, viewGroup, false);
        return new NoteAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapterViewHolder holder, int position) {
        holder.title.setText(Titles[position]);
        holder.date.setText(Dates[position]);
    }


    @Override
    public int getItemCount() {
        if(Titles == null || Dates == null) return 0;
        return Titles.length;
    }
    public class NoteAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView title, date;

        public NoteAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title_shown);
            date = (TextView) itemView.findViewById(R.id.date_shown);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String title = Titles[position];
            mClickHandler.onClick(title);
        }
    }

    public void setTitles(String[] titles) {
        Titles = titles;
    }

    public void setDates(String[] dates) {
        Dates = dates;
    }

}

