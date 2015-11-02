package com.app.tstore;


import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class Map extends FragmentActivity {

	private Activity activity = this;

	private static final String NCIVICO = "NCivico";
	private static final String CAP = "cap";
	private static final String CITTA = "città";
	private static final String INDIRIZZO = "indirizzo";
	private static final String LATITUDINE = "latitudine";
	private static final String LONGITUDINE = "longitudine";
	private static final String PROVINCIA = "provincia";
	private static final String TELEFONO = "telefono";

	private GoogleMap map;
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		String url = MainActivity.url + "jsonStore.action";

		new ProgressTask().execute(url);

		map=((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);

		CameraUpdate center=
				CameraUpdateFactory.newLatLng(new LatLng(41.9102415,12.3959122));
		CameraUpdate zoom=CameraUpdateFactory.zoomTo(5);

		map.moveCamera(center);
		map.animateCamera(zoom);


	}

	private class ProgressTask extends AsyncTask<String, Void, JSONArray> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(context);
			dialog.setMessage("Progress start");
			dialog.setCancelable(false);
			dialog.show();
		}		

		@Override
		protected JSONArray doInBackground(String... params) {
			JsonDownloader jParser = new JsonDownloader();
			return jParser.getJSONFromUrl(params[0]);

		}


		@Override
		protected void onPostExecute(JSONArray json) {
			if(dialog.isShowing())
				dialog.dismiss();
			if (json == null) {
				Toast.makeText(context, "Download error", Toast.LENGTH_LONG).show();
				return;				
			}

			for(int i = 0; i < json.length(); i++) {
				try {
					JSONObject c = json.getJSONObject(i);	
					String ncivico = c.getString(NCIVICO);
					String cap = c.getString(CAP);
					String città = c.getString(CITTA);
					String indirizzo = c.getString(INDIRIZZO);
					int latitudine = c.getInt(LATITUDINE);
					int longitudine = c.getInt(LONGITUDINE);
					String provincia = c.getString(PROVINCIA);
					String telefono = c.getString(TELEFONO);

					map.addMarker(new MarkerOptions()
					.position(new LatLng(latitudine, longitudine))
					.title("TechStore " + provincia + "\n" + indirizzo + ", " + ncivico + " " + città + " " + telefono)
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
							);	

				} catch(JSONException e) {
					e.printStackTrace();
				}
			}

			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();

			Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

			if (location != null)
			{    
				CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user)
				.zoom(5)
				.bearing(0)           // Orientamento verso Est
				.tilt(30)              // Inclinazione
				.build();
				map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			}
		}
	}	
}