package com.br.xmaps.galeriateste;

import java.util.Collection;

public class GalleryImage {
	String id_foto;
	String esporte;
	String latitude;
	String longitude;
	String foto;
	String thumb;
	String legenda;
	String usuario;
	
	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getId_foto() {
		return id_foto;
	}


	public void setId_foto(String id_foto) {
		this.id_foto = id_foto;
	}


	public String getEsporte() {
		return esporte;
	}


	public void setEsporte(String esporte) {
		this.esporte = esporte;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}


	public String getThumb() {
		return thumb;
	}


	public void setThumb(String thumb) {
		this.thumb = thumb;
	}


	public String getLegenda() {
		return legenda;
	}


	public void setLegenda(String legenda) {
		this.legenda = legenda;
	}

	
	public GalleryImage(Collection<String> imagem){
		this.usuario = (String) imagem.toArray()[2];
		this.id_foto = (String) imagem.toArray()[1];
		this.esporte = (String) imagem.toArray()[0];
		this.latitude = (String) imagem.toArray()[3];
		this.longitude = (String) imagem.toArray()[5];
		this.foto = (String) imagem.toArray()[4];
		this.thumb = (String) imagem.toArray()[6];
		this.legenda = (String) imagem.toArray()[7];
	}
	

}
