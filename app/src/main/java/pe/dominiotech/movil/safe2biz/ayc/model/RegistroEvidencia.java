package pe.dominiotech.movil.safe2biz.ayc.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "ayc_evidencia")
public class RegistroEvidencia implements Serializable{

    @DatabaseField(columnName = "nombre", id = true)
    private String nombre;
    @DatabaseField(columnName = "ruta")
    private String ruta;
    @DatabaseField(columnName = "ayc_registro_id")
    private int ayc_registro_id;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getAyc_registro_id() {
        return ayc_registro_id;
    }

    public void setAyc_registro_id(int ayc_registro_id) {
        this.ayc_registro_id = ayc_registro_id;
    }

    @Override
    public String toString() {
        return this.getRuta();
    }
}
