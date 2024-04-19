package com.example.appshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshop.models.Loai;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoaiAdapter extends RecyclerView.Adapter<LoaiAdapter.LoaiHolder>{
    private Context mContext;
    private ArrayList<Loai> mListLoai;

    public LoaiAdapter(Context mContext, ArrayList<Loai> mListLoai) {
        this.mContext = mContext;
        this.mListLoai = mListLoai;
    }

    @NonNull
    @Override
    public LoaiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rcv_item_loai, parent, false);
        return new LoaiAdapter.LoaiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiHolder holder, int position) {
        //get the position
        Loai modelLoai = mListLoai.get(position);

        Picasso.get().load(modelLoai.getHinhanh()).into(holder.Hinhanh);
        holder.Loai.setText(modelLoai.getLoai());

        //Xử lý bấm vào các loại sp
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra("Loai",mListLoai.get(position).getLoai());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListLoai.size();
    }

    public class LoaiHolder extends RecyclerView.ViewHolder{
        private CircleImageView Hinhanh;
        private TextView Loai;
        public LoaiHolder(@NonNull View itemView) {
            super(itemView);
            Hinhanh = (CircleImageView) itemView.findViewById(R.id.imgLoai);
            Loai = (TextView) itemView.findViewById(R.id.Loaiid);
        }
    }

}
