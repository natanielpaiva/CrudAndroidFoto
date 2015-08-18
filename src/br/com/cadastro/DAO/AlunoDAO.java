package br.com.cadastro.DAO;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.cadastro.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {

	private static final String DATABASE = "dbCadastro";
	private static final int VERSION = 2;
	private static final String TABLE = "Aluno";

	public AlunoDAO(Context context) {
		super(context, DATABASE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE + " ("
				+ "id INTEGER PRIMARY KEY, "
				+ "nome TEXT NOT NULL, "
				+ "telefone TEXT, " 
				+ "endereco TEXT, " 
				+ "site TEXT, "
				+ "caminhoFoto TEXT, "
				+ "nota REAL )";

		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		String sql = "DROP TABLE IF EXISTS " + TABLE;
		db.execSQL(sql);
		onCreate(db);
	}

	public void insere(Aluno aluno) {
		ContentValues cv = new ContentValues();

		cv.put("nome", aluno.getNome());
		cv.put("telefone", aluno.getTelefone());
		cv.put("site", aluno.getSite());
		cv.put("endereco", aluno.getEndereco());
		cv.put("nota", aluno.getNota());
		cv.put("caminhoFoto", aluno.getCaminhoFoto());

		getWritableDatabase().insert(TABLE, null, cv);

	}

	public List<Aluno> getLista() {

		List<Aluno> alunos = new ArrayList<Aluno>();

		String sql = "SELECT * FROM " + TABLE + ";";
		Cursor c = getReadableDatabase().rawQuery(sql, null);

		while (c.moveToNext()) {
			Aluno aluno = new Aluno();
			aluno.setId(c.getLong(c.getColumnIndex("id")));
			aluno.setNome(c.getString(c.getColumnIndex("nome")));
			aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
			aluno.setSite(c.getString(c.getColumnIndex("site")));
			aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
			aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
			aluno.setNota(c.getDouble(c.getColumnIndex("nota")));

			alunos.add(aluno);
		}

		return alunos;
	}

	public void deletar(Aluno aluno) {
		String[] args = { aluno.getId().toString() };

		getWritableDatabase().delete(TABLE, "id=?", args);

	}

	public void editar(Aluno aluno) {
		ContentValues cv = new ContentValues();

		cv.put("nome", aluno.getNome());
		cv.put("telefone", aluno.getTelefone());
		cv.put("site", aluno.getSite());
		cv.put("endereco", aluno.getEndereco());
		cv.put("caminhoFoto", aluno.getCaminhoFoto());
		cv.put("nota", aluno.getNota().toString());

		String[] args = {aluno.getId().toString()};
		getWritableDatabase().update(TABLE, cv, "id=?", args );
	}
	
	public boolean ehAluno(String telefone){
		String[] args = {telefone};
		String sql = "select id from " + TABLE + " where telefone = ?";
		Cursor cursor = getReadableDatabase().rawQuery(sql, args);
		return cursor.moveToFirst();
	}

}
