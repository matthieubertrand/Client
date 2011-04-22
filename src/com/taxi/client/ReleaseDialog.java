package com.taxi.client;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
/**
 * Gere la fenetre de dialogue relative aux informations de l'application
 * @author Clement Bizeau & Yves Szymezak & Matthieu Bertrand
 *
 */

	public class ReleaseDialog extends Dialog implements OnClickListener {

		public ReleaseDialog(Context context) {
			super(context);
			setTitle("Information");
			setContentView(R.layout.release);
			Button annulerBtn = (Button)findViewById(R.id.dialogInfoBtnRetour);
	        annulerBtn.setOnClickListener(this);		}

		public void onClick(View v) {
			dismiss();
			
				
			}
		
	}


