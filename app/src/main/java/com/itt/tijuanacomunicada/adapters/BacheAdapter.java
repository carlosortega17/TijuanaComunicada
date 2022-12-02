package com.itt.tijuanacomunicada.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.itt.tijuanacomunicada.R;
import com.itt.tijuanacomunicada.models.BachesModel;

public class BacheAdapter extends FirestoreRecyclerAdapter<BachesModel, BacheAdapter.ViewHolder> {
    public BacheAdapter(@NonNull FirestoreRecyclerOptions<BachesModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BacheAdapter.ViewHolder holder, int position, @NonNull BachesModel model) {
        holder.lblLongitud.setText("Longitud: " + model.getLongitud());
        holder.lblLatitud.setText("Latitud: " + model.getLatitud());
        try {
            byte[] decodedBytes = Base64.decode(
                    model.getPhoto().substring(model.getPhoto().indexOf(",") + 1),
                    Base64.DEFAULT
            );
            Bitmap bmp = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            holder.imgHole.setImageBitmap(bmp);
        } catch (Exception e) {
            // TODO
        }
    }

    @NonNull
    @Override
    public BacheAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout.hole_item, parent, false);
        return new ViewHolder(viewRoot);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblLongitud;
        TextView lblLatitud;
        ImageView imgHole;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblLatitud = itemView.findViewById(R.id.lblLatitud);
            lblLongitud = itemView.findViewById(R.id.lblLongitud);
            imgHole = itemView.findViewById(R.id.imgHole);
        }
    }
}
