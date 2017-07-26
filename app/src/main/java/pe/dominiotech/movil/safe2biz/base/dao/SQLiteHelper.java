package pe.dominiotech.movil.safe2biz.base.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.sql.SQLException;

public class SQLiteHelper extends OrmLiteSqliteOpenHelper{

	protected Context context;

	public SQLiteHelper(Context context, String name, CursorFactory factory, int version ) {
		super(context, name, factory, version);
		this.context = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		AssetManager assetManager = context.getAssets();
		try {
			InputStream intialScript = assetManager.open("db/schema_sqlite.sql");
			String[] sentences = getSentences(intialScript);
			for (String sentence : sentences) {
				if(sentence == null || sentence.trim().equals("")){
					continue;
				}
				db.execSQL(sentence);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void updateVersion() {
		SQLiteDatabase db = getWritableDatabase();
		updateVersion(db);
	}
	
	private void updateVersion(SQLiteDatabase db) {
		String versionTop = "1.0.0";
	    try {
	    	versionTop = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
	    } catch (NameNotFoundException e) {
	    }
		Cursor cursor = db.rawQuery("SELECT * FROM T_PARAMETRO WHERE x_codigo = 'VERSION_APP'", null);
		cursor.moveToNext();
		String currentVersion = cursor.getString(cursor.getColumnIndex("x_valor"));
		currentVersion = currentVersion + ".sql";
		AssetManager assetManager = context.getAssets();
	    String fileList[];
		try {
			fileList = assetManager.list("db/version");
			if (fileList != null){   
		    	for (String fileName : fileList) {
		    		if((versionTop+".sql").compareTo(fileName) >= 0  && fileName.compareTo(currentVersion) > 0){
		    			InputStream intialScript = assetManager.open("db/version/" + fileName);
		    			String[] sentences = getSentences(intialScript);
		    			for (String sentence : sentences) {
		    				if(sentence == null || sentence.trim().equals("")){
		    					continue;
		    				}
		    				db.execSQL(sentence);
		    			}
		    		}
				}			 
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		ContentValues valores = new ContentValues();
		valores.put("x_valor",versionTop);
		String[] args = new String[]{"VERSION_APP"};
		db.update("T_PARAMETRO", valores, "x_codigo = ?", args);
	}

	private String[] getSentences(InputStream is) throws IOException {
		String scriptAsString = parseSqlIn(is);
		return scriptAsString.split(";");
	}
	
	private String parseSqlIn(InputStream is) throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringWriter sw = new StringWriter();
			BufferedWriter writer = new BufferedWriter(sw);
			for (int c=reader.read(); c != -1; c=reader.read()) {
				writer.write(c);
			}
			writer.flush();
			return sw.toString();
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource
			connectionSource, int oldVersion, int newVersion) {
	}
	
	public void init() {
		close();
	}
	
	public <T,ID> RuntimeExceptionDao<T, ID> createDao(Class<T> daoClass)  {
		try {
			Dao<T, ID> dao = DaoManager.createDao(getConnectionSource(), daoClass);
			RuntimeExceptionDao<T, ID> accountDao =  new RuntimeExceptionDao<T, ID>(dao);
			return accountDao;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQLiteHelper.createDao");
		}
	}
}
