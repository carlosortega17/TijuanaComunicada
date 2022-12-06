package com.itt.tijuanacomunicada.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.itt.tijuanacomunicada.MapsActivity;
import com.itt.tijuanacomunicada.R;
import com.itt.tijuanacomunicada.models.BachesModel;

public class BacheAdapter extends FirestoreRecyclerAdapter<BachesModel, BacheAdapter.ViewHolder> {
    private Context context;

    public BacheAdapter(@NonNull FirestoreRecyclerOptions<BachesModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BacheAdapter.ViewHolder holder, int position, @NonNull BachesModel model) {
        try {
            holder.lblLongitud.setText("Longitud: " + model.getLatitud());
            holder.lblLatitud.setText("Latitud: " + model.getLongitud());
            byte[] decodedBytes = Base64.decode(
                    model.getPhoto().substring(model.getPhoto().indexOf(",") + 1),
                    Base64.DEFAULT
            );
            Bitmap bmp = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            holder.imgHole.setImageBitmap(bmp);
            holder.imgHole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = String.format("geo:%s,%s",model.getLongitud(),model.getLatitud());
                    //Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(uri));
                    Intent intent = new Intent(v.getContext(),MapsActivity.class);
                    intent.putExtra("LONGITUD",model.getLongitud());
                    intent.putExtra("LATITUD",model.getLatitud());
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            //Toast.makeText(context, "No se pudo cargar la informacion", Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
    @Override
    public BacheAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_hole_single, parent, false);
        context = parent.getContext();
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
