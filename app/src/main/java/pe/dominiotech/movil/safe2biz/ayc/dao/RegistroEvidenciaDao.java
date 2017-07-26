package pe.dominiotech.movil.safe2biz.ayc.dao;

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

import pe.dominiotech.movil.safe2biz.ayc.model.RegistroEvidencia;
import pe.dominiotech.movil.safe2biz.exception.LoginException;


public class RegistroEvidenciaDao extends OrmLiteSqliteOpenHelper {

	public RegistroEvidenciaDao(Context context, String name, CursorFactory factory,
								int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

		try {
			// Create Table with given table name with columnName
			TableUtils.createTable(connectionSource, RegistroEvidencia.class);

		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}


	public Dao.CreateOrUpdateStatus createOrUpdate(RegistroEvidencia evidenciaBean) throws SQLException, java.sql.SQLException {
		Dao dao = getDao(evidenciaBean.getClass());
		return dao.createOrUpdate(evidenciaBean);
	}
	
	public void save(RegistroEvidencia evidenciaBean) {

//		try {
		Dao sacDao = null;
		try {
			sacDao = getDao(evidenciaBean.getClass());
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

//		RuntimeExceptionDao<RegistroEvidencia, String> sacDao = (Dao<RegistroEvidencia, ?>) getDao(RegistroEvidencia.class);
		try {
			sacDao.create(evidenciaBean);
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

	public List<RegistroEvidencia> getEvidenciaAyCBeanList(RegistroEvidencia evidenciaBean) {
		List<RegistroEvidencia> registroEvidenciaList;

		try {
			Dao<RegistroEvidencia, ?> sacDao = null;
			try {
				sacDao = getDao(RegistroEvidencia.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}

			QueryBuilder<RegistroEvidencia, ?> qb = sacDao.queryBuilder();
			qb.where().eq("ayc_registro_id",evidenciaBean.getAyc_registro_id());

			List<RegistroEvidencia> sacList = qb.query();

			if(sacList != null && sacList.size() > 0){
				registroEvidenciaList = sacList;

			}else{
				return null;
			}

			return registroEvidenciaList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao.error");
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
		
	public RegistroEvidencia getBean(RegistroEvidencia RegistroEvidencia) {

		Dao sacDao = null;
		try {
			sacDao = getDao(RegistroEvidencia.getClass());
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		//RuntimeExceptionDao<RegistroEvidencia, String> usuarioDao = createDao(RegistroEvidencia.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("ayc_registro_id", RegistroEvidencia.getAyc_registro_id());
		List<RegistroEvidencia> SacList = null;
		try {
			SacList = sacDao.queryForFieldValues(fields);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		if(SacList != null && SacList.size() > 0){
				RegistroEvidencia = SacList.get(0);
			}else{
				return null;
			}
			
			return RegistroEvidencia;

	}

	public RegistroEvidencia getBeanByPath(RegistroEvidencia RegistroEvidencia) {

		Dao sacDao = null;
		try {
			sacDao = getDao(RegistroEvidencia.getClass());
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		//RuntimeExceptionDao<RegistroEvidencia, String> usuarioDao = createDao(RegistroEvidencia.class);
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("ruta", RegistroEvidencia.getRuta());
		List<RegistroEvidencia> SacList = null;
		try {
			SacList = sacDao.queryForFieldValues(fields);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		if(SacList != null && SacList.size() > 0){
			RegistroEvidencia = SacList.get(0);
		}else{
			return null;
		}

		return RegistroEvidencia;

	}

	public int deleteById(Class clazz, Object aId) throws SQLException, java.sql.SQLException {
		Dao<RegistroEvidencia, Object> dao = getDao(clazz);
		return dao.deleteById(aId);
	}

}
