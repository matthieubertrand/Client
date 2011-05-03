package com.taxi.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rest_client.ConnectionException;
import rest_client.CourseExistException;
import rest_client.HttpUrlException;
import rest_client.ParamsException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import client_request.ClientRequest;
import core.course.Client;
import core.course.Course;
import core.localisation.GeoPoint;

/**
 * Gere les lancements d'activit�es selon les boutons Envoi requete sur serveur
 * web Reccupere destination du client
 * 
 * @author Clement Bizeau & Yves Szymezak & Matthieu Bertrand
 * 
 */
public class Main extends Activity implements OnClickListener, LocationListener {
	private final static String server_addr = "http://88.184.190.42:8080";
	private Button goCourseBtn;
	private Button infoBtn;
	private Button releaseBtn;
	private CustomDialog infosDialog;
	private ReleaseDialog releaseDialog;
	private SharedPreferences sharePref;
	private SharedData data;
	private LocationManager locManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		data = (SharedData) getApplication();
		goCourseBtn = (Button) findViewById(R.id.goCourseBtn);
		goCourseBtn.setOnClickListener(this);
		infoBtn = (Button) findViewById(R.id.infoBtn);
		infoBtn.setOnClickListener(this);
		releaseBtn = (Button) findViewById(R.id.releaseBtn);
		releaseBtn.setOnClickListener(this);
		sharePref = getSharedPreferences("Info_Client", 0);
		data.nom = sharePref.getString("usrname", "");
		data.prenom = sharePref.getString("usrsurname", "");
		data.telephone = sharePref.getString("phone", "");
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.goCourseBtn:
			TextView textdestination = (TextView) findViewById(R.id.DestEditTxt);
			data.usrdestination = textdestination.getText().toString();
			Pattern p = Pattern.compile(".{4,}");
			Matcher m = p.matcher(data.usrdestination);
			if(m.matches()) {
				ClientRequest req = new ClientRequest(server_addr);
				try {
					if(data.position != null) {
						// GmapsDirection.getTrajetInfo(data.position,
						// data.usrdestination);
						req.addCourse(new Course(0, data.usrdestination,
								new Client(data.nom, data.prenom,
										data.position, data.telephone)));
						new ProgressTask(this).execute(data.telephone);
					} else
						Toast.makeText(this,
								"Votre position n'a pas encore été determinée",
								Toast.LENGTH_SHORT).show();
				} catch(CourseExistException e) {
					Toast.makeText(this, "Course déjà répertoriée",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				} catch(ParamsException e) {
					e.printStackTrace();
					/*
					 * }catch(DirectionNotFoundException e) {
					 * Toast.makeText(this, "Destination incorrecte",
					 * Toast.LENGTH_SHORT).show(); e.printStackTrace(); }
					 * catch(DirectionInvalidRequestException e) {
					 * Toast.makeText(this, "Destination incorrecte",
					 * Toast.LENGTH_SHORT).show(); e.printStackTrace(); }
					 * catch(DirectionException e) { Toast.makeText(this,
					 * "Destination incorrecte", Toast.LENGTH_SHORT).show();
					 * e.printStackTrace(); }
					 * catch(DirectionZeroResultsException e) {
					 * Toast.makeText(this, "Destination incorrecte",
					 * Toast.LENGTH_SHORT).show(); e.printStackTrace();
					 */
				} catch(HttpUrlException e) {
					Toast.makeText(this, "La connexion au serveur a échouée",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				} catch(ConnectionException e) {
					Toast.makeText(this, "La connexion au serveur a échouée",
							Toast.LENGTH_SHORT).show();
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else
				Toast.makeText(this,
						"Erreur de destination, veuillez vérifier le contenu",
						Toast.LENGTH_SHORT).show();
			break;
		case R.id.infoBtn:
			infosDialog = new CustomDialog(this);
			infosDialog.show();
			break;
		case R.id.releaseBtn:
			releaseDialog = new ReleaseDialog(this);
			releaseDialog.show();
			break;
		}
	}

	public class ProgressTask extends AsyncTask<String, Void, Integer> {
		private CustomProgressDialog dialog;

		public ProgressTask(Context parent) {
			dialog = new CustomProgressDialog(parent);
		}

		@Override
		protected void onPostExecute(Integer result) {
			dialog.dismiss();
			if(result < 0)
				return;
			Intent estim = new Intent(Main.this, Estimation.class);
			estim.putExtra("idTaxi", result);
			startActivity(estim);
		}

		@Override
		protected void onPreExecute() {
			dialog.show();
		}

		@Override
		protected Integer doInBackground(String... params) {
			ClientRequest req = new ClientRequest(server_addr);
			for(;;)
				try {
					if(!dialog.isShowing())
						return -1;
					int idTaxi = req.getCourse(params[0]);
					if(idTaxi > 0)
						return idTaxi;
					else
						try {
							Thread.sleep(10000);
						} catch(InterruptedException e) {
							e.printStackTrace();
						}
				} catch(ParamsException e) {
					e.printStackTrace();
				} catch(ConnectionException e) {
					e.printStackTrace();
					return -1;
				}
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		if(data.position == null)
			data.position = new GeoPoint(location.getLatitude(),
					location.getLongitude());
		else {
			data.position.lat = location.getLatitude();
			data.position.lon = location.getLongitude();
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
