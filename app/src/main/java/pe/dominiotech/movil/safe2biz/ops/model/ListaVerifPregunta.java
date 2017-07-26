package pe.dominiotech.movil.safe2biz.ops.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_LISTA_VERIF_PREGUNTA)
public class ListaVerifPregunta implements Serializable{

    @DatabaseField(columnName = "ops_lista_verif_pregunta_id", id = true)
    @SerializedName("ops_lista_verif_pregunta_id")
    private Long ops_lista_verif_pregunta_id;
    @DatabaseField(columnName = "ops_lista_verif_seccion_id")
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
    @DatabaseField(columnName = "flag_pregunta")
    @SerializedName("flag_pregunta")
    private int flag_pregunta;
    @DatabaseField(columnName = "orden")
    @SerializedName("codigo")
    private String ordenEncuesta;
    private Long  opsListaVerifResultadoId;
    private int flagObservacion;
    private int flagFoto;


    public String getOrdenEncuesta() {
        return ordenEncuesta;
    }

    public void setOrdenEncuesta(String ordenEncuesta) {
        this.ordenEncuesta = ordenEncuesta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getOps_lista_verif_pregunta_id() {
        return ops_lista_verif_pregunta_id;
    }

    public void setOps_lista_verif_pregunta_id(Long ops_lista_verif_pregunta_id) {
        this.ops_lista_verif_pregunta_id = ops_lista_verif_pregunta_id;
    }

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

    public int getFlag_pregunta() {
        return flag_pregunta;
    }

    public void setFlag_pregunta(int flag_pregunta) {
        this.flag_pregunta = flag_pregunta;
    }

    public int getFlagObservacion() {
        return flagObservacion;
    }

    public void setFlagObservacion(int flagObservacion) {
        this.flagObservacion = flagObservacion;
    }

    public int getFlagFoto() {
        return flagFoto;
    }

    public void setFlagFoto(int flagFoto) {
        this.flagFoto = flagFoto;
    }

    public Long getOpsListaVerifResultadoId() {
        return opsListaVerifResultadoId;
    }

    public void setOpsListaVerifResultadoId(Long opsListaVerifResultadoId) {
        this.opsListaVerifResultadoId = opsListaVerifResultadoId;
    }
}