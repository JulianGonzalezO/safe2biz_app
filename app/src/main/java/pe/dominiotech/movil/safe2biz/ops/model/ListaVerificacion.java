package pe.dominiotech.movil.safe2biz.ops.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_OPS_LISTA_VERIFICACION)
public class ListaVerificacion implements Serializable{

    @DatabaseField(columnName = "ops_lista_verificacion_id", id = true)
    @SerializedName("ops_lista_verificacion_id")
    private Long ops_lista_verificacion_id;
    @DatabaseField(columnName = "codigo")
    @SerializedName("codigo")
    private String codigo;
    @DatabaseField(columnName = "nombre")
    @SerializedName("nombre")
    private String nombre;
    @DatabaseField(columnName = "ops_tipo_resultado_id")
    @SerializedName("ops_tipo_resultado_id")
    private Long opsTipoResultadoId;

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

    public Long getOps_lista_verificacion_id() {
        return ops_lista_verificacion_id;
    }

    public void setOps_lista_verificacion_id(Long ops_lista_verificacion_id) {
        this.ops_lista_verificacion_id = ops_lista_verificacion_id;
    }

    public Long getOpsTipoResultadoId() {
        return opsTipoResultadoId;
    }

    public void setOpsTipoResultadoId(Long opsTipoResultadoId) {
        this.opsTipoResultadoId = opsTipoResultadoId;
    }
}