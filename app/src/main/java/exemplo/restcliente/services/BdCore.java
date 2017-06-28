package exemplo.restcliente.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * Neste arquivo estão os códigos de criação de todo o banco de daodos.
 */
public class BdCore extends SQLiteOpenHelper {

    private static final String Name_BD = "Provas.db";
    private static final int Versao_BD = 1;

    public BdCore(Context context) {
        super(context, Name_BD, null, Versao_BD);
    }

    public boolean checkDataBase(Context context) {
        File database = context.getDatabasePath(Name_BD);
        if (!database.exists()) {
            Log.i("IFMG", "BD não existente");
            return false;
        } else {
            Log.i("IFMG", "BD não existente");
            return true;
        }
    }

    /**
     * Chamando os metodos de criacao das tabelas
     *
     * @param bd
     */
    @Override
    public void onCreate(SQLiteDatabase bd) {
        //neste local são chamados os métodos que criam as tabelas
        criarTabelaProva(bd);
        criarTabelaQuestao(bd);

    }

    /**
     * Create Pessoa.
     * Cria tabela no banco local;
     * Chamada no metodo onCreate desta classe.
     *
     * @param bd = Nome do banco de dados.
     */
    public void criarTabelaProva(SQLiteDatabase bd) {
        String slqCreateTabelaDefeito = "CREATE TABLE `Prova` (\n" +
                "\t`proCodigo`\tINTEGER NOT NULL,\n" +
                "\t`proData`\tTEXT,\n" +
                "\t`proDisciplina`\tTEXT,\n" +
                "\t`proCurso`\tTEXT,\n" +
                "\tPRIMARY KEY(`proCodigo`)\n" +
                ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaDefeito);
    }

    /**
     * Create telefone.
     * Cria tabela no banco local;
     * Chamada no metodo onCreate desta classe.
     *
     * @param bd = Nome do banco de dados.
     */
    public void criarTabelaQuestao(SQLiteDatabase bd) {
        String slqCreateTabelaEstabelecimento = "CREATE TABLE `Questao` (\n" +
                "\t`queCodigo`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`queEnunciado`\tTEXT,\n" +
                "\t`queProva`\tINTEGER\n" +
                ");";
        // Executa a query passada como parametro
        bd.execSQL(slqCreateTabelaEstabelecimento);
    }


    /**
     * Upgrade banco.
     * Sistema chama automaticamente quando a versão do banco é alterada;
     * Realiza o drop e create das tabelas.
     *
     * @param bd         = Banco de dados;
     * @param oldVersion = Versao antiga do banco;
     * @param newVersion = Nova versao do banco.
     */
    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {

        //TABELA Telefone
        upgradeTabelaTelefone(bd, oldVersion, newVersion);
        criarTabelaProva(bd);

        //TABELA Pessoa
        upgradeTablePessoa(bd, oldVersion, newVersion);
        criarTabelaQuestao(bd);
    }

    /******************************************************************************
     * UPGRADES DAS TABELAS, COMANDOS PARA DELETÁ-LAS CASO UMA VERSÃO NOVA DO BANCO DE DADOS ESTEJA NO CÓDIGO
     */
    private void upgradeTablePessoa(SQLiteDatabase bd, int oldVersion, int newVersion) {
        //Drop da tabela
        String sqlDropTabelaDTC = "DROP TABLE Prova";
        //Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaDTC);
    }

    private void upgradeTabelaTelefone(SQLiteDatabase bd, int oldVersion, int newVersion) {
        // Drop da tabela
        String sqlDropTabelaDefeito = "DROP TABLE Questao";
        // Executa a query passada como parametro
        bd.execSQL(sqlDropTabelaDefeito);
    }


}


