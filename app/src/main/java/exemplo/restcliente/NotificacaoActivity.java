package exemplo.restcliente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);
        String msg = getIntent().getStringExtra("msg");

        //seta no textview
        TextView text = (TextView) findViewById(R.id.tvMensagem);
        text.setText(msg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MenuProvaActivity.class);

        startActivity(intent);
    }
}
