package pe.dominiotech.movil.safe2biz.base.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_AREA)
public class Area implements Serializable{

    @DatabaseField(columnName = "fb_area_id", id = true)
    @SerializedName("fb_area_id")
    private Long fb_area_id;                   // Id Area
    @DatabaseField(columnName = "codigo")
    @SerializedName("codigo")
    private String codigo;                      // codigo
    @DatabaseField(columnName = "nombre")
    @SerializedName("nombre")
    private String nombre;                      // Nombre Area

    public Long getFb_area_id() {
        return fb_area_id;
    }

    public void setFb_area_id(Long fb_area_id) {
        this.fb_area_id = fb_area_id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.getNombre();
    }
}
