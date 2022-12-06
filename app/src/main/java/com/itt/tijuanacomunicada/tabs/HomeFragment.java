package com.itt.tijuanacomunicada.tabs;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itt.tijuanacomunicada.R;
import com.itt.tijuanacomunicada.models.BachesModel;
import com.itt.tijuanacomunicada.services.AuthService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class HomeFragment extends Fragment {
    private final static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private LocationManager locationManager;
    private AppCompatButton btnLocation;
    private ImageView imgBache;
    private AppCompatButton btnImage;
    private AppCompatButton btnAdd;
    private TextView lblLatitud;
    private TextView lblLongitud;
    private static final int REQUEST_LOCATION = 1;
    private String encoded = null;
    private String latitud = null;
    private String longitud = null;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_home, container, false);
        btnLocation = viewRoot.findViewById(R.id.btnLocation);
        imgBache = viewRoot.findViewById(R.id.imgBache);
        btnImage = viewRoot.findViewById(R.id.btnImage);
        btnAdd = viewRoot.findViewById(R.id.btnAdd);
        lblLatitud = viewRoot.findViewById(R.id.lblLatitud);
        lblLongitud = viewRoot.findViewById(R.id.lblLongitud);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions(new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA,
                }, REQUEST_LOCATION);
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else  {
                    GetLocationAndCamera();
                }
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 7);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (encoded != null || latitud != null || longitud != null) {
                    AddHole(longitud, latitud, encoded, AuthService.CurrentUser());
                } else {
                    Toast.makeText(getActivity(), "Necesitas todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });
        return viewRoot;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgBache.setImageBitmap(bitmap);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void GetLocationAndCamera() {
        if (
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CAMERA,
            }, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double lon = locationGPS.getLongitude();
                latitud = String.valueOf(lat);
                longitud = String.valueOf(lon);
                lblLatitud.setText("Latitud: "+latitud);
                lblLongitud.setText("Longitud: "+longitud);
            } else {
                Toast.makeText(getActivity(), "No se pudo determinar la locacion", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void ClearAll() {
        lblLatitud.setText("Latitud: 0");
        lblLongitud.setText("Longitud: 0");
        imgBache.setImageResource(0);
        longitud = null;
        latitud = null;
        encoded = null;
    }

    private void AddHole(String lo, String la, String p, String user) {
        CollectionReference holes = firebaseFirestore.collection("holes");
        BachesModel bachesModel = new BachesModel(lo, la, p, user);
        btnAdd.setEnabled(false);
        holes.add(bachesModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getActivity(), "Se agrego el bache al listado", Toast.LENGTH_LONG).show();
                btnAdd.setEnabled(true);
                ClearAll();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                btnAdd.setEnabled(true);
                ClearAll();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onStart(){
        super.onStart();
        //Log.d(TAG,"onStart() llamado");
    }

    @Override
    public void onResume(){
        super.onResume();
        //Log.d(TAG,"onResume() llamado");
    }

    @Override
    public void onStop(){
        super.onStop();
        //Log.d(TAG,"onStop() llamado");
    }

    @Override
    public void onPause(){
        super.onPause();
        //Log.d(TAG,"onStart() llamado");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //Log.d(TAG,"onDestroy() llamado");
    }
}