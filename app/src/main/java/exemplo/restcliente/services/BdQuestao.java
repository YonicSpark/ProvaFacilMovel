package exemplo.restcliente.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import exemplo.restcliente.beans.Questao;


/**
 * Classe responsável por manipular o banco de dados na tabela específica
 */
public class BdQuestao {

    public SQLiteDatabase db,dbr;

    public BdQuestao(Context context) {
        //objeto obrigatório para todas as classes
        BdCore auxBd = new BdCore(context);

        //acesso para escrita no bd
        db = auxBd.getWritableDatabase();
        //acesso para leitura do bd
        dbr = auxBd.getReadableDatabase();
    }


    /**
     * Método para inserir
     */
    public long insertPessoa(Questao q) {
        ContentValues values = new ContentValues();
        values.put("queEnunciado", q.getQueEnunciado());
        values.put("queProva",q.getQueProva());

        //inserindo diretamente na tabela pessoa sem a necessidade de script sql
        return db.insert("Questao", null, values);
    }

    /**
     * Método para deletar
     */
    public void deleteAllPessoa() {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        // deleta todas informações da tabela usando script sql
        db.execSQL("DELETE FROM Questao;");
    }

    /**
     * Deletar por codigo
     */
    public void deletePessoa(int pesCodigo) {
        // Auxiliar para preencher o banco
        ContentValues values = new ContentValues();

        /// Converte paramentro para string
        String args[] = new String[]{pesCodigo+""};

        // Delete query
        db.delete("Questao",// Nome da tabela
                "queCodigo=?",// Coluna da tabela
                args); // Argumentos de delete

    }

    /**
     * Listar
     *
     * @return
     */
    public List<Questao> listarQuestoes() {
        // Cria lista
        List<Questao> listaQuestao = new LinkedList<Questao>();
        // Query do banco
        String query = "SELECT * FROM Questao";
        // Cria o cursor e executa a query
        Cursor cursor = db.rawQuery(query, null);
        // Percorre os resultados
        // Se o cursor pode ir ao primeiro
        if (cursor.moveToFirst()) do {
            // Cria novo , cada vez que entrar aqui
            Questao q = new Questao();
            // Define os campos da configuracao, pegando do cursor pelo id da coluna
            q.setQueCodigo(cursor.getInt(0));
            q.setQueEnunciado(cursor.getString(1));
            q.setQueProva(cursor.getInt(2));

            // Adiciona as informacoes
            listaQuestao.add(q);
        }
        while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        // Retorna a lista
        return listaQuestao;
    }

    public List<Questao> getAllQuestao(int queProva) {
        List<Questao> telList = new ArrayList<Questao>();

        // Select All Query
        String selectQuery = "SELECT  * FROM  Questao WHERE queProva = "+queProva+";";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding the objs to list
        if (cursor.moveToFirst()) {
            do {
                Questao tel = new Questao();
                tel.setQueCodigo(cursor.getInt(0));
                tel.setQueEnunciado(cursor.getString(1));
                tel.setQueProva(cursor.getInt(2));
                telList.add(tel);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return telList;
    }

    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM  questao;";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    public List<Questao> buscarPessoa(Integer cod) {
        // Cria lista
        List<Questao> listaPessoa = new LinkedList<Questao>();
        // Query do banco
        Cursor cursor = db.query("Questao",
                new String[]{"queCodigo", "queEnunciado"},
                "queCodigo=?", new String[]{String.valueOf(cod)},
                null, null, null);
        // Percorre os resultados
        if (cursor.moveToFirst()) {// Se o cursor pode ir ao primeiro
            do {
                // Cria novo , cada vez que entrar aqui
                Questao q = new Questao();
                // Define os campos da configuracao, pegando do cursor pelo id da coluna
                q.setQueCodigo(cursor.getInt(0));
                q.setQueEnunciado(cursor.getString(1));
                q.setQueProva(cursor.getInt(2));

                // Adiciona as informacoes
                listaPessoa.add(q);
            }
            while (cursor.moveToNext()); // Enquanto o usuario pode mover para o proximo ele executa esse metodo
        }
        // Retorna a lista
        return listaPessoa;
    }

    //-------------------------------------------------------------------------
    public List<Questao> getAllSql(){
        return findBySql("SELECT * FROM Questao;");
    }
    // Consulta por sql s
    public List<Questao> findBySql(String sql) {
        //SQLiteDatabase db = getReadableDatabase();
        Log.d("[IFMG]", "SQL: " + sql);
        try {
            Log.d("[IFMG]", "Vai consultar");
            Cursor c = dbr.rawQuery(sql, null);
            Log.d("[IFMG]", "Consultou...");
            return toList(c);
        } finally {

            //dbr.close();
        }
    }

    // Lê o cursor e cria a lista de coatatos
    private List<Questao> toList(Cursor c) {
        List<Questao> contatos = new ArrayList<Questao>();
        Log.d("IFMG", "Identifica Cursor...");
        if (c.moveToFirst()) {
            do {
                Questao contato = new Questao();
                contatos.add(contato);

                // recupera os atributos de contatos
                contato.setQueCodigo(c.getInt(c.getColumnIndex("queCodigo")));
                contato.setQueEnunciado(c.getString(c.getColumnIndex("queEnunciado")));
                contato.setQueProva(c.getInt(c.getColumnIndex("queProva")));
            } while (c.moveToNext());
        }
        return contatos;
    }

    /**
     * método que somente executa sql de manipulação de dados
     * @param sql
     */
    public void executeSQL(String sql) {
        try {
            Log.d("IFMG", "Executando: " + sql);
            db.execSQL(sql);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

}
