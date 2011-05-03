package com.taxi.client;

import gmaps.DirectionException;
import gmaps.DirectionInvalidRequestException;
import gmaps.DirectionNotFoundException;
import gmaps.DirectionZeroResultsException;
import gmaps.GmapsDirection;
import gmaps.OverQueryLimitException;
import rest_client.ConnectionException;
import rest_client.HttpUrlException;
import rest_client.ParamsException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
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
	private static final int UPDATE_UI = 0;
	private static final int UPDATE_TIME = 120000;
	private int idTaxi;
	private SharedData data;
	private Handler handlerTimer = new Handler();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estimation);
		data = (SharedData) getApplication();
		Bundle b = getIntent().getExtras();
		idTaxi = b.getInt("idTaxi");
		TextView tempsCourse = (TextView) findViewById(R.id.EstimationTempsTrajet);
		TextView prix = (TextView) findViewById(R.id.EstimationPrix);
		TrajetInfo infos;
		try {
			infos = GmapsDirection.getTrajetInfo(data.usrdestination,
					data.position);
			tempsCourse.setText(infos.temps);
			prix.setText(EstimationPrix.EstimPrix(infos.distanceValue));
		} catch(DirectionNotFoundException e) {
			e.printStackTrace();
		} catch(DirectionInvalidRequestException e) {
			e.printStackTrace();
		} catch(DirectionException e) {
			e.printStackTrace();
		} catch(DirectionZeroResultsException e) {
			e.printStackTrace();
		} catch(OverQueryLimitException e) {
			tempsCourse.setText("inconnue");
			prix.setText("inconnue");
			e.printStackTrace();
		}
		handlerTimer.removeCallbacks(updateEstimmation);
		handlerTimer.postDelayed(updateEstimmation, 500);
	}

	private Runnable updateEstimmation = new Runnable() {
		@Override
		public void run() {
			Log.i("taxi", "go timer");
			ClientRequest req = new ClientRequest(Main.SERVER_ADDR);
			TextView tempsTaxi = (TextView) findViewById(R.id.EstimationTempsTaxi);
			try {
				GeoPoint geo = req.getPosTaxi(idTaxi);
				TrajetInfo infos = GmapsDirection.getTrajetInfo(geo,
						data.position);
				tempsTaxi.setText(infos.temps);
			} catch(ParamsException e) {
				e.printStackTrace();
			} catch(HttpUrlException e) {
				e.printStackTrace();
			} catch(ConnectionException e) {
				e.printStackTrace();
			} catch(DirectionNotFoundException e) {
				e.printStackTrace();
			} catch(DirectionInvalidRequestException e) {
				e.printStackTrace();
			} catch(DirectionException e) {
				e.printStackTrace();
			} catch(DirectionZeroResultsException e) {
				e.printStackTrace();
			} catch(OverQueryLimitException e) {
				tempsTaxi.setText("inconnue");
			}
			Message msg = new Message();
			msg.what = UPDATE_UI;
			updateUiEvent.sendMessage(msg);
			handlerTimer.postDelayed(this, UPDATE_TIME);
		}
	};
	private Handler updateUiEvent = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case UPDATE_UI:
				break;
			default:
				super.handleMessage(msg);
			}
		}
	};
}
