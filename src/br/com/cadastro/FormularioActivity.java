package br.com.cadastro;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.cadastro.DAO.AlunoDAO;

public class FormularioActivity extends Activity {

	private FormularioHelper helper;
	private Aluno alunoEdit;
	private String caminhoFoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);

		final Button salvar = (Button) findViewById(R.id.btnSalvar);

		helper = new FormularioHelper(this);

		alunoEdit = (Aluno) getIntent().getSerializableExtra("alunoEdit");
		if(alunoEdit != null){
			helper.popularAluno(alunoEdit);
			salvar.setText("Alterar");
		}

		salvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Aluno aluno = helper.getAlunoFormulario();
				AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
				if (alunoEdit != null) {
					aluno.setId(alunoEdit.getId());
				    dao.editar(aluno);
				} else {
					dao.insere(aluno);
				}
				dao.close();
				Toast.makeText(FormularioActivity.this, "Registro salvo com sucesso!",
						Toast.LENGTH_SHORT).show();

				finish();

			}
		});
		
		ImageView foto = helper.getFoto();
		
	    foto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				caminhoFoto = getExternalFilesDir(null) + "/" +System.currentTimeMillis()+ ".png";
				File arquivo = new File(caminhoFoto);
				
				Uri localFoto = Uri.fromFile(arquivo);
				
				camera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
				
				startActivityForResult(camera, 156);
			}
		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == 156){
			if(resultCode == Activity.RESULT_OK){
				helper.carregaImagem(caminhoFoto);
			}else{
				caminhoFoto = null;
			}
			
		}
		
//		super.onActivityResult(requestCode, resultCode, data);
	}

}
