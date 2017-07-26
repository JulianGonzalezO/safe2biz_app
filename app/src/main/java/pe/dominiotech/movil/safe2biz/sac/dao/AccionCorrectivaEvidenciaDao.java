package pe.dominiotech.movil.safe2biz.sac.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.dominiotech.movil.safe2biz.exception.LoginException;
import pe.dominiotech.movil.safe2biz.sac.model.AccionCorrectivaEvidencia;


public class AccionCorrectivaEvidenciaDao extends OrmLiteSqliteOpenHelper {

	public AccionCorrectivaEvidenciaDao(Context context, String name, CursorFactory factory,
                                        int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

		try {
			// Create Table with given table name with columnName
			TableUtils.createTable(connectionSource, AccionCorrectivaEvidencia.class);

		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}


	public Dao.CreateOrUpdateStatus createOrUpdate(AccionCorrectivaEvidencia accionCorrectivaEvidencia) throws SQLException, java.sql.SQLException {
		Dao dao = getDao(accionCorrectivaEvidencia.getClass());
		return dao.createOrUpdate(accionCorrectivaEvidencia);

	}
	
	public void save(AccionCorrectivaEvidencia accionCorrectivaEvidencia) {

//		try {
		Dao sacDao = null;
		try {
			sacDao = getDao(accionCorrectivaEvidencia.getClass());
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

//		RuntimeExceptionDao<AccionCorrectivaEvidencia, String> sacDao = (Dao<AccionCorrectivaEvidencia, ?>) getDao(AccionCorrectivaEvidencia.class);
		try {
			sacDao.create(accionCorrectivaEvidencia);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new RuntimeException("login.error");
//		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int oldVersion, int newVersion) {

	}

	public List<AccionCorrectivaEvidencia> getEvidenciaBeanList(AccionCorrectivaEvidencia accionCorrectivaEvidencia) {
		List<AccionCorrectivaEvidencia> accionCorrectivaEvidenciaList;

		try {
			Dao<AccionCorrectivaEvidencia, ?> sacDao = null;
			try {
				sacDao = getDao(AccionCorrectivaEvidencia.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}

			QueryBuilder<AccionCorrectivaEvidencia, ?> qb = sacDao.queryBuilder();
			qb.where().eq("sac_accion_correctiva_id", accionCorrectivaEvidencia.getSac_accion_correctiva_id());

			List<AccionCorrectivaEvidencia> sacList = qb.query();

			if(sacList != null && sacList.size() > 0){
				accionCorrectivaEvidenciaList = sacList;

			}else{
				return null;
			}

			return accionCorrectivaEvidenciaList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao.error");
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
		
	public AccionCorrectivaEvidencia getBean(AccionCorrectivaEvidencia AccionCorrectivaEvidencia) {

		Dao sacDao = null;
		try {
			sacDao = getDao(AccionCorrectivaEvidencia.getClass());
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		//RuntimeExceptionDao<AccionCorrectivaEvidencia, String> usuarioDao = createDao(AccionCorrectivaEvidencia.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("nombre", AccionCorrectivaEvidencia.getNombre());
		List<AccionCorrectivaEvidencia> SacList = null;
		try {
			SacList = sacDao.queryForFieldValues(fields);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		if(SacList != null && SacList.size() > 0){
				AccionCorrectivaEvidencia = SacList.get(0);
			}else{
				return null;
			}
			
			return AccionCorrectivaEvidencia;

	}

	public AccionCorrectivaEvidencia getBeanByPath(AccionCorrectivaEvidencia AccionCorrectivaEvidencia) {

		Dao sacDao = null;
		try {
			sacDao = getDao(AccionCorrectivaEvidencia.getClass());
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		//RuntimeExceptionDao<AccionCorrectivaEvidencia, String> usuarioDao = createDao(AccionCorrectivaEvidencia.class);
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("ruta", AccionCorrectivaEvidencia.getRuta());
		List<AccionCorrectivaEvidencia> SacList = null;
		try {
			SacList = sacDao.queryForFieldValues(fields);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		if(SacList != null && SacList.size() > 0){
			AccionCorrectivaEvidencia = SacList.get(0);
		}else{
			return null;
		}

		return AccionCorrectivaEvidencia;

	}

	public int deleteById(Class clazz, Object aId) throws SQLException, java.sql.SQLException {
		Dao<AccionCorrectivaEvidencia, Object> dao = getDao(clazz);
		return dao.deleteById(aId);
	}

}
