package pe.dominiotech.movil.safe2biz.base.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.dominiotech.movil.safe2biz.exception.LoginException;
import pe.dominiotech.movil.safe2biz.model.UsuarioBean;

public class UsuarioDao extends SQLiteHelper{

	public UsuarioDao(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	

	public int save(UsuarioBean usuarioBean) {
		int isSaveUsuario = 1;
		try {
		 	RuntimeExceptionDao<UsuarioBean, String> usuarioDao = createDao(UsuarioBean.class);
		 	usuarioDao.createOrUpdate(usuarioBean);

		 	return isSaveUsuario;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("UsuarioDao-save.error");
		}
	}
	
	public int update(UsuarioBean usuarioBean) {
		try {
			RuntimeExceptionDao<UsuarioBean, String> usuarioDao = createDao(UsuarioBean.class);
			usuarioBean.setUsuario_id(1);
			int row = usuarioDao.update(usuarioBean);
			return row;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("usuarioService-usuarioDao-update.error");
		}
	}
	
		
	public UsuarioBean getBean(UsuarioBean usuarioBean) {
		RuntimeExceptionDao<UsuarioBean, String> usuarioDao = createDao(UsuarioBean.class);
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("usuario_id", usuarioBean.getUsuario_id());
//		fields.put("user_login", usuarioBean.getUser_login());
//		fields.put("password", usuarioBean.getPassword());
//		fields.put("ip_o_dominio_servidor", usuarioBean.getIpOrDominioServidor());
		List<UsuarioBean> usuarioList = usuarioDao.queryForFieldValues(fields);
		if(usuarioList != null && usuarioList.size() > 0){
			usuarioBean = usuarioList.get(0);
		}else{
			return null;
		}

		return usuarioBean;
	}
	
    public boolean delete(UsuarioBean usuarioBean) {
		RuntimeExceptionDao<UsuarioBean, String> usuarioDao = createDao(UsuarioBean.class);
		UsuarioBean  bean = getBean(usuarioBean);
		usuarioBean.setUsuario_id(bean.getUsuario_id());
		int 	rows = usuarioDao.deleteById("1");

		return rows>0;
    }

	public void refresh(UsuarioBean usuarioBean) {
		RuntimeExceptionDao<UsuarioBean, String> dao = createDao(UsuarioBean.class);
		dao.refresh(usuarioBean);
	}

	public int saveOrUpdate(UsuarioBean usuarioBean) {
		RuntimeExceptionDao<UsuarioBean, String> usuarioDao = createDao(UsuarioBean.class);
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("cod_usuario", "001");
		List<UsuarioBean> formatoEducacion03List = usuarioDao.queryForFieldValues(fields);
		usuarioDao.createOrUpdate(usuarioBean);
		return 0;
	}

	public UsuarioBean validarLogin(String userLogin, String password) {
		UsuarioBean usuarioBean = new UsuarioBean();
		try {
			RuntimeExceptionDao<UsuarioBean, String> usuarioDao = createDao(UsuarioBean.class);
			Map<String, Object> fields = new HashMap<String, Object>();
			fields.put("user_login", userLogin);
			fields.put("password", password);
			List<UsuarioBean> usuarioList = usuarioDao.queryForFieldValues(fields);
			if(usuarioList != null && usuarioList.size() > 0){
				usuarioBean = usuarioList.get(0);
			}else{
				return null;
			}
			return usuarioBean;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException("validarLogin.error");
		}
	}
}
