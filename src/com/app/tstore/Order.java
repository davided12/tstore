package com.app.tstore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.internal.et;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class Order extends ListActivity implements OnItemClickListener {

	private String username;
	private String password;
	

	ArrayList<HashMap<String, String>> orderslist = new ArrayList<HashMap<String,String>>();

	private Context context = this;
	private Activity activity = this;


	private static final String IDORDINE = "idordine";
	private static final String NOMEPROD = "nomeProd";
	//[{"idordine":17,"idutente":5,"nomeProd":"Asus ZH40294"}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		if (savedInstanceState == null) {
			Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			username = bundle.getString("USERNAME");
			password = bundle.getString("PASSWORD");
		}

		getListView().setOnItemClickListener(this);

		String url = MainActivity.url + "jsonOrder.action?username=" + username + "&password=" + password;
		
		new ProgressTask().execute(url);	
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
				Toast.makeText(context, "Wrong data", Toast.LENGTH_LONG).show();
				finish();
				return;				
			}			
			if (json.length() == 0) 
				Toast.makeText(context, "No orders found", Toast.LENGTH_LONG).show();	
			for(int i = 0; i < json.length(); i++) {
				try {
					JSONObject c = json.getJSONObject(i);	
					String idordine =  String.valueOf(c.getInt(IDORDINE));
					String nomeProd = c.getString(NOMEPROD);
					
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(IDORDINE, idordine);
					map.put(NOMEPROD, nomeProd);
					orderslist.add(map);
	
				} catch(JSONException e) {
					e.printStackTrace();
				}
			}
			//[{"idordine":17,"idutente":5,"nomeProd":"Asus ZH40294"}
			ListAdapter adapter = new SimpleAdapter(context, orderslist,
					R.layout.list_item_ordine, new String[] { NOMEPROD },
					new int[] { R.id.nomeprod });

			setListAdapter(adapter);

		}	
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//Toast.makeText(context, orderslist.get(position).get(NOMEPROD), Toast.LENGTH_LONG).show();
		Intent intent = new Intent();
    	intent.setClass(activity, Order_details.class);
    	intent.putExtra("USERNAME", username);
    	intent.putExtra("PASSWORD", password);
    	intent.putExtra("IDORDINE", orderslist.get(position).get(IDORDINE));
    	
    	Log.d("idordineorderjava", orderslist.get(position).get(IDORDINE));
    	
    	startActivity(intent);
	}
}