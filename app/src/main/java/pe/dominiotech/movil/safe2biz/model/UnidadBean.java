package pe.dominiotech.movil.safe2biz.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_UNIDAD)
public class UnidadBean implements Serializable{

    @DatabaseField(columnName = "fb_uea_pe_id", id = true)
    private Long fb_uea_pe_id;                   // Id unidad
    @DatabaseField(columnName = "codigo")
    private String codigo;                      // codigo (Nombre en Abreviatura)
    @DatabaseField(columnName = "nombre")
    private String nombre;                      // Nombre Unidad
    @DatabaseField(columnName = "sc_user_id")
    private Long scUserId;                      // Id Usuario

    public Long getFb_uea_pe_id() {
        return fb_uea_pe_id;
    }

    public void setFb_uea_pe_id(Long fb_uea_pe_id) {
        this.fb_uea_pe_id = fb_uea_pe_id;
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

    public Long getScUserId() {
        return scUserId;
    }

    public void setScUserId(Long scUserId) {
        this.scUserId = scUserId;
    }
}
