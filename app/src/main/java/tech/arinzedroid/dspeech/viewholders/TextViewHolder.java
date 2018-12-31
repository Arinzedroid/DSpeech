package tech.arinzedroid.dspeech.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.arinzedroid.dspeech.R;
import tech.arinzedroid.dspeech.interfaces.ItemClickedListener;

public class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @BindView(R.id.display_text)
    public TextView DisplayText;
    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;
    @BindView(R.id.refresh_button)
    public ImageButton refreshButton;

    private ItemClickedListener itemClickedListener;

    public TextViewHolder(View itemView,ItemClickedListener itemClickedListener) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.itemClickedListener = itemClickedListener;
        refreshButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.refresh_button){
            itemClickedListener.onRefreshButtonClicked(this.getLayoutPosition());
        }
    }
}
