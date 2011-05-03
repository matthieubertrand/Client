package com.taxi.client;

import rest_client.ConnectionException;
import rest_client.CourseNotFoundException;
import rest_client.ParamsException;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import client_request.ClientRequest;

/**
 * 
 * @author Clement Bizeau & Yves Szymezak & Matthieu Bertrand Gere l'ouverture
 *         et la fermeture d'un fenetre de dialogue type progresdialog
 */
public class CustomProgressDialog extends Dialog implements OnClickListener {
	private SharedData data;

	public CustomProgressDialog(Context context) {
		super(context);
		setContentView(R.layout.progressdialog);
		Button annulerBtn = (Button) findViewById(R.id.progressdialogBtnAnnuler);
		annulerBtn.setOnClickListener(this);
		data = (SharedData) ((Main) context).getApplication();
	}

	@Override
	public void onClick(View v) {
		ClientRequest req = new ClientRequest(Main.SERVER_ADDR);
		try {
			req.removeCourse(data.telephone);
		} catch(ParamsException e) {
			e.printStackTrace();
		} catch(CourseNotFoundException e) {
			e.printStackTrace();
		} catch(ConnectionException e) {
			e.printStackTrace();
		}
		dismiss();
	}
}
