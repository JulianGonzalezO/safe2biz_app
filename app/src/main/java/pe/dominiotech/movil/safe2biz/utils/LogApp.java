package pe.dominiotech.movil.safe2biz.utils;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogApp {

	public static void log(String texto) {
		
		try {			
		    File root = Environment.getExternalStorageDirectory().getAbsoluteFile();
		    if (root.canWrite()){
		        File logfile = new File(root,AppConstants.ARCHIVO_LOG_SAFE2BIZ);
		        FileWriter gpxwriter = new FileWriter(logfile,true);
		        BufferedWriter out = new BufferedWriter(gpxwriter);
		        out.write(texto+"\n");
		        out.close();
		    }		    
		    
		} catch (IOException e) {
		}
		
	}

	public static void log(String ruta, String nombre, String texto) {

		try {
			nombre = Util.obtenerFechaLog().replace("/", AppConstants.cadena_vacia)+"_"+nombre;
			File root = Util.validarDirectorio(ruta);
			if (root.canWrite()){
				File logfile = new File(root,nombre);
				FileWriter gpxwriter = new FileWriter(logfile,true);
				BufferedWriter out = new BufferedWriter(gpxwriter);
				out.write(Util.obtenerHora()+" - "+texto+"\n");
				out.close();
			}

		} catch (IOException e) {
		}

	}

	public static void borrarLog(String ruta) {
		try {
			File log = new File(ruta);
			if (log.length() > 1024 * 1024)// si es mayor a 1M
				log.delete();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static long tamanoLog(String ruta){
		long tam = 0;
		try {
			File log = new File(ruta);
			tam = log.length();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tam;
	}

}
