package exemplo.restcliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import exemplo.restcliente.adapters.SimplesAdapterListView1;
import exemplo.restcliente.beans.Questao;
import exemplo.restcliente.services.BdQuestao;
import exemplo.restcliente.sinc.JSONDados;
import exemplo.restcliente.sinc.JSONParser;

import static exemplo.restcliente.sinc.JSONDados.URL_SERVICO2;

public class PesquisaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<String> lista = new ArrayList<String>();
    private SimplesAdapterListView1 salv;
    private int cod,codProva;
    private String s;
    private Spinner sp1,sp2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);
        listView = (ListView) findViewById(R.id.listView);
        salv = new SimplesAdapterListView1(this);
        salv.setLista(lista);//atribuindo a lista vindo da principal no adapter
        sp1 = (Spinner) findViewById(R.id.spinnerDisciplina); //spinners
        sp2 = (Spinner) findViewById(R.id.spinnerTema);     //spinners
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.Disciplina,android.R.layout.simple_spinner_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.Tema,android.R.layout.simple_spinner_item);
        sp1.setAdapter(adapter);
        sp2.setAdapter(adapter2);
        //setando adapter criado
        listView.setAdapter(salv);
        //setando o lisener nesta classe
        listView.setOnItemClickListener(this);
        Bundle bundle = getIntent().getExtras();
        codProva = bundle.getInt("ID");
        Toast.makeText(this,String.valueOf(codProva) , Toast.LENGTH_SHORT).show();
    }

    public void onClickRes(View v) {
        //chama o metodo de requisição passando o json como parametro e a URL do webservice
        requisitaPost(
                JSONDados.geraJsonQuestao(
                        sp1.getSelectedItem().toString(),sp2.getSelectedItem().toString()

                ),
                URL_SERVICO2
        );
        Toast.makeText(this, sp1.getSelectedItem().toString()+" "+ sp2.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

    }
    public void requisitaPost(final String parametroJSON, final String URL_) {


        //thread obrigatória para realização da requisição pode ser usado com outras formas de thread
        new Thread(new Runnable() {
            public void run() {
                JSONParser jsonParser = new JSONParser();
                JSONObject json = null;
                try {
                    //prepara parâmetros para serem enviados via método POST
                    HashMap<String, String> params = new HashMap<>();
                    params.put("dados", parametroJSON);

                    Log.d("[IFMG]", parametroJSON);
                    Log.d("[IFMG]", "JSON Envio Iniciando...");

                    //faz a requisição POST e retorna o que o webservice REST envoiu dentro de json
                    json = jsonParser.makeHttpRequest(URL_, "POST", params);

                    Log.d("[IFMG]", " JSON Envio Terminado...");

                    //Mostra no log e retorna o que o json retornou, caso não retornou nulo
                    if (json != null) {
                        Log.d("[IFMG]", json.toString());
                        //return json;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("[IFMG]", "finalizando baixar defeitos");

                //----------------------------------------------
                //PÓS DOWNLOAD
                //----------------------------------------------

                //teste para ferificar se o json chegou corretamente e foi interpretado
                if (json != null) {
                    //------------------------------------------------------------
                    //AQUI SE PEGA O JSON RETORNADO E TRATA O QUE DEVE SER TRATADO
                    //------------------------------------------------------------
                    final ArrayList<String> resp = interpretaJSON_Aritimetica(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lista = resp;
                            atualizaListView();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Falha na conexão!!!", Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        }).start();
    }
    public ArrayList<String> interpretaJSON_Aritimetica(JSONObject json) {
        String texto = "";
        ArrayList<String> textos = new ArrayList<>();
        try {
            JSONArray linhas = null;
            //Printando na string os elementos identificados nela
            try {
                linhas = (JSONArray) json.get("pesquisar");//pega vetor do json recebido
                if (linhas.length() > 0) {//verifica we exite algum registro recebido do servidor
                    for (int i = 0; i < linhas.length(); i++) {
                        JSONObject linha = (JSONObject) linhas.get(i);
                        textos.add(i,linha.getString("pesquisar0"));
                        textos.add(i,linha.getString("pesquisar1"));
                        textos.add(i,linha.getString("pesquisar2"));
                        textos.add(i,linha.getString("pesquisar3"));
                        textos.add(i,linha.getString("pesquisar4"));
                        Log.d("[IFMG]", "resultado: " + texto);
                    }
                }
            } catch (Exception c) {
                c.printStackTrace();
                Log.d("[IFMG]", "Erro: " + c.getMessage());
            }
        } catch (Exception e) {//JSONException e) {
            e.printStackTrace();
        }
        return textos;
    }

    public void atualizaListView(){
        salv.setLista(lista);
        listView.setAdapter(salv);
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
                Questao q1 = new Questao();

                q1.setQueEnunciado(lista.get(cod));
                q1.setQueProva(codProva);

                BdQuestao bd = new BdQuestao(getApplicationContext());
                bd.insertPessoa(q1);
                Toast.makeText(PesquisaActivity.this, "Adicionado com sucesso! "+s, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton(btDois, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //execcutando botão um
                Toast.makeText(PesquisaActivity.this, "Adição cancelada!", Toast.LENGTH_SHORT).show();
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
        Intent i = new Intent(this,ManipularProvaActivity.class);
        i.putExtra("ID",codProva);
        startActivity(i);
    }
}
