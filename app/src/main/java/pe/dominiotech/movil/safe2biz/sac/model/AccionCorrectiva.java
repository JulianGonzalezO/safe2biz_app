package pe.dominiotech.movil.safe2biz.sac.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_SAC)
public class AccionCorrectiva implements Serializable{

    @DatabaseField(columnName = "sac_accion_correctiva_id", id= true)
    private int sac_accion_correctiva_id;                   // Id
    @DatabaseField(columnName = "codigo_accion_correctiva")
    private String codigo_accion_correctiva;
    @DatabaseField(columnName = "accion_correctiva_detalle")
    private String accion_correctiva_detalle;
    @DatabaseField(columnName = "fecha_acordada_ejecucion")
    private String fecha_acordada_ejecucion;
    @DatabaseField(columnName = "nombre_responsable_correccion")
    private String nombre_responsable_correccion;
    @DatabaseField(columnName = "origen")
    private String origen;
    @DatabaseField(columnName = "fecha_ejecucion")
    private String fecha_ejecucion;

    public int getSac_accion_correctiva_id() {
        return sac_accion_correctiva_id;
    }

    public void setSac_accion_correctiva_id(int sac_accion_correctiva_id) {
        this.sac_accion_correctiva_id = sac_accion_correctiva_id;
    }

    public String getCodigo_accion_correctiva() {
        return codigo_accion_correctiva;
    }

    public void setCodigo_accion_correctiva(String codigo_accion_correctiva) {
        this.codigo_accion_correctiva = codigo_accion_correctiva;
    }

    public String getAccion_correctiva_detalle() {
        return accion_correctiva_detalle;
    }

    public void setAccion_correctiva_detalle(String accion_correctiva_detalle) {
        this.accion_correctiva_detalle = accion_correctiva_detalle;
    }

    public String getFecha_acordada_ejecucion() {
        return fecha_acordada_ejecucion;
    }

    public void setFecha_acordada_ejecucion(String fecha_acordada_ejecucion) {
        this.fecha_acordada_ejecucion = fecha_acordada_ejecucion;
    }

    public String getNombre_responsable_correccion() {
        return nombre_responsable_correccion;
    }

    public void setNombre_responsable_correccion(String nombre_responsable_correccion) {
        this.nombre_responsable_correccion = nombre_responsable_correccion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }


    public String getFecha_ejecucion() {
        return fecha_ejecucion;
    }

    public void setFecha_ejecucion(String fecha_ejecucion) {
        this.fecha_ejecucion = fecha_ejecucion;
    }
}
