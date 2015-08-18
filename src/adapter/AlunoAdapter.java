package adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.cadastro.Aluno;
import br.com.cadastro.R;

public class AlunoAdapter extends BaseAdapter {

	private List<Aluno> alunos;
	private Activity activity;

	public AlunoAdapter(List<Aluno> alunos, Activity activity) {
		this.alunos = alunos;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return alunos.size();
	}

	@Override
	public Object getItem(int position) {
		return alunos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return alunos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Aluno aluno = alunos.get(position);
		
		LayoutInflater inflater = activity.getLayoutInflater();
		View linha = inflater.inflate(R.layout.item, null);
		
		TextView nome = (TextView) linha.findViewById(R.id.nome);
		nome.setText(aluno.getNome());
		
		ImageView foto = (ImageView) linha.findViewById(R.id.foto);
		
		if(aluno.getCaminhoFoto() != null){
			Bitmap imagem = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
			Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);
			
			foto.setImageBitmap(imagemReduzida);
		}else{
			foto.setImageResource(R.drawable.ic_no_image);
		}
		
		return linha;
	}
}
