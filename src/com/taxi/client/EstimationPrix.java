package com.taxi.client;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EstimationPrix {

	public static String EstimPrix(double distanceMeter) {
		GregorianCalendar cal;
		int num_jour;
		int heure;
		double prix;
		cal = new GregorianCalendar();
		heure = cal.get(Calendar.HOUR_OF_DAY);
		num_jour = cal.get(Calendar.DAY_OF_WEEK);
		double distance = distanceMeter / 1000;

		if(num_jour <= 7 && num_jour > 1) {
			if(heure < 19 && heure >= 7) {

				prix = 1.5 * distance;
			} else {
				prix = 2 * distance;
			}
		} else {
			prix = 2 * distance;

		}
		prix += 2.2;
		if(prix < 6.2) {
			prix = 6.2;
		}
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(prix) + " €";
	}

}
