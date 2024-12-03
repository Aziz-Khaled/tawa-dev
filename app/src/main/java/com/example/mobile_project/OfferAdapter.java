package com.example.mobile_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {
    private List<Offer> offerList;

    public OfferAdapter(List<Offer> offerList) {
        this.offerList = offerList;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer, parent, false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        Offer offer = offerList.get(position);
        holder.tvTitle.setText(offer.getTitle());
        holder.tvDescription.setText(offer.getDescription());
        holder.tvName.setText(offer.getName());
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public static class OfferViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvName;

        public OfferViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.OfferTitle);
            tvDescription = itemView.findViewById(R.id.tvOfferDescription);
            tvName = itemView.findViewById(R.id.Name);
        }
    }
}
