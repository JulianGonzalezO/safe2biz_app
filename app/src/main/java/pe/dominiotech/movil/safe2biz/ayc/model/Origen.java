package pe.dominiotech.movil.safe2biz.ayc.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "origen_ayc")
public class Origen implements Serializable {

    @DatabaseField(columnName = "code", id = true)
    private	String code;

    @DatabaseField(columnName = "name")
    private String name;

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
}
