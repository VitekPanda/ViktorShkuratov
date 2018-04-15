package viktorsever.viktorshkuratov.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import viktorsever.viktorshkuratov.Adapter.StockAdapter;
import viktorsever.viktorshkuratov.ApiController;
import viktorsever.viktorshkuratov.R;
import viktorsever.viktorshkuratov.model.Stock;

public class StockController {
    ApiController apic;
    RecyclerView stockView;
    List<Stock> stocks;
    ProgressBar progressBar;
    Context mContext;
    private Handler mHandler = new Handler();


    public StockController (RecyclerView stockView, ProgressBar progressBar, Context context) {
        apic = ApiController.self();
        this.stockView = stockView;
        this.progressBar = progressBar;
        mContext = context;
    }

    //Запрос акций
    public void getStocks () {
        RequestParams params = new RequestParams();
        apic.get(mContext.getString(R.string.url), params, new AsyncHttpResponseHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("status code ", String.valueOf(statusCode));
                if (statusCode == 200) {
                    try {
                        onRequestSuccess(responseBody);
                    } catch (JSONException e) {
                        Log.e("JSONException ", String.valueOf(e));
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        Log.e("UnsupportedEncodingException ", String.valueOf(e));
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFail ", String.valueOf(error));
                Log.e("onFail statusCode ", String.valueOf(statusCode));
            }
        });
    }

    //Обработка полученого ответа
    private void onRequestSuccess (byte[] responseBody) throws JSONException, UnsupportedEncodingException {
        String responseString = new String(responseBody, "UTF-8");
        stocks = new ArrayList<>();
        JSONObject json = new JSONObject(responseString);
        JSONArray stock = json.getJSONArray("stock");
        Log.e("size ", String.valueOf(stock.length()));
        for (int i = 0; i < stock.length(); i++) {
            String name = stock.getJSONObject(i).getString("name");
            String amount = String.format("%.2f", Double.parseDouble(stock.getJSONObject(i).getJSONObject("price").getString("amount")));
            String volume = stock.getJSONObject(i).getString("volume");
            stocks.add(new Stock(name,volume,amount));
        }
        StockAdapter adapter = new StockAdapter(stocks);
        Log.e("stock_size ", String.valueOf(stocks.size()));
        stockView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        mHandler.postDelayed(get_stocks,15000);
    }

    private Runnable get_stocks = new Runnable() {
        public void run() {
            if (isOnline()) {
                getStocks();
            }
        }
    };
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
