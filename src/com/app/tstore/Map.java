package com.app.tstore;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


	public class Map extends FragmentActivity implements OnMarkerClickListener {

		private Activity activity = this;
		
		private final LatLng STARTING_POINT=new LatLng(45.464711, 9.188736);

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_map);
			
			
			GoogleMap map=((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			map.setOnMarkerClickListener(this);
				
			//zoom predefinito 5
			//map.moveCamera(CameraUpdateFactory.newLatLngZoom(STARTING_POINT, 5));
			//lieve zoom di durata 2 sec
			//map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
			//marker di default
			//map.addMarker(new MarkerOptions().position(STARTING_POINT));
			//personalizza img marker
			//map.addMarker(new MarkerOptions().position(STARTING_POINT).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
			//label del marker
			map.addMarker(new MarkerOptions()
			.position(STARTING_POINT)
			.title("Il mio pin")
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
			);	
			
			map.setMyLocationEnabled(true);  // centra sulla mia pos.
			//map.setTrafficEnabled(true);     // mostra stato traffico
			
			//maggiori dettagli posizionamento mappa
			CameraPosition cameraPosition = new CameraPosition.Builder()
			.target(STARTING_POINT)
			.zoom(5)
			.bearing(0)           // Orientamento verso Est
			.tilt(30)              // Inclinazione
			.build();
			map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			
		}
		
		//marker cliccabile
		@Override
		 public boolean onMarkerClick(Marker marker) {
		   //marker.setSnippet("Lat: " + (Math.round(marker.getPosition().latitude * 100.0) / 100.0) + " Long: "+ (Math.round(marker.getPosition().longitude * 100.0) / 100.0));
		   marker.setTitle("TechStore");
		   marker.setSnippet("Sede di Milano");
		   return false;
		 }
	}
