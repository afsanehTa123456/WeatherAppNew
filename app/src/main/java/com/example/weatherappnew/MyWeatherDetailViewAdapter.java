package com.example.weatherappnew;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyWeatherDetailViewAdapter extends RecyclerView.Adapter<MyWeatherDetailViewAdapter.ViewHolder> {
    private List<DetailWeather> mData;
    private LayoutInflater mInflater;
    private MyWeatherViewAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    MyWeatherDetailViewAdapter(Context context, List<DetailWeather> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public MyWeatherDetailViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_show_seven_day_row, parent, false);
        return new MyWeatherDetailViewAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MyWeatherDetailViewAdapter.ViewHolder holder, int position) {
        Log.d("varandtestinjam","varandtestinjam");
        String MPoster = mData.get(position).txtdeg;
        holder._txtdeg.setText(MPoster);
        String Mdayofweek = mData.get(position).dayofweek;
        holder._txtdayofweek.setText(Mdayofweek);
        String Mtxtdes = mData.get(position).txtdes;
        holder._txtdes.setText(Mtxtdes);
        //holder.myimg.setImageDrawable(R.drawable.ic_launcher_background);https://cdn.theatlantic.com/assets/media/img/mt/2017/10/Pict1_Ursinia_calendulifolia/lead_720_405.jpg?mod=1533691909
        //Picasso.get().load(mData.get(position).imgShow).into(holder._imgShow);
        switch (mData.get(position).imgShow)
        {
            case "01d":
                holder._imgShow.setImageResource(R.drawable.one);
                break;
            case "02d":
                holder._imgShow.setImageResource(R.drawable.two);
                break;
            case "03d":
                holder._imgShow.setImageResource(R.drawable.three);
                break;
            case "04d":
                holder._imgShow.setImageResource(R.drawable.four);
                break;
            case "09d":
                holder._imgShow.setImageResource(R.drawable.nine);
                break;
            case "10d":
                holder._imgShow.setImageResource(R.drawable.ten);
                break;
            case "11d":
                holder._imgShow.setImageResource(R.drawable.eleven);
                break;
            case "13d":
                holder._imgShow.setImageResource(R.drawable.thrtin);
                break;
            default:
                holder._imgShow.setImageResource(R.drawable.one);
                break;
        }
        //Log.d("varandimg",mData.get(position).imgShow);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        Log.d("varandimg", String.valueOf(mData.size()));
        return mData.size();

    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView _txtdeg,_txtdayofweek,_txtdes;
        ImageView _imgShow;
        ViewHolder(View itemView) {
            super(itemView);

            _txtdes = itemView.findViewById(R.id.txtdes);
            _txtdayofweek = itemView.findViewById(R.id.txtdayofweek);
            _txtdeg = itemView.findViewById(R.id.txtdeg);
            _imgShow = itemView.findViewById(R.id.imgShow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).txtdes;
    }

    // allows clicks events to be caught
    void setClickListener(MyWeatherViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
