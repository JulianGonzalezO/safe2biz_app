package pe.dominiotech.movil.safe2biz.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import pe.dominiotech.movil.safe2biz.utils.AppConstants;

@DatabaseTable(tableName = AppConstants.TABLA_PARAMETRO)
public class ParametroBean {
	@DatabaseField(id = true, columnName="n_parametro")
	private Long parametro_id;

	@DatabaseField(columnName="x_codigo")
	private String	codigo;
	
	@DatabaseField(columnName="x_valor")
	private String	valor;

	@DatabaseField(columnName="x_descripcion")
	private String	descripcion;

	public enum Codes{
		PERIODO_ACTUAL{
			public String getCode() {
				return "PERIODO_ACTUAL";
			}
		},   
		VERSION_DB_SAFE2BIZ1{
			public String getCode() {
				return "VERSION_DB";
			}
		};
		public abstract String getCode();
	}
	

	public ParametroBean() {
	}
	
	public Long getParametro_id() {
		return parametro_id;
	}

	public void setParametro_id(Long parametro_id) {
		this.parametro_id = parametro_id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
