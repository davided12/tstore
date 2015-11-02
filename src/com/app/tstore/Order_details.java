package com.app.tstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Order_details extends ListActivity implements OnItemClickListener {

	private String username;
	private String password;
	private String idordine;

	ArrayList<HashMap<String, String>> orderslist = new ArrayList<HashMap<String,String>>();

	private Context context = this;
	//[{"data":"lun, 28 set 2015 06:47:53","foto":"\/TechStoreWA\/img\/id1.jpg","idordine":17,"nomeProd":"Asus ZH40294","prezzo":1797.0,"quantita":3,"stato":"pendente"}]
	//{"data":"mer, 14 ott 2015 03:31:01","foto":"jpg","idordine":28,"nomeProd":"test2","prezzo":30.0,"quantita":1,"stato":"pendente"}
	private static final String IDORDINE = "idordine";
	private static final String DATA = "data";
	private static final String NOMEPROD = "nomeProd";
	private static final String PREZZO = "prezzo";
	private static final String QUANTITA = "quantita";
	private static final String STATO = "stato";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_details);

		if (savedInstanceState == null) {
			Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			username = bundle.getString("USERNAME");
			password = bundle.getString("PASSWORD");
			idordine = bundle.getString("IDORDINE");
		}

		String url = MainActivity.url + "jsonOrderDetails.action?username=" + username + "&password=" + password + "&idordine=" + idordine;

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
			//"idordine":28,"nomeProd":"test2","prezzo":30.0,"quantita":1,"stato":"pendente"}

			for(int i = 0; i < json.length(); i++) {
				try {
					JSONObject c = json.getJSONObject(i);
					String idordine =  String.valueOf(c.getInt(IDORDINE));
					String data = c.getString(DATA);
					String nomeProd = c.getString(NOMEPROD);
					String prezzo = String.valueOf(c.getInt(PREZZO));
					String quantita = String.valueOf(c.getInt(QUANTITA));
					String stato = c.getString(STATO);

					HashMap<String, String> map = new HashMap<String, String>();
					map.put(IDORDINE, idordine);
					map.put(DATA, data);
					map.put(NOMEPROD, nomeProd);
					map.put(PREZZO, prezzo);
					map.put(QUANTITA, quantita);
					map.put(STATO, stato);
					orderslist.add(map);

				} catch(JSONException e) {
					e.printStackTrace();
				}
			}

			ListAdapter adapter = new SimpleAdapter(context, orderslist,
					R.layout.list_item_ordinedetails, new String[] { IDORDINE, DATA, NOMEPROD, PREZZO, QUANTITA, STATO },
					new int[] {R.id.idordine, R.id.data, R.id.nomeProd, R.id.prezzo, R.id.quantita, R.id.stato });

			setListAdapter(adapter);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {
	}
}