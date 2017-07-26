package pe.dominiotech.movil.safe2biz.model;

public class FiltroDescargaModuloBean {

	private String periodo;
	private String anho;
	private boolean listaVerificacion;
	private boolean actosAndCondiciones;
	private boolean ejecucionSac;
	private boolean verificacionSac;
	
	public FiltroDescargaModuloBean(){
		this.periodo = "";
		this.anho = "";
		this.listaVerificacion = false;
		this.actosAndCondiciones = false;
		this.ejecucionSac = false;
		this.verificacionSac = false;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getAnho() {
		return anho;
	}

	public void setAnho(String anho) {
		this.anho = anho;
	}

	public boolean isListaVerificacion() {
		return listaVerificacion;
	}

	public void setListaVerificacion(boolean listaVerificacion) {
		this.listaVerificacion = listaVerificacion;
	}

	public boolean isActosAndCondiciones() {
		return actosAndCondiciones;
	}

	public void setActosAndCondiciones(boolean actosAndCondiciones) {
		this.actosAndCondiciones = actosAndCondiciones;
	}

	public boolean isEjecucionSac() {
		return ejecucionSac;
	}

	public void setEjecucionSac(boolean ejecucionSac) {
		this.ejecucionSac = ejecucionSac;
	}

	public boolean isVerificacionSac() {
		return verificacionSac;
	}

	public void setVerificacionSac(boolean verificacionSac) {
		this.verificacionSac = verificacionSac;
	}
}
