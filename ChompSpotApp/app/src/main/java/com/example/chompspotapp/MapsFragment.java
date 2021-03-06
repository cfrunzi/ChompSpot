package com.example.chompspotapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for(int i = 0; i <= (MainActivity.entries); i++) {
                LatLng sydney = new LatLng(MainActivity.cache[i].getLatitude(), MainActivity.cache[i].getLongitude());
                MarkerOptions marker = new MarkerOptions().position(sydney).title(MainActivity.cache[i].getName());
                String busy = MainActivity.cache[i].getBusy();

                Bitmap bitMap;

                if (busy == "Busy") {
                    bitMap = BitmapFactory.decodeResource(getResources(), R.drawable.busy);
                } else if (busy == "Moderate") {
                    bitMap = BitmapFactory.decodeResource(getResources(), R.drawable.moderate);
                } else{
                    bitMap = BitmapFactory.decodeResource(getResources(), R.drawable.slow);
                }

                //Scaling map markers
                double mod = .9;
                int width =(int) (mod*90);
                int height = (int) (mod*149);

                Bitmap scaledMap = Bitmap.createScaledBitmap(bitMap, width, height, false);
                marker.icon(BitmapDescriptorFactory.fromBitmap(scaledMap));
                googleMap.addMarker(marker);
                LatLng ltlng = new LatLng(MainActivity.cache[i].getLatitude(),MainActivity.cache[i].getLongitude());
                builder.include(ltlng);

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        int index = 0;
                        for (int j = 0; j < MainActivity.cache.length; j++){
                            if (marker.getPosition().equals(new LatLng(MainActivity.cache[j].getLatitude(),
                                    MainActivity.cache[j].getLongitude()))){
                                index = j;
                                break;
                            }
                        }
                        mfListener.goToMap(MainActivity.cache[index]);
                        return false;
                    }
                });
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
            LatLngBounds bounds = builder.build();
            int padding = 0; // offset from edges of the map in pixels
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width,height, padding);

            googleMap.moveCamera(cu);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MapOverlay.TempFragmentListener)
            mfListener = (mapFragListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement mapFragListener.");
    }

    mapFragListener mfListener;

    interface mapFragListener {
        void goToMap(Business bus);
    }
}