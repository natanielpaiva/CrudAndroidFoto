package br.com.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

public class FormularioHelper {
	
	private EditText campoNome;
	private EditText campoSite;
	private EditText campoEndereco;
	private EditText campoTelefone;
	private RatingBar campoNota;
	private Aluno aluno;
	private ImageView foto;

	public FormularioHelper(FormularioActivity activity) {
		aluno = new Aluno();
		
		campoNome = (EditText) activity.findViewById(R.id.nome);
		campoSite = (EditText) activity.findViewById(R.id.site);
		campoEndereco = (EditText) activity.findViewById(R.id.endereco);
		campoTelefone = (EditText) activity.findViewById(R.id.telefone);
		campoNota = (RatingBar) activity.findViewById(R.id.nota);
	    foto = (ImageView) activity.findViewById(R.id.foto);
	}
	
	public Aluno getAlunoFormulario(){
		
		String nome = campoNome.getText().toString();
		String site = campoSite.getText().toString();
		String endereco = campoEndereco.getText().toString();
		String telefone = campoTelefone.getText().toString();
		int nota = campoNota.getProgress();
		
		aluno.setNome(nome);
		aluno.setSite(site);
		aluno.setEndereco(endereco);
		aluno.setTelefone(telefone);
		aluno.setNota(Double.valueOf(nota));
		
		return aluno;
	}

	public void popularAluno(Aluno alunoEdit) {
		aluno = alunoEdit;
		campoNome.setText(alunoEdit.getNome());
		campoTelefone.setText(alunoEdit.getTelefone());
		campoEndereco.setText(alunoEdit.getEndereco());
		campoSite.setText(alunoEdit.getSite());
		campoNota.setRating(alunoEdit.getNota().floatValue());
		
		if(aluno.getCaminhoFoto() != null){
			carregaImagem(aluno.getCaminhoFoto());
		}
		
	}
	
	public ImageView getFoto(){
		return foto;
	}
	
	public void carregaImagem(String caminhoArquivo){
		aluno.setCaminhoFoto(caminhoArquivo);
		Bitmap bm = BitmapFactory.decodeFile(caminhoArquivo);
		Bitmap imagemReduzida = Bitmap.createScaledBitmap(bm, 100, 100, true);
		foto.setImageBitmap(imagemReduzida);
	}

}
