package com.example.appshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshop.models.Tintuc;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TintucAdapter extends RecyclerView.Adapter<TintucAdapter.TintucHolder>{

    private Context mContext;
    private ArrayList<Tintuc> mListTintuc;

    public TintucAdapter(Context mContext, ArrayList<Tintuc> mListTintuc) {
        this.mContext = mContext;
        this.mListTintuc = mListTintuc;
    }

    @NonNull
    @Override
    public TintucHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rcv_item_tintuc, parent, false);
        return new TintucHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TintucHolder holder, int position) {
        //get the position
        Tintuc modelTintuc = mListTintuc.get(position);
//        if(tintuc == null){
//            return;
//        }
        Picasso.get().load(modelTintuc.getTintucimg()).into(holder.tintucimg);
        holder.Tieude.setText(modelTintuc.getTieude());
        holder.motangan.setText(modelTintuc.getMotangan());
    }

    @Override
    public int getItemCount() {
        return mListTintuc.size();
    }

    public class TintucHolder extends RecyclerView.ViewHolder{
        private ImageView tintucimg;
        private TextView Tieude, motangan;
        public TintucHolder(@NonNull View itemView) {
            super(itemView);

            Tieude = (TextView) itemView.findViewById(R.id.txtTieude);
            motangan = (TextView) itemView.findViewById(R.id.txtMotangan);
            tintucimg = (ImageView) itemView.findViewById(R.id.imgTintuc);
        }
    }

}
