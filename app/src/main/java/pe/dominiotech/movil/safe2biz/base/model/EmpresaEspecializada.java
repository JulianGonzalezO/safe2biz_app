package pe.dominiotech.movil.safe2biz.base.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_EMPRESA_ESPECIALIZADA)
public class EmpresaEspecializada implements Serializable{

    @DatabaseField(columnName = "fb_empresa_especializada_id", id = true)
    @SerializedName("fb_empresa_especializada_id")
    private Long fb_empresa_especializada_id;                    // Id Empresa Especializada
    @DatabaseField(columnName = "razon_social")
    @SerializedName("razon_social")
    private String razon_social;                             // Razón Social
    @DatabaseField(columnName = "ruc_empresa")
    @SerializedName("ruc_empresa")
    private String ruc_empresa;                              // RUC Empresa
    @DatabaseField(columnName = "g_rol_empresa_id")
    @SerializedName("g_rol_empresa_id")
    private Long g_rol_empresa_id;                              // Id Rol Empresa

    public Long getFb_empresa_especializada_id() {
        return fb_empresa_especializada_id;
    }

    public void setFb_empresa_especializada_id(Long fb_empresa_especializada_id) {
        this.fb_empresa_especializada_id = fb_empresa_especializada_id;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getRuc_empresa() {
        return ruc_empresa;
    }

    public void setRuc_empresa(String ruc_empresa) {
        this.ruc_empresa = ruc_empresa;
    }

    public Long getG_rol_empresa_id() {
        return g_rol_empresa_id;
    }

    public void setG_rol_empresa_id(Long g_rol_empresa_id) {
        this.g_rol_empresa_id = g_rol_empresa_id;
    }
}
