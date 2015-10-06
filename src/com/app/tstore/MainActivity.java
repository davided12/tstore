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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {
	private Activity activity = this;
	private Handler handler = new Handler();
	// User name
	private EditText et_Username;
	// Password
	private EditText et_Password;
	// Sign In
	private Button bt_SignIn;
	
	private int utente_id;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialization
		et_Username = (EditText) findViewById(R.id.et_Username);
		et_Password = (EditText) findViewById(R.id.et_Password);
		bt_SignIn = (Button) findViewById(R.id.bt_SignIn);


		bt_SignIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {	
				work();
			}
		});
	}

	private void work() {final ProgressDialog progress_dialog = new ProgressDialog(activity);
	progress_dialog.setMessage("Stiamo contattando il server...");
	progress_dialog.setCancelable(false);
	progress_dialog.show();

	String query = "";
	query += "SELECT * FROM `utente` WHERE `username` = '";
	query += et_Username.getText().toString().trim();
	query += "' AND `password` = '";
	query += et_Password.getText().toString().trim();
	query += "'";

	final Server connessione = new Server();

	
	OnQueryEseguita onQueryEseguita = new OnQueryEseguita() {									

		@Override
		public void onQueryEseguita(List<List<String>> risultato) {
			Log.d("DT", "1");
			if (risultato != null) {
				Log.d("DT", "2");

				boolean isLogged = risultato.size() > 0;
				Log.d("DT_loggato", String.valueOf(isLogged));

				if (isLogged){
					//esiste nel db
					utente_id = Integer.parseInt(risultato.get(0).get(0)); 
					Log.d("DT_ID_UTENTE", String.valueOf(utente_id));					
					
					Bundle bundle = new Bundle();
					bundle.putString("Nome_utente",et_Username.getText().toString());
					bundle.putInt("Id_utente", utente_id);
					Intent intent;

					intent = new Intent(activity, Order.class);							
						
					intent.putExtras(bundle);
					startActivity(intent);						
				} else {
					// non esiste nel db
					Log.d("DT_non_esiste", String.valueOf(isLogged) );

					Toast.makeText(activity, "Username o password non validi!", Toast.LENGTH_SHORT).show();	

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
public void onBackPressed() {

	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage("Sicuro di voler uscire?")
	.setCancelable(false)
	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int id) {
			finish();
		}

	})
	.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) { 

		}
	})

	;
	AlertDialog alert = builder.create();
	alert.show();

	}

}

