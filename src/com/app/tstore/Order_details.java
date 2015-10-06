package com.app.tstore;

import java.util.List;

import com.app.tstore.Server.OnQueryEseguita;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public class Order_details extends Activity implements OnClickListener {

	private Activity activity = this;
	private Handler handler = new Handler();
	private String et_Username;
	private String et_Password;
	private int utente_id;
	private int ordine_id;

	private Orders orders;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_details);

		TextView go_map = (TextView)findViewById(R.id.go_map);
		go_map.setOnClickListener(this);
		go_map.setText("Home Sales");



		if (savedInstanceState == null) {
			Intent intent = getIntent();
			Bundle bundle = intent.getExtras();
			et_Username = bundle.getString("Nome_utente");
			utente_id = bundle.getInt("Id_utente");
			ordine_id = bundle.getInt("Id_ordine");
		} else {
			et_Username = savedInstanceState.getString("Nome_utente");
			utente_id = savedInstanceState.getInt("Id_utente");
			ordine_id = savedInstanceState.getInt("Id_ordine");
		}

		//	((TextView)findViewById(R.id.username)).setText(" Welcome " + et_Username );
		Log.d("id_orderdetails", String.valueOf(utente_id) );	
		Log.d("idORDINE_orderdetails", String.valueOf(ordine_id) );	

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
			+ "WHERE ordine.idordine = "+ ordine_id + ";";
	//query con AND tra id utente e id ordine
	//WHERE FirstName = 'John' AND LastName = 'Smith';
	//+ "WHERE ordine.idutente = "+ utente_id + ";";
	//"WHERE ordine.idutente = "+ utente_id + "AND ordine.idordine = "+ ordine_id + ";";

	final Server connessione = new Server();


	OnQueryEseguita onQueryEseguita = new OnQueryEseguita() {									

		@Override
		public void onQueryEseguita(List<List<String>> risultato) {
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
						
						orders = new Orders(Integer.parseInt(riga.get(0)), (riga.get(1)), riga.get(2), Integer.parseInt(riga.get(3)), Integer.parseInt(riga.get(6)), riga.get(8), riga.get(9), riga.get(11), riga.get(12), riga.get(17), riga.get(19), riga.get(9));
						//                                     int idordine, String data, String stato, int idutente,                  int quantita,                 String nome_prod, String img, String prezzo, String sconto, String categoria, String descrizione, String foto)
						//get0
						TextView idordine = (TextView)findViewById(R.id.idordine);
						//get1
						TextView data = (TextView)findViewById(R.id.data);
						//get2
						TextView stato = (TextView)findViewById(R.id.stato);
						//get6
						TextView quantita = (TextView)findViewById(R.id.quantita);
						//get8
						TextView nome_prod = (TextView)findViewById(R.id.nome_prod);
						//get9
						TextView foto = (TextView)findViewById(R.id.foto);
						//get11
						TextView prezzo = (TextView)findViewById(R.id.prezzo);
						//get12
						TextView sconto = (TextView)findViewById(R.id.sconto);
						//get17
						TextView categoria = (TextView)findViewById(R.id.categoria);
						//get19
						//TextView descrizione = (TextView)findViewById(R.id.descrizione);



						idordine.setText(String.valueOf(orders.getIdordine()));								
						data.setText(String.valueOf(orders.getData().split("\\s")[0]));
						stato.setText(String.valueOf(orders.getStato()));
						quantita.setText(String.valueOf(orders.getQuantita()));
						prezzo.setText(String.valueOf(orders.getPrezzo())+ "€");
						sconto.setText(String.valueOf(orders.getSconto())+ "%");
						categoria.setText(String.valueOf(orders.getCategoria()));
						nome_prod.setText(String.valueOf(orders.getNome_prod()));
						//descrizione.setText(String.valueOf(orders.getDescrizione()));
						foto.setText(String.valueOf(orders.getFoto()));

						Log.d("GET0", String.valueOf(riga.get(0)));
						Log.d("GET1", String.valueOf(riga.get(1)));
						Log.d("GET2", String.valueOf(riga.get(2)));
						Log.d("GET3", String.valueOf(riga.get(3)));
						Log.d("GET4", String.valueOf(riga.get(4)));
						Log.d("GET5", String.valueOf(riga.get(5)));
						Log.d("GET6", String.valueOf(riga.get(6)));
						Log.d("GET7", String.valueOf(riga.get(7)));
						Log.d("GET8", String.valueOf(riga.get(8)));
						Log.d("GET9", String.valueOf(riga.get(9)));
						Log.d("GET10", String.valueOf(riga.get(10)));
						Log.d("GET11", String.valueOf(riga.get(11)));
						Log.d("GET12", String.valueOf(riga.get(12)));
						Log.d("GET13", String.valueOf(riga.get(13)));
						Log.d("GET14", String.valueOf(riga.get(14)));
						Log.d("GET15", String.valueOf(riga.get(15)));
						Log.d("GET16", String.valueOf(riga.get(16)));
						Log.d("GET17", String.valueOf(riga.get(17)));
						Log.d("GET18", String.valueOf(riga.get(18)));
						Log.d("GET19", String.valueOf(riga.get(19)));
						Log.d("GET20", String.valueOf(riga.get(20)));

					}

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
	public void onClick(View v) {

		Intent intent = new Intent(this, Map.class);
		startActivity(intent);

	}
}

//List<String> riga = risultato.get(0);

//Log.d("DT_risultatoget0", String.valueOf(risultato.get(0)));
//Log.d("DT_risultatoget1", String.valueOf(risultato.get(1)));

/*
TextView idordine = (TextView)findViewById(R.id.idordine);
 */