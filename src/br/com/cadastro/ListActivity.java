package br.com.cadastro;

import java.util.List;

import adapter.AlunoAdapter;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.cadastro.DAO.AlunoDAO;

public class ListActivity extends Activity {

	private ListView lista;
	private AlunoDAO dao;
	private Aluno aluno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_alunos);

		lista = (ListView) findViewById(R.id.lista);
		
		registerForContextMenu(lista);
		
		dao = new AlunoDAO(this);
		
		this.refreshAlunos();

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
//				Toast.makeText(ListActivity.this, "A posição é " + position,
//						Toast.LENGTH_SHORT).show();
				Aluno alunoEdit = (Aluno) adapter.getItemAtPosition(position);
				
				Intent irParaFormulario = new Intent(ListActivity.this, FormularioActivity.class );
				irParaFormulario.putExtra("alunoEdit", alunoEdit);
				startActivity(irParaFormulario);

			}

		});

		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				aluno = (Aluno) adapter.getItemAtPosition(position);
				return false;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.refreshAlunos();
	}

	public void refreshAlunos() {
		List<Aluno> alunos = dao.getLista();

		AlunoAdapter adapter = new AlunoAdapter(alunos, this);

		lista.setAdapter(adapter);
		
		getLayoutInflater();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_lista, menu);
		;
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_novo:
			Intent irParaFormulario = new Intent(this, FormularioActivity.class);
			startActivity(irParaFormulario);
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		MenuItem navegarNoSite = menu.add("Navegar no Site");
		
		navegarNoSite.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				Intent abrirSite = new Intent(Intent.ACTION_VIEW);
				Uri siteAluno = Uri.parse("http://"+aluno.getSite());
				abrirSite.setData(siteAluno);
				startActivity(abrirSite);
				return false;
			}
		});
		
		
		MenuItem ligar = menu.add("Ligar");
		
		ligar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irParaTelaDeDiscar = new Intent(Intent.ACTION_CALL);
				Uri telefone = Uri.parse("tel:"+aluno.getTelefone());
				irParaTelaDeDiscar.setData(telefone);
				startActivity(irParaTelaDeDiscar);
				return false;
			}
		});
		
		
		MenuItem deletar = menu.add("Deletar");
		
		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				dao.deletar(aluno);
				dao.close();
				refreshAlunos();
				return false;
			}
		});
		
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}
}
