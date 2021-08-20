package com.example.rayons.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rayons.Model.DataModel;
import com.example.rayons.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private Context Cotx;
    private List<DataModel> Data;

    public AdapterData(Context ctx, List<DataModel> data) {
        this.Cotx = ctx;
        this.Data = data;
    }

    @NonNull
    @NotNull
    @Override
    public HolderData onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View Layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_kapal, parent,false);
        HolderData holder = new HolderData(Layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HolderData holder, int position) {
        DataModel Model = Data.get(position);
        holder.IdKapal.setText(String.valueOf(Model.getId()));
        holder.NamaKapal.setText(Model.getNama_kapal());
        holder.KelasPelayaran.setText(Model.getKelas_pelayaran());

        Picasso.get()
                .load(Model.getGambar())
                .into(holder.GambarKapal);
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView NamaKapal, KelasPelayaran, IdKapal;
        CircleImageView GambarKapal;

        public HolderData(@NonNull @NotNull View itemView) {
            super(itemView);

            IdKapal = itemView.findViewById(R.id.id_kapal);
            NamaKapal = itemView.findViewById(R.id.nama_kapal);
            KelasPelayaran = itemView.findViewById(R.id.kelas_pelayaran);
            GambarKapal = itemView.findViewById(R.id.gambar_kapal);
        }
    }
}
