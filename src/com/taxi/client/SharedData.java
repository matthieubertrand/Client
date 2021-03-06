package com.taxi.client;

import android.app.Application;
import core.localisation.GeoPoint;

/**
 * Regroupe les data disponible dans toute l application
 * 
 * @author Clement Bizeau & Yves Szymezak & Matthieu Bertrand
 * 
 */
public class SharedData extends Application {
	public String nom;
	public String prenom;
	public String telephone;
	public GeoPoint position;
	public String usrdestination;
}
