package com.taxi.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Clement Bizeau & Yves Szymezak & Matthieu Bertrand Gere une fenetre
 *         de dialogue renseignant sur les informations clientes (+sauvegarde)
 */
public class CustomDialog extends Dialog implements OnClickListener {
	private SharedPreferences sharedPref;
	TextView textnom;
	TextView textprenom;
	TextView texttelephone;
	SharedData data;

	public CustomDialog(Context context) {
		super(context);
		setTitle("Information Client");
		setContentView(R.layout.infoclient);
		data = (SharedData) ((Activity) context).getApplication();
		textnom = (TextView) findViewById(R.id.nomEditTxt);
		textprenom = (TextView) findViewById(R.id.prenomEditTxt);
		texttelephone = (TextView) findViewById(R.id.telEditTxt);
		Button annulerBtn = (Button) findViewById(R.id.dialogInfoBtnAnnuler);
		annulerBtn.setOnClickListener(this);
		Button validerBtn = (Button) findViewById(R.id.dialogInfoBtnValider);
		validerBtn.setOnClickListener(this);
		textnom.setText(data.nom);
		textprenom.setText(data.prenom);
		texttelephone.setText(data.telephone);
		sharedPref = context.getSharedPreferences("Info_Client", 0);
	}

	@Override
	public void onClick(View v) {

		switch(v.getId()) {
		case R.id.dialogInfoBtnAnnuler:
			dismiss();
			break;
		case R.id.dialogInfoBtnValider:
			data.nom = textnom.getText().toString();
			data.prenom = textprenom.getText().toString();
			data.telephone = texttelephone.getText().toString();
			Pattern p = Pattern.compile("0[0-9]{9}");
			Matcher m = p.matcher(data.telephone);
			Pattern p1 = Pattern.compile("[a-zA-Z]{2,}");
			Matcher m1 = p1.matcher(data.nom);
			Pattern p2 = Pattern.compile("[a-zA-Z]{2,}");
			Matcher m2 = p2.matcher(data.prenom);
			Pattern p4 = Pattern.compile(".{2,}");
			Matcher m4 = p4.matcher(data.nom);
			Pattern p5 = Pattern.compile(".{2,}");
			Matcher m5 = p5.matcher(data.prenom);

			SharedPreferences.Editor editor = sharedPref.edit();

			if(m.matches() && m1.matches() && m2.matches()) {
				if(m4.matches() && m5.matches()) {

					editor.putString("usrname", data.nom);
					editor.putString("usrsurname", data.prenom);
					editor.putString("phone", data.telephone);
					editor.commit();
					dismiss();
				}
			} else {
				Toast.makeText(
						getContext(),
						"Erreur dans les champs de saisie, veuillez vérifier le contenu.",
						Toast.LENGTH_SHORT).show();
			}

			break;
		}
	}
}
