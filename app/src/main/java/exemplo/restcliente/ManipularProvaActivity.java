package exemplo.restcliente;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import exemplo.restcliente.adapters.SimplesAdapterListView2;
import exemplo.restcliente.beans.Questao;
import exemplo.restcliente.services.BdQuestao;
import exemplo.restcliente.utils.FileUtils;
import exemplo.restcliente.utils.IOUtils;
import exemplo.restcliente.utils.SDCardUtils;

public class ManipularProvaActivity extends AppCompatActivity {
    private int x;
    private ListView listView;
    private List<Questao> lista;
    private SimplesAdapterListView2 salv;
    private int cod,codProva;
    private String s;
    TextView tv;
    private Spinner sp1,sp2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulacao_prova);

        listView = (ListView) findViewById(R.id.listView);
        tv = (TextView) findViewById(R.id.textViewProva);
        Bundle bundle = getIntent().getExtras();
        x = bundle.getInt("ID");
        tv.setText("Prova Número "+x);
        intent2();
        sp1 = (Spinner) findViewById(R.id.spinner1); //spinners
        sp2 = (Spinner) findViewById(R.id.spinner2);     //spinners
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.Disciplina,android.R.layout.simple_spinner_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.curso,android.R.layout.simple_spinner_item);
        sp1.setAdapter(adapter);
        sp2.setAdapter(adapter2);
    }

    public void intent( View v){
        Intent i = new Intent(this,PesquisaActivity.class);
        i.putExtra("ID",x);
        startActivity(i);

    }
    public void intent2(){
        EditText etLista = (EditText) findViewById(R.id.Lista);
        BdQuestao bd = new BdQuestao(getApplicationContext());
        List<Questao> contatos = bd.getAllQuestao(x);

        String texto = "";
        for(int i = 0; i<contatos.size();i++){
            texto +=  contatos.get(i).getQueEnunciado()+"\n";
        }
        etLista.setText(texto);

    }
    public void onClickNotificacaoBig() {
        int id = 2;
        //seta a activity a ser chamada quando for clicado no evento a barra de status do android
        Intent intent = new Intent(this, NotificacaoActivity.class);
        //seta a mensagem que será exibida na activity
        intent.putExtra("msg", "A prova foi salva com sucesso na pasta ");
        //prepara a lista de mensagens
        List<String> list = new ArrayList<>();
        list.add("A prova foi salva com sucesso");
        //seta o titulo do alerta
        String contentTitle = "Nova mensagem";
        String contentText ="Você possui "+list.size()+" novas mensagens";
        NotificationUtil.createBig(this, intent, contentTitle, contentText, list, id);
    }

    public String makeText(){
        EditText edit = (EditText) findViewById(R.id.editText2);
        String str = tv.getText()+"\n" +
                "Data da prova : "+edit.getText()+"\n" +
                "Disciplina: "+sp1.getSelectedItem().toString()+"\n" +
                "Curso: "+sp2.getSelectedItem().toString()+"\n";
        return str;
    }
    public void onClicGravar(View v){
        //Escrita interna na pasta da aplicação
        gravaArquivoMemoriaExterna(
                getApplicationContext(),
                makeText(),
                tv.getText().toString()+".txt"
        );
        onClickNotificacaoBig();

        //Escrita interna na pasta da aplicação
        /*
        gravaArquivoMemoriaInterna(
                getApplicationContext(),
                etTextoW.getText().toString(),
                etNomeArquivoW.getText().toString()
        );
        */
    }
    public void gravaArquivoMemoriaInterna(Context context, String texto, String nomeArquivo){
        //cria arquivo em /data/data/exemplo.exemplogravaarquivo/files/
        File f = FileUtils.getFile(context, nomeArquivo);

        IOUtils.writeString(f,texto);
        Log.d("IFMG", "Arquivo gravado:"+f.getAbsolutePath());
    }
    public void gravaArquivoMemoriaExterna(Context context, String texto, String nomeArquivo){
        //cria arquivo privado nos downloads do sd card
        File f = SDCardUtils.getPublicFile(nomeArquivo, Environment.DIRECTORY_PICTURES);
        IOUtils.writeString(f,texto);
        Log.d("IFMG", "Arquivo gravado na pasta Downloads: "+f.getAbsolutePath());
    }



    //método listener do listView setado no onCreate
    public void onItemClick(AdapterView<?> parent, View view, int idx, long id) {
        s = (String) parent.getAdapter().getItem(idx); // Objeto selecionado, que neste caso era de um array de strings
        cod = idx;
        showMessage("Adicionar","Você deseja adicionar a questão a prova?: "+idx,"Adicionar","Cancelar");
    }

    //método que printa um listview na tela
    public void showMessage(String titulo, String mensagem, final String btUm, final String btDois) {
        AlertDialog alerta;
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle(titulo);
        //define a mensagem
        builder.setMessage(mensagem);
        //define um botão  como positivo
        builder.setPositiveButton(btUm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                Toast.makeText(ManipularProvaActivity.this, "Adicionado com sucesso! "+s, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton(btDois, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //execcutando botão um
                Toast.makeText(ManipularProvaActivity.this, "Adição cancelada!", Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent iv = new Intent(this,MenuProvaActivity.class);
        startActivity(iv);
    }
}
