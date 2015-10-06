package com.app.tstore;

import java.util.Date;

import android.provider.ContactsContract.Contacts.Data;

public class Orders {

	private int idordine;
	private String data;
	private String stato;
	private int idutente;
	private int quantita;
	private String nome_prod;
	private String img;
	private String prezzo;
	private String sconto;
	private String categoria;
	private String descrizione;
	private String foto;
	
	
	public Orders(int idordine, String data, String stato, int idutente, int quantita, String nome_prod, String img, String prezzo, String sconto, String categoria, String descrizione, String foto)
	{
		super();
		this.idordine = idordine;
		this.data = data;
		this.stato = stato;
		this.idutente = idutente;
		this.quantita = quantita;
		this.nome_prod = nome_prod;
		this.img = img;
		this.prezzo = prezzo;
		this.sconto = sconto;
		this.categoria = categoria;
		this.descrizione = descrizione;
		this.foto = foto;
	}
	
	public Orders(String nome_prod, int idutente, int idordine)
	{
		super();
		this.nome_prod = nome_prod;
		this.idutente = idutente;
		this.idordine = idordine;
	}

	public String getPrezzo() {
		return prezzo;
	}

	public String getSconto() {
		return sconto;
	}

	public String getCategoria() {
		return categoria;
	}

	public String getNome_prod() {
		return nome_prod;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getFoto() {
		return foto;
	}

	public int getIdordine() {
		return idordine;
	}

	public String getData() {
		return data;
	}

	public String getStato() {
		return stato;
	}

	public int getIdutente() {
		return idutente;
	}

	public int getQuantita() {
		return quantita;
	}
}