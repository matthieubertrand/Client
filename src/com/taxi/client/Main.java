package com.taxi.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rest_client.CourseExistException;
import rest_client.HttpUrlException;
import rest_client.ParamsException;

import core.course.Client;
import core.course.Course;
import core.localisation.GeoPoint;

import client_request.ClientRequest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/**
 *  Gere les lancements d'activitées selon les boutons
 *  Envoi requete sur serveur web
 *  Reccupere destination du client
 * @author Clement Bizeau & Yves Szymezak & Matthieu Bertrand
 * 
 */
public class Main extends Activity implements OnClickListener {
	private Button goCourseBtn;
	private Button infoBtn;
	private Button releaseBtn;
	private CustomDialog infosDialog;
	private ReleaseDialog releaseDialog;
	private CustomProgressDialog progressdialog;
	private String usrdestination;
	private SharedPreferences sharePref;
	private SharedData data;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        data = (SharedData) getApplication();
        goCourseBtn = (Button)findViewById(R.id.goCourseBtn);
        goCourseBtn.setOnClickListener(this);
        infoBtn = (Button)findViewById (R.id.infoBtn);
        infoBtn.setOnClickListener(this);
        releaseBtn = (Button)findViewById((R.id.releaseBtn));
        releaseBtn.setOnClickListener(this);
        sharePref = getSharedPreferences("Info_Client", 0);
        data.nom = sharePref.getString("usrname", "");
        data.prenom = sharePref.getString("usrsurname", "");
        data.telephone = sharePref.getString("phone", "");
        
    }
	@Override
	public void onClick(View v) {
		Log.i("taxi","id : "+v.getId());
		switch(v.getId()) {
		case R.id.goCourseBtn:
			TextView textdestination = (TextView) findViewById(R.id.DestEditTxt);
            usrdestination = textdestination.getText().toString();
            Pattern p = Pattern.compile(".{4,}");
            Matcher m = p.matcher(usrdestination);
            if(m.matches())
            {
            	/*
            	Log.i("taxi", "execute request");
            	ClientRequest req = new ClientRequest("http://88.184.190.42:8080");
            	try {
					req.addCourse(new Course(0,usrdestination,new Client(data.nom, data.prenom, new GeoPoint(01.236547, 25.369874), data.telephone)));
				} catch (CourseExistException e) {
					// TODO Auto-generated catch block
					Log.i("taxi", "course exist exception");
					e.printStackTrace();
				} catch (ParamsException e) {
					// TODO Auto-generated catch block
					Log.i("taxi", "params exception");
					e.printStackTrace();
				} catch (HttpUrlException e) {
					Log.i("taxi", "httpurl exception");
					e.printStackTrace();
				} catch (Exception e) {
					Log.i("taxi", "exception");
				}
				Log.i("taxi", "request ok");
				*/
            	//progressdialog=new CustomProgressDialog( this);
    			//progressdialog.show();
    			Intent intent = new Intent(Main.this, Estimation.class);
    			startActivity(intent);
				
            }
		else 
			{
			Toast.makeText(this, "Erreur de destination, veuillez vérifier le contenu.",Toast.LENGTH_SHORT  ).show();
			}
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
}
