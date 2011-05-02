package com.taxi.client;

import gmaps.DirectionException;
import gmaps.DirectionInvalidRequestException;
import gmaps.DirectionNotFoundException;
import gmaps.DirectionZeroResultsException;
import gmaps.GmapsDirection;
import gmaps.OverQueryLimitException;
import java.util.ArrayList;
import java.util.HashMap;
import rest_client.ConnectionException;
import rest_client.HttpUrlException;
import rest_client.ParamsException;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import client_request.ClientRequest;
import core.TrajetInfo;
import core.localisation.GeoPoint;

public class Estimation extends Activity {
	/**
	 * Gere et affiche une liste d estimation de cout et de distance relative �
	 * la course
	 * 
	 * @author Clement Bizeau & Yves Szymezak & Matthieu Bertrand
	 */
	private final static String server_addr = "http://88.184.190.42:8080";
	private ListView ListView;
	private int idTaxi;
	private SharedData data;
	private ClientRequest req;
	private HashMap<String, String> map;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estimation);
		data = (SharedData) getApplication();
		Bundle b = getIntent().getExtras();
		idTaxi = b.getInt("idTaxi");
		Log.i("taxi", "idTaxi : " + idTaxi);
		ListView = (ListView) findViewById(R.id.listview);
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		map = new HashMap<String, String>();
		map.put("titre", "Temps d'arrivée du taxi estimé");
		req = new ClientRequest(server_addr);
		try {
			GeoPoint posTaxi;
			posTaxi = req.getPosTaxi(idTaxi);
			try {
				map.put("description",
						GmapsDirection.getTrajetInfo(posTaxi, data.position).temps);
			} catch(OverQueryLimitException e) {
				e.printStackTrace();
			}
			map.put("img", String.valueOf(R.drawable.taxi));
			listItem.add(map);
			map = new HashMap<String, String>();
			map.put("titre", "Temps d'arrivée à destination");
			TrajetInfo infos;
			infos = GmapsDirection.getTrajetInfo(data.usrdestination,
					data.position);
			map.put("description", infos.temps);
			map.put("img", String.valueOf(R.drawable.arrivee));
			listItem.add(map);
			map = new HashMap<String, String>();
			map.put("titre", "Estimation du prix de la course");
			Log.i("taxi", "dist : " + infos.distance);
			map.put("description",
					EstimationPrix.EstimPrix(infos.distanceValue));
			map.put("img", String.valueOf(R.drawable.euro));
			listItem.add(map);
		} catch(ConnectionException e) {
			Toast.makeText(this, "Connexion perdue", Toast.LENGTH_SHORT).show();
		} catch(DirectionNotFoundException e1) {
			e1.printStackTrace();
		} catch(DirectionInvalidRequestException e1) {
			e1.printStackTrace();
		} catch(DirectionException e1) {
			e1.printStackTrace();
		} catch(DirectionZeroResultsException e1) {
			e1.printStackTrace();
		} catch(ParamsException e) {
			e.printStackTrace();
		} catch(HttpUrlException e) {
			e.printStackTrace();
		} catch(OverQueryLimitException e) {
			e.printStackTrace();
		}
		SimpleAdapter mSchedule = new SimpleAdapter(getBaseContext(), listItem,
				R.layout.item, new String[] { "img", "titre", "description" },
				new int[] { R.id.img, R.id.titre, R.id.description });
		ListView.setAdapter(mSchedule);
		/*
		 * final Timer timLocIpdate = new Timer(); timLocIpdate.schedule(new
		 * TimerTask() {
		 * 
		 * @Override public void run() { try {
		 * Log.i("taxi","�a marche le truc de clement"); GeoPoint posTaxi =
		 * req.getPosTaxi(idTaxi); Collection<String> listItem = map.values();
		 * String [] list = (String[]) listItem.toArray(); list[0] =
		 * GmapsDirection.getTrajetInfo(posTaxi, data.position).temps;
		 * notifyAll(); } catch(ParamsException e) { e.printStackTrace(); }
		 * catch(HttpUrlException e) { e.printStackTrace(); }
		 * catch(DirectionNotFoundException e) { e.printStackTrace(); }
		 * catch(DirectionInvalidRequestException e) { e.printStackTrace(); }
		 * catch(DirectionException e) { e.printStackTrace(); }
		 * catch(DirectionZeroResultsException e) { e.printStackTrace(); }
		 * catch(ConnectionException e) { e.printStackTrace(); } } }, 0, 60000);
		 */
	}
}
