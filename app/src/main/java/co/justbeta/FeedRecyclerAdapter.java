package co.justbeta;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.justbeta.justbeta.R;
import co.justbeta.models.Product;

/**
 * Created by barnabas on 10/12/15
 */
public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.FeedRecyclerViewHolder> {
    List<Product> mProducts;

    public void setProducts(List<Product> products) {
        this.mProducts = products;
        this.notifyDataSetChanged();
    }

    @Override
    public FeedRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_product_feed_item, parent, false);

        return new FeedRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedRecyclerViewHolder holder, int position) {
        if (mProducts != null) {
            holder.mProductNameTV.setText(mProducts.get(position).getTitle());
            holder.mMakerNameTV.setText(mProducts.get(position).getEmail());
        }
    }

    @Override
    public int getItemCount() {
        return (mProducts == null) ? 0 : mProducts.size();
    }

    public static class FeedRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView mProductNameTV;
        private final TextView mMakerNameTV;
        private final CardView cardView;

        public FeedRecyclerViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.content_product_feed);
            mProductNameTV = (TextView) itemView.findViewById(R.id.content_product_name);
            mMakerNameTV = (TextView) itemView.findViewById(R.id.content_product_maker);
        }
    }
}
