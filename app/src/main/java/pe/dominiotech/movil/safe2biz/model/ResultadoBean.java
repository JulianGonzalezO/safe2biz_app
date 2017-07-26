package pe.dominiotech.movil.safe2biz.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_LISTA_VERIF_RESULTADO)
public class ResultadoBean implements Serializable{

    @DatabaseField(columnName = "ops_lista_verif_resultado_id", id = true)
    @SerializedName("ops_lista_verif_resultado_id")
    private Long ops_lista_verif_resultado_id;                   // Id Lista Verificacion Resultado
    @DatabaseField(columnName = "ops_tipo_resultado_id")
    @SerializedName("ops_tipo_resultado_id")
    private Long ops_tipo_resultado_id;                      // Id Tipo Resultado
    @DatabaseField(columnName = "codigo")
    @SerializedName("codigo")
    private String codigo;                      // codigo (Nombre en Abreviatura)
    @DatabaseField(columnName = "nombre")
    @SerializedName("nombre")
    private String nombre;                      // Nombre Tipo Resultado

    public Long getOps_lista_verif_resultado_id() {
        return ops_lista_verif_resultado_id;
    }

    public void setOps_lista_verif_resultado_id(Long ops_lista_verif_resultado_id) {
        this.ops_lista_verif_resultado_id = ops_lista_verif_resultado_id;
    }

    public Long getOps_tipo_resultado_id() {
        return ops_tipo_resultado_id;
    }

    public void setOps_tipo_resultado_id(Long ops_tipo_resultado_id) {
        this.ops_tipo_resultado_id = ops_tipo_resultado_id;
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
