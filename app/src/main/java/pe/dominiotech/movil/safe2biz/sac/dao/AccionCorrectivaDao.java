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
import pe.dominiotech.movil.safe2biz.sac.model.AccionCorrectiva;


@SuppressWarnings("ConstantConditions")
public class AccionCorrectivaDao extends OrmLiteSqliteOpenHelper {

	public AccionCorrectivaDao(Context context, String name, CursorFactory factory,
                               int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

		try {
			// Create Table with given table name with columnName
			TableUtils.createTable(connectionSource, AccionCorrectiva.class);

		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}


	public Dao.CreateOrUpdateStatus createOrUpdate(AccionCorrectiva accionCorrectiva) throws SQLException, java.sql.SQLException {
		Dao dao = getDao(accionCorrectiva.getClass());
		return dao.createOrUpdate(accionCorrectiva);

	}
	
	public void save(AccionCorrectiva accionCorrectiva) {

//		try {
		Dao sacDao = null;
		try {
			sacDao = getDao(accionCorrectiva.getClass());
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

//		RuntimeExceptionDao<AccionCorrectiva, String> sacDao = (Dao<AccionCorrectiva, ?>) getDao(AccionCorrectiva.class);
		try {
			sacDao.create(accionCorrectiva);
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

	public List<AccionCorrectiva> getSacBeanList() {
		List<AccionCorrectiva> accionCorrectivaList;

		try {
			Dao<AccionCorrectiva, ?> sacDao = null;
			try {
				sacDao = getDao(AccionCorrectiva.class);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}

			QueryBuilder<AccionCorrectiva, ?> qb = sacDao.queryBuilder();
			qb.orderBy("fecha_acordada_ejecucion", true);

			List<AccionCorrectiva> sacList = qb.query();

			if(sacList != null && sacList.size() > 0){
				accionCorrectivaList = sacList;

			}else{
				return null;
			}

			return accionCorrectivaList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ListaVerificacionDao.error");
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
		
	public AccionCorrectiva getBean(AccionCorrectiva AccionCorrectiva) {

		Dao sacDao = null;
		try {
			sacDao = getDao(AccionCorrectiva.getClass());
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		//RuntimeExceptionDao<AccionCorrectiva, String> usuarioDao = createDao(AccionCorrectiva.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("sac_accion_correctiva_id", AccionCorrectiva.getSac_accion_correctiva_id());
		List<AccionCorrectiva> SacList = null;
		try {
			SacList = sacDao.queryForFieldValues(fields);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		if(SacList != null && SacList.size() > 0){
				AccionCorrectiva = SacList.get(0);
			}else{
				return null;
			}
			
			return AccionCorrectiva;

	} 




}
