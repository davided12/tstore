package com.app.tstore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.util.Log;


public class Server {

	public void eseguiQuery (final String query, final OnQueryEseguita onQueryEseguita, final Handler handler) {

		new Thread(new Runnable() {

			List<List<String>> risultato = null;
			@Override
			public void run() {

				Log.d("DT", query);
				//String url = "jdbc:mysql://" + "suxsem.dlinkddns.com" + ":3306/davide_db?connectTimeout=5000";
				String url = "jdbc:mysql://" + "sql2.freemysqlhosting.net" + ":3306/sql292151?connectTimeout=15000";
				String username_db = "sql292151";
				String password_db = "sB7*xJ8*";	


				Connection connection = null;
				try {
					System.out.println("Connecting database...");
					Class.forName("com.mysql.jdbc.Driver").newInstance();

					connection = DriverManager.getConnection(url, username_db, password_db);	
					System.out.println("Database connected!");

					Statement stmt = connection.createStatement();
					stmt.setQueryTimeout(5);
					stmt.execute(query);

					ResultSet resultSet = stmt.getResultSet();

					risultato = new ArrayList<List<String>>();

					if (resultSet != null) {
						ResultSetMetaData metadata = resultSet.getMetaData();
						int numberOfColumns = metadata.getColumnCount();

						while (resultSet.next()) {      
							List<String> riga = new ArrayList<String>();

							int i = 1;
							while(i <= numberOfColumns) {
								riga.add(resultSet.getString(i++));
							}
							risultato.add(riga);

						}						
					}

					Log.d("DT", "6");
					stmt.close();

					connection.close();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {

					System.out.println("Chiusura della connessione.");
					if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
				}

				handler.post(new Runnable() {

					@Override
					public void run() {
						if (onQueryEseguita != null)
							onQueryEseguita.onQueryEseguita(risultato);

					}
				});
				Log.d("DT", "7");
			}
		}).start();		
	}

	public static abstract class OnQueryEseguita {

		public abstract void onQueryEseguita (List<List<String>> risultato);
	}

}

