package app.daazi.aluno.appclientevipsqllite.controller;


import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import java.util.List;

import app.daazi.aluno.appclientevipsqllite.api.AppDataBase;
import app.daazi.aluno.appclientevipsqllite.datamodel.ClienteDataModel;
import app.daazi.aluno.appclientevipsqllite.datamodel.ClientePFDataModel;
import app.daazi.aluno.appclientevipsqllite.model.Cliente;
import app.daazi.aluno.appclientevipsqllite.model.ClientePF;

public class ClientePFController extends AppDataBase {

    private static final String TABELA = ClientePFDataModel.TABELA;
    private ContentValues dados;


    public ClientePFController(@Nullable Context context) {
        super(context);
    }

    public boolean incluir(ClientePF obj) {

        dados = new ContentValues();

        dados.put(ClientePFDataModel.FK, obj.getClienteID());
        dados.put(ClientePFDataModel.NOME_COMPLETO, obj.getNomeCompleto());
        dados.put(ClientePFDataModel.CPF, obj.getCpf());

        return insert(TABELA, dados);
    }

    public boolean alterar(ClientePF obj) {

        dados = new ContentValues();

        dados.put(ClientePFDataModel.ID, obj.getId());
        dados.put(ClientePFDataModel.NOME_COMPLETO, obj.getNomeCompleto());
        dados.put(ClientePFDataModel.CPF, obj.getCpf());

        return update(TABELA, dados);
    }

    public boolean deletar(ClientePF obj) {

        return delete(TABELA, obj.getId());
    }

    public List<ClientePF> listar() {

        return listClientesPessoaFisica(TABELA);
    }

    public int getUltimoID(){

        return getLastPK(TABELA);
    }

}
