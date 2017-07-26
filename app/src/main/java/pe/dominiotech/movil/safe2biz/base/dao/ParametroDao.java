package pe.dominiotech.movil.safe2biz.base.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.dominiotech.movil.safe2biz.exception.LoginException;
import pe.dominiotech.movil.safe2biz.model.ParametroBean;
import pe.dominiotech.movil.safe2biz.utils.AppConstants;

public class ParametroDao extends SQLiteHelper {

	public ParametroDao(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public int modificarParMovil(String codigo, String valor) {
		ContentValues valores = new ContentValues();
		valores.put(AppConstants.PARMOV_VALOR, valor);
		try {
			ParametroBean parametroBean = getParametroXCodigo(codigo);
			parametroBean.setValor(valor);
			RuntimeExceptionDao<ParametroBean, String> parametroDao = createDao(ParametroBean.class);
			int row = parametroDao.update(parametroBean);
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("ParametroDao-modificarParMovil.error");
		}
	}

	public ParametroBean getParametroXCodigo(String codigo){
		ParametroBean parametroBean = new ParametroBean();
		try {
			RuntimeExceptionDao<ParametroBean, String> parametroDao = createDao(ParametroBean.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put(AppConstants.PARMOV_CODIGO, codigo);
			List<ParametroBean> parametroBeanList = parametroDao.queryForFieldValues(fields);
			if(parametroBeanList != null && parametroBeanList.size() > 0){
				parametroBean = parametroBeanList.get(0);
			}else{
				return null;
			}
			return parametroBean;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("ParametroDao-getParametroXCodigo.error");
		}
	}

}
