package com.example.appshop;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    Context context;
    List<SanPham> list;
    public ProductAdapter(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_item_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        //Có thể dùng thư viện Picasso hoặc Glide để load ảnh bằng URL
//        Picasso.get().load(list.get(position).getHinhAnh()).into(holder.imgItemSp);

        Glide.with(context).load(list.get(position).getHinhAnh()).into(holder.imgItemSp);
        holder.nameItemSp.setText(list.get(position).getTenSP());
        holder.priceItemSp.setText("Price : " + String.valueOf(list.get(position).getGia()) + "$");
        holder.quantity.setText("Quantity :" + list.get(position).getQuantity());

        //Xử lý sự kiện cho Detail Product
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailProductActivity.class);
                i.putExtra("detail", list.get(holder.getPosition()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
//        private Button btnDatMua;
        private ImageButton btnThemGioHang, btnThemFav;
        private ImageView imgItemSp;
        private TextView nameItemSp, priceItemSp, quantity;
        private CardView carditem;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

//            btnDatMua = (Button) itemView.findViewById(R.id.btnDatMua);
            btnThemGioHang = (ImageButton) itemView.findViewById(R.id.btnThemGioHang);
            btnThemFav = (ImageButton) itemView.findViewById(R.id.btnThemFav);
            imgItemSp = (ImageView) itemView.findViewById(R.id.imgitemsp);
            nameItemSp = (TextView) itemView.findViewById(R.id.nameitemsp);
            priceItemSp = (TextView) itemView.findViewById(R.id.priceitemsp);
            carditem = (CardView) itemView.findViewById(R.id.carditem);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
        }
    }
}
