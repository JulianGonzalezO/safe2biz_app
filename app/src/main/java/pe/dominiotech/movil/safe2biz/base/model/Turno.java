package pe.dominiotech.movil.safe2biz.base.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_TURNO)
public class Turno implements Serializable{

    @DatabaseField(columnName = "code", id = true)
    @SerializedName("codigo")
    private String code;                      // codigo (Nombre en Abreviatura)
    @DatabaseField(columnName = "name")
    @SerializedName("nombre")
    private String name;                      // Nombre Turno

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
