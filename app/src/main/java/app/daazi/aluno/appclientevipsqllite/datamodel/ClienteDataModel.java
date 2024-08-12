package app.daazi.aluno.appclientevipsqllite.datamodel;

// MOR - Modelo Objeto Relacional
// Transforma classes em tabelas e objetos em registros.

public class ClienteDataModel {

    /**
     *     private int id;
     *     private String primeiroNome;
     *     private String sobreNome;
     *     private String email;
     *     private String senha;
     *     private boolean pessoaFisica;
     */

    public static final String TABELA = "cliente";
    public static final String ID = "id";
    public static final String PRIMEIRO_NOME = "primeiroNome";
    public static final String SOBRENOME = "sobreNome";
    public static final String EMAIL = "email";
    public static final String SENHA = "senha";
    public static final String PESSOA_FISICA = "pessoaFisica";
    private static final String DATA_INC = "datainc";
    private static final String DATA_ALT = "dataalt";

    private static String query;

    /**
     *                 "CREATE TABLE cliente (\n" +
     *                 "id      INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
     *                 "nome    TEXT, \n" +
     *                 "email   TEXT, \n" +
     *                 "status  INTEGER, \n" +
     *                 "datainc TEXT, \n" +
     *                 "dataalt TEXT \n" +
     *                 ")";
     */

    public static String gerarTabela(){

        query = "CREATE TABLE "+TABELA+" ( ";
        query += ID+" INTEGER PRIMARY KEY AUTOINCREMENT, ";
        query += PRIMEIRO_NOME+" TEXT, ";
        query += SOBRENOME+" TEXT, ";
        query += EMAIL+" TEXT, ";
        query += SENHA+" TEXT, ";
        query += PESSOA_FISICA+" INTEGER, ";
        query += DATA_INC+" datetime default current_timestamp, ";
        query += DATA_ALT+" datetime default current_timestamp ";
        query += " )";



        return query;
    }

}
