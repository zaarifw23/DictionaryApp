package com.example.hci_wd_25;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> word, definition;
    private DatabaseHelper myDB;
    CustomAdapter(Context context, ArrayList<String> word, ArrayList<String> definition) {
        this.context = context;
        this.word = word;
        this.definition = definition;
        this.myDB = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        // Set the text for both word and definition TextViews
//        if (holder.word != null) {
//            holder.word.setText(word.get(position));
//        } else {
//            Log.e("CustomAdapter", "Word TextView is null at position " + position);
//        }
//        if (holder.definition != null) {
//            holder.definition.setText(definition.get(position));
//        } else {
//            Log.e("CustomAdapter", "Definition TextView is null at position " + position);
//        }
//    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.wordText.setText(word.get(position));
        holder.definitionText.setText(definition.get(position));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    showDeleteDialog(currentPosition);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return word.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView wordText, definitionText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            wordText = itemView.findViewById(R.id.word_text_view);
            definitionText = itemView.findViewById(R.id.definition_text_view);
        }
    }

    private void showDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteItem(int position) {
        String wordToDelete = word.get(position);
        boolean isDeleted = myDB.deleteOneRow(wordToDelete);
        if (isDeleted) {
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
            word.remove(position);
            definition.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, word.size());
        } else {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
    }
}
