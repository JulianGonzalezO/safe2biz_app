package pe.dominiotech.movil.safe2biz.service;

import android.content.Context;

public abstract class AppService  {
	protected Context context;
	public void setContext(Context context) {
		this.context = context;
	}
	 
}
