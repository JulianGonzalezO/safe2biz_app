package pe.dominiotech.movil.safe2biz.service;

import pe.dominiotech.movil.safe2biz.base.dao.UsuarioDao;
import pe.dominiotech.movil.safe2biz.model.UsuarioBean;

public class UsuarioService extends AppService{
	
	private UsuarioDao usuarioDao;

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
	public int save(UsuarioBean usuarioBean) {
		return usuarioDao.save(usuarioBean);
	}
	
	public int update(UsuarioBean usuarioBean) {
		return usuarioDao.update(usuarioBean);
	}
	
	public boolean delete(UsuarioBean usuarioBean) {
		return usuarioDao.delete(usuarioBean);
	}
	
	public int saveOrUpdate(UsuarioBean usuarioBean){
		return usuarioDao.saveOrUpdate(usuarioBean);
	}
	
	public UsuarioBean getBean(UsuarioBean usuarioBean){
		return usuarioDao.getBean(usuarioBean);
	}

	public UsuarioBean validarLogin(String userLogin, String password){
		return usuarioDao.validarLogin(userLogin, password);
	}
}
