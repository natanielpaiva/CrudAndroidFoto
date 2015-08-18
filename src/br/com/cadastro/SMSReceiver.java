package br.com.cadastro;

import br.com.cadastro.DAO.AlunoDAO;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
			Object[] mensagens = (Object[]) intent.getExtras().get("pdus");
			byte[]  mensagem = (byte[]) mensagens[0];
			
			SmsMessage sms = SmsMessage.createFromPdu(mensagem);
			String telefone = sms.getOriginatingAddress();
			
			boolean smsDeAluno = new AlunoDAO(context).ehAluno(telefone);
			
			if(smsDeAluno){
				//MediaPlayer musica = MediaPlayer.create(context, R.row.musica);
				//musica.start();
				Toast.makeText(context, "Chegou uma mensagem de aluno do telefone:" + telefone, Toast.LENGTH_SHORT).show();
			}
			
	}

}
