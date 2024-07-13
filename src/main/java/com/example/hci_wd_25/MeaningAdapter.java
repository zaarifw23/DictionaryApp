package com.example.hci_wd_25;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_wd_25.databinding.MeaningRecyclerRowBinding;

import java.util.List;

public class MeaningAdapter extends RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder> {

    private List<Meaning> meaningList;

    public MeaningAdapter(List<Meaning> meaningList) {
        this.meaningList = meaningList;
    }

    @Override
    public MeaningViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MeaningRecyclerRowBinding binding = MeaningRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MeaningViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MeaningViewHolder holder, int position) {
        holder.bind(meaningList.get(position));
    }

    @Override
    public int getItemCount() {
        return meaningList.size();
    }

    public void updateNewData(List<Meaning> newMeaningList) {
        meaningList = newMeaningList;
        notifyDataSetChanged();
    }

    class MeaningViewHolder extends RecyclerView.ViewHolder {
        private MeaningRecyclerRowBinding binding;

        MeaningViewHolder(MeaningRecyclerRowBinding binding) {
            super(binding.getRoot());  // Pass the root view to the superclass constructor
            this.binding = binding;
        }

        void bind(Meaning meaning) {
            binding.partOfSpeechTextview.setText(meaning.getPartOfSpeech());

            // Convert the list of definitions to a string
            StringBuilder definitionsStr = new StringBuilder();
            for (Definition definition : meaning.getDefinitions()) {
                definitionsStr.append(definition.getDefinition()).append("\n");
            }
            binding.definitionsTextview.setText(definitionsStr.toString());

            // Set the synonyms and antonyms
            if (meaning.getSynonyms() != null && !meaning.getSynonyms().isEmpty()) {
                binding.synonymsTextview.setText(String.join(", ", meaning.getSynonyms()));
            } else {
                binding.synonymsTextview.setText("No synonyms available");
            }

            if (meaning.getAntonyms() != null && !meaning.getAntonyms().isEmpty()) {
                binding.antonymsTextview.setText(String.join(", ", meaning.getAntonyms()));
            } else {
                binding.antonymsTextview.setText("No antonyms available");
            }
        }

    }
}
