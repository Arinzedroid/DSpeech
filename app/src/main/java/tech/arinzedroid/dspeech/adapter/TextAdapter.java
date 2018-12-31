package tech.arinzedroid.dspeech.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import tech.arinzedroid.dspeech.R;
import tech.arinzedroid.dspeech.interfaces.ItemClickedListener;
import tech.arinzedroid.dspeech.viewholders.TextViewHolder;

public class TextAdapter extends RecyclerView.Adapter<TextViewHolder> {

    private ArrayList<String> text;
    private ItemClickedListener itemClickedListener;
    private int pos = -1 ;
    private boolean show = false;

    public TextAdapter(List<String> text, ItemClickedListener itemClickedListener){
        this.text = new ArrayList<>(text);
        this.itemClickedListener = itemClickedListener;
    }

    public void addTexts(String newText){
        int size = text.size();
        this.text.add(size,newText);
        notifyItemInserted(size);
    }

    public void clearContents(){
        text.clear();
        notifyDataSetChanged();
    }

    public void showProgressBar(int position, boolean show){
        this.pos = position;
        this.show = show;
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_items,parent,false);
        return new TextViewHolder(view,itemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        isError(holder, position);
        holder.DisplayText.setText(text.get(position));
        if(show && position == pos){
            holder.progressBar.setVisibility(View.VISIBLE);
//            show =false;
        }else {
            holder.progressBar.setVisibility(View.GONE);
            isError(holder,position);
        }
    }

    private void isError(@NonNull TextViewHolder holder, int position) {
        if(text.get(position).equalsIgnoreCase("error..."))
            holder.refreshButton.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return text.size();
    }
}
