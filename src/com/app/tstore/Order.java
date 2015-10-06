package com.app.tstore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


import java.util.Date;
import java.util.List;

import org.w3c.dom.Text;

import com.app.tstore.Server.OnQueryEseguita;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.Contacts.Data;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;




public class Order extends ListActivity implements OnClickListener, OnItemClickListener {

	private Activity activity = this;
	private Handler handler = new Handler();
	private String et_Username;
	private String et_Password;
	private int utente_id;
	private List<Integer> ordersid = new ArrayList <Integer>();

	private Orders orders;


	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	ArrayList<String> listItems=new ArrayList<String>();

	//DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
	ArrayAdapter<String> adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		setListAdapter(adapter);



		if (savedInstanceState == null) {
			Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			et_Username = bundle.getString("Nome_utente");
			utente_id = bundle.getInt("Id_utente");
		} else {
			et_Username = savedInstanceState.getString("Nome_utente");
			utente_id = savedInstanceState.getInt("Id_utente");
		}

		//	((TextView)findViewById(R.id.username)).setText(" Welcome " + et_Username );
		Log.d("DT_ID_utette", String.valueOf(utente_id) );	


		work();

	}


	private void work() {final ProgressDialog progress_dialog = new ProgressDialog(activity);
	progress_dialog.setMessage("Stiamo contattando il server...");
	progress_dialog.setCancelable(false);
	progress_dialog.show();

	String query = "";
	query += "SELECT * FROM ordine INNER JOIN ordine_ha_configurazione ON ordine.idordine=ordine_ha_configurazione.idordine "
			+ "INNER JOIN configurazione ON ordine_ha_configurazione.idconfig=configurazione.idconfig "
			+ "INNER JOIN prodotto ON configurazione.idprodotto=prodotto.idprodotto "
			+ "WHERE ordine.idutente = "+ utente_id + ";";


	final Server connessione = new Server();


	OnQueryEseguita onQueryEseguita = new OnQueryEseguita() {									

		@Override
		public void onQueryEseguita(final List<List<String>> risultato) {
			Log.d("DT", "1");
			if (risultato != null) {
				Log.d("DT", "2");

				boolean has_order = risultato.size() > 0;
				Log.d("DT_has_order", String.valueOf(has_order));

				if (has_order){
					//l'utente ha un ordine nel db
					Log.d("DT_dati_ordine", "has order");

					for (int i=0; i<risultato.size(); i++)
					{
						Log.d("DT_risultatoget", String.valueOf(risultato.get(i)));

						List<String> riga = risultato.get(i);

						Log.d("DT_riga", String.valueOf(riga.get(i)));

						orders = new Orders((riga.get(8)), Integer.parseInt(riga.get(3)), Integer.parseInt(riga.get(0)));
						//TODO get8 nome_prod  get3 idutente  get0 idordine
						Log.d("NOMEPROD", String.valueOf(riga.get(8)));
						Log.d("IDUTENTE", String.valueOf(riga.get(3)));
						Log.d("IDORDINE", String.valueOf(riga.get(0)));


						listItems.add(riga.get(8));
						//IDORDINE
						ordersid.add(Integer.parseInt((riga.get(0))));
						adapter.notifyDataSetChanged();
					}

					//utente_id = orders.getIdutente(); 
					//ordine_id = orders.getIdordine();

					getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

							//TODO riconoscere il listitem premuto salvando id_ordine per usarlo per la query	
							int ordine_id = ordersid.get((int) id);
							final Bundle bundle = new Bundle();
							//bundle.putString("Nome_utente",et_Username.toString());
							//bundle.putInt("Id_utente", utente_id);
							bundle.putInt("Id_ordine", ordine_id);
							//Log.d("oraID_UTENTE", String.valueOf(utente_id));	
							final Intent intent;

							intent = new Intent(activity, Order_details.class);							

							intent.putExtras(bundle);
							startActivity(intent);
						}
					});

				} else {
					// non esiste nel db
					//	Log.d("DT_non_esiste", String.valueOf(has_order) );

					Toast.makeText(activity, "non hai ordini effettuati", Toast.LENGTH_SHORT).show();	
				}

			} else {

				new AlertDialog.Builder(activity)
				.setCancelable(false)
				.setTitle("IL SERVER NON RISPONDE")
				.setMessage("Server non disponibile, controlla la connessione o contatta l'amministratore")
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						// continue with delete
					}
				})		
				.setNeutralButton("RIPROVA", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						work();
					}
				})
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();							
			}

			progress_dialog.dismiss();
		}
	};

	connessione.eseguiQuery(query, onQueryEseguita, handler);
	Log.d("DT", "5");		
	}

	@Override
	public void onClick(View arg0) {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}