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

import pe.dominiotech.movil.safe2biz.ayc.model.Origen;
import pe.dominiotech.movil.safe2biz.ayc.model.Registro;
import pe.dominiotech.movil.safe2biz.ayc.model.TipoRiesgo;
import pe.dominiotech.movil.safe2biz.exception.LoginException;


public class RegistroDao extends OrmLiteSqliteOpenHelper {

    public RegistroDao(Context context, String name, CursorFactory factory,
                       int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

        try {
            // Create Table with given table name with columnName
            TableUtils.createTable(connectionSource, Registro.class);

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }


    public Dao.CreateOrUpdateStatus createOrUpdate(Registro registroModel) throws SQLException, java.sql.SQLException {
        Dao dao = getDao(registroModel.getClass());
        return dao.createOrUpdate(registroModel);

    }

    public void save(Registro registroModel) {

//		try {
        Dao registroDao = null;
        try {
            registroDao = getDao(registroModel.getClass());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

//		RuntimeExceptionDao<Registro, String> registroDao = (Dao<Registro, ?>) getDao(Registro.class);
        try {
            registroDao.create(registroModel);
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

    public List<Registro> getRegistroList() {
        List<Registro> RegistroList;

        try {
            Dao<Registro, ?> registroDao = null;
            try {
                registroDao = getDao(Registro.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }

            QueryBuilder<Registro, ?> qb = registroDao.queryBuilder();

            List<Registro> sacList = qb.query();

            if(sacList != null && sacList.size() > 0){
                RegistroList = sacList;

            }else{
                return null;
            }

            return RegistroList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new LoginException("ListaVerificacionDao.error");
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Registro getBean(Registro Registro) {

        Dao registroDao = null;
        try {
            registroDao = getDao(Registro.getClass());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        //RuntimeExceptionDao<Registro, String> usuarioDao = createDao(Registro.class);
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("ayc_registro_id", Registro.getAyc_registro_id());
        List<Registro> SacList = null;
        try {
            SacList = registroDao.queryForFieldValues(fields);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        if(SacList != null && SacList.size() > 0){
            Registro = SacList.get(0);
        }else{
            return null;
        }
        return Registro;
    }

    public List getAll(Class clazz) throws SQLException, java.sql.SQLException {
        Dao dao = getDao(clazz);
        return dao.queryForAll();
    }

    public Object getById(Class clazz, Object aId) throws SQLException, java.sql.SQLException {
        Dao dao = getDao(clazz);
        return dao.queryForId(aId);
    }

    public Dao.CreateOrUpdateStatus createOrUpdateGeneric(Object obj) throws SQLException, java.sql.SQLException {
        Dao dao = getDao(obj.getClass());
        return dao.createOrUpdate(obj);
    }

    public  int deleteById(Class clazz, Object aId) throws SQLException, java.sql.SQLException {
        Dao dao = getDao(clazz);
        return dao.deleteById(aId);
    }


    public Dao.CreateOrUpdateStatus createOrUpdateOrigen(Origen origen) throws SQLException, java.sql.SQLException {
        Dao dao = getDao(origen.getClass());
        return dao.createOrUpdate(origen);
    }

    public Origen getOrigen(Origen origen) {

        Dao origenDao = null;
        try {
            origenDao = getDao(origen.getClass());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        //RuntimeExceptionDao<Registro, String> usuarioDao = createDao(Registro.class);
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("code", origen.getCode());
        List<Origen> origenList = null;
        try {
            assert origenDao != null;
            origenList = origenDao.queryForFieldValues(fields);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        if(origenList != null && origenList.size() > 0){
            origen = origenList.get(0);
        }else{
            return null;
        }
        return origen;
    }

    public Origen refreshOrigen(Origen origen){

        Dao origenDao ;
        try {
            origenDao = getDao(origen.getClass());
            origenDao.refresh(origen);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return origen;
    }

    public TipoRiesgo refreshTipoRiesgo(TipoRiesgo tipoRiesgo){

        Dao tipoRiesgoDao;
        try {
            tipoRiesgoDao = getDao(tipoRiesgo.getClass());
            tipoRiesgoDao.refresh(tipoRiesgo);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return tipoRiesgo;
    }

    public List<TipoRiesgo> getTipoRiesgoList() {
        List<TipoRiesgo> tipoRiesgoList ;
        Dao<TipoRiesgo, Integer> tipoRiesgoDao;

        try {
            tipoRiesgoDao = getDao(TipoRiesgo.class);
            List<TipoRiesgo> riesgoList = tipoRiesgoDao.queryForAll();
            if(riesgoList != null && riesgoList.size() > 0){
                tipoRiesgoList = riesgoList;
            }else{
                return null;
            }

            return tipoRiesgoList;

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            throw new LoginException("ListaVerificacionDao.error");
        }
    }


}
