package pe.dominiotech.movil.safe2biz.sac.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "SAC_ACCION_CORRECTIVA_EVIDENCIA")
public class AccionCorrectivaEvidencia implements Serializable{

    @DatabaseField(columnName = "nombre", id = true)
    private String nombre;
    @DatabaseField(columnName = "ruta")
    private String ruta;
    @DatabaseField(columnName = "sac_accion_correctiva_id")
    private int sac_accion_correctiva_id;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getSac_accion_correctiva_id() {
        return sac_accion_correctiva_id;
    }

    public void setSac_accion_correctiva_id(int sac_accion_correctiva_id) {
        this.sac_accion_correctiva_id = sac_accion_correctiva_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.getRuta();
    }
}
