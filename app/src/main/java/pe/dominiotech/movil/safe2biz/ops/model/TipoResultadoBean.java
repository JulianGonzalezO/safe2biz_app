package pe.dominiotech.movil.safe2biz.ops.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_TIPO_RESULTADO)
public class TipoResultadoBean implements Serializable{

    @DatabaseField(columnName = "ops_tipo_resultado_id", id = true)
    @SerializedName("ops_tipo_resultado_id")
    private Long opsTipoResultadoId;                   // Id Tipo Resultado
    @DatabaseField(columnName = "codigo")
    @SerializedName("codigo")
    private String codigo;                      // codigo (Nombre en Abreviatura)
    @DatabaseField(columnName = "nombre")
    @SerializedName("nombre")
    private String nombre;                      // Nombre Tipo Resultado

    public Long getOpsTipoResultadoId() {
        return opsTipoResultadoId;
    }

    public void setOpsTipoResultadoId(Long opsTipoResultadoId) {
        this.opsTipoResultadoId = opsTipoResultadoId;
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
}
