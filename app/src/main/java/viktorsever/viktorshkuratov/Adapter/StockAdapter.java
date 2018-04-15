package viktorsever.viktorshkuratov.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import viktorsever.viktorshkuratov.R;
import viktorsever.viktorshkuratov.model.Stock;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {


    public static class StockViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView stockName;
        TextView stockAmount;
        TextView stockVolume;

        StockViewHolder (View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            stockName = (TextView)itemView.findViewById(R.id.stockName);
            stockAmount = (TextView)itemView.findViewById(R.id.stockAmount);
            stockVolume = (TextView) itemView.findViewById(R.id.stockVolume);
        }
    }

    List<Stock> stocks;

    public StockAdapter(List<Stock> stocks){
        this.stocks = stocks;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, int position) {
        holder.stockName.setText(stocks.get(position).getName());
        holder.stockAmount.setText(stocks.get(position).getAmount());
        holder.stockVolume.setText(stocks.get(position).getVolume());
    }
    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        StockViewHolder pvh = new StockViewHolder(v);
        return pvh;
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }
}
