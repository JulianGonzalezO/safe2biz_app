package pe.dominiotech.movil.safe2biz.base.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import pe.dominiotech.movil.safe2biz.MainApplication;
import pe.dominiotech.movil.safe2biz.model.UsuarioBean;

public class SynchronizeDao extends SQLiteHelper {
	protected static final String TAG = SynchronizeDao.class.getSimpleName();
	private MainApplication mainApplication;
	//TODO: Capturar
	public SynchronizeDao(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	public void limpiarBaseDeDatos(String codEquipo) {
		RuntimeExceptionDao<UsuarioBean, String> daoUsuario = createDao(UsuarioBean.class);
		try {
			DeleteBuilder<UsuarioBean, String> deleteUsuarioBuilder = daoUsuario.deleteBuilder();
			deleteUsuarioBuilder.where().eq("cod_usuario", codEquipo);
			deleteUsuarioBuilder.delete();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void guardarDatosUsuario(final List<UsuarioBean> usuarioBeans) {

		if (usuarioBeans == null) {
			return;
		}
		try {
			final RuntimeExceptionDao<UsuarioBean, String> dao = createDao(UsuarioBean.class);
			 DeleteBuilder<UsuarioBean, String> deleteBuilder = dao.deleteBuilder();

			dao.callBatchTasks(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					for (UsuarioBean usuarioBean : usuarioBeans) {
						// escuelaBean.setSincronizar(1);
						dao.createOrUpdate(usuarioBean);
					}
					return null;
				}
			});

		} catch (android.database.SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Se produjo una excepcion en el proceso de Sincronizacion");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Se produjo una excepcion en el proceso de Sincronizacion");

		}
	}
	
}
