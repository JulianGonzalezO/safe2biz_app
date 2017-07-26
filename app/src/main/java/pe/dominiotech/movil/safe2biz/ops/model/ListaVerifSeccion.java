package pe.dominiotech.movil.safe2biz.ops.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_LISTA_VERIF_SECCION)
public class ListaVerifSeccion implements Serializable{

    @DatabaseField(columnName = "ops_lista_verif_seccion_id", id = true)
    @SerializedName("ops_lista_verif_seccion_id")
    private Long ops_lista_verif_seccion_id;
    @DatabaseField(columnName = "ops_lista_verif_categoria_id")
    @SerializedName("ops_lista_verif_categoria_id")
    private Long ops_lista_verif_categoria_id;
    @DatabaseField(columnName = "ops_lista_verificacion_id")
    @SerializedName("ops_lista_verificacion_id")
    private Long ops_lista_verificacion_id;
    @DatabaseField(columnName = "nombre")
    @SerializedName("nombre")
    private String nombre;
    @DatabaseField(columnName = "orden")
    @SerializedName("codigo")
    private String orden;

    private List<ListaVerifPregunta> ListaPreguntas;
    private int flagSeccionCompleta;

    public Long getOps_lista_verif_seccion_id() {
        return ops_lista_verif_seccion_id;
    }

    public void setOps_lista_verif_seccion_id(Long ops_lista_verif_seccion_id) {
        this.ops_lista_verif_seccion_id = ops_lista_verif_seccion_id;
    }

    public Long getOps_lista_verif_categoria_id() {
        return ops_lista_verif_categoria_id;
    }

    public void setOps_lista_verif_categoria_id(Long ops_lista_verif_categoria_id) {
        this.ops_lista_verif_categoria_id = ops_lista_verif_categoria_id;
    }

    public Long getOps_lista_verificacion_id() {
        return ops_lista_verificacion_id;
    }

    public void setOps_lista_verificacion_id(Long ops_lista_verificacion_id) {
        this.ops_lista_verificacion_id = ops_lista_verificacion_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public int getFlagSeccionCompleta() {
        return flagSeccionCompleta;
    }

    public void setFlagSeccionCompleta(int flagSeccionCompleta) {
        this.flagSeccionCompleta = flagSeccionCompleta;
    }

    public void setListaPreguntas(List<ListaVerifPregunta> listaPreguntas) {
        ListaPreguntas = listaPreguntas;
    }

    public List<ListaVerifPregunta> getListaPreguntas() {
        return ListaPreguntas;
    }
}