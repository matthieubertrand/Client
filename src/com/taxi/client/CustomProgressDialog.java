package com.taxi.client;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author Clement Bizeau & Yves Szymezak & Matthieu Bertrand Gere l'ouverture
 *         et la fermeture d'un fenetre de dialogue type progresdialog
 */
public class CustomProgressDialog extends Dialog implements OnClickListener {

	public CustomProgressDialog(Context context) {
		super(context);
		setContentView(R.layout.progressdialog);
		Button annulerBtn = (Button) findViewById(R.id.progressdialogBtnAnnuler);
		annulerBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		dismiss();
	}

}
