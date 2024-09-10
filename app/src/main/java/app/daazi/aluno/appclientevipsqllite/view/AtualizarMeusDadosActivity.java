package app.daazi.aluno.appclientevipsqllite.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;

import app.daazi.aluno.appclientevipsqllite.R;
import app.daazi.aluno.appclientevipsqllite.api.AppUtil;
import app.daazi.aluno.appclientevipsqllite.controller.ClienteController;
import app.daazi.aluno.appclientevipsqllite.controller.ClientePFController;
import app.daazi.aluno.appclientevipsqllite.controller.ClientePJController;
import app.daazi.aluno.appclientevipsqllite.model.Cliente;

public class AtualizarMeusDadosActivity extends AppCompatActivity {

    // Card Cliente
    EditText editPrimeiroNome, editSobrenome;
    CheckBox ckPessoaFisica;
    Button btnEditarCardCliente, btnSalvarCardCliente;


    // Card Cliente PF
    EditText editCpf, editNomeCompleto;
    Button btnEditarCardPF, btnSalvarCardPF;


    // Card Cliente PJ
    EditText editCnpj, editRazaoSocial, editDataAbertura;
    CheckBox ckSimplesNacional, ckMei;
    Button btnEditarCardPJ, btnSalvarCardPJ;


    // Card Credenciais
    EditText  editEmail, editSenhaA;
    Button btnEditarCredenciais, btnSalvarCredenciais;


    Cliente cliente;
    ClienteController controller;
    ClientePFController controllerPF;
    ClientePJController controllerPJ;

    SharedPreferences preferences;

    int clienteID;
    boolean isPessoaFisica, isSimplesNacional, isMei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_meus_dados);

        restaurarSharedPreferences();

        initFormulario();

        popularFormulario();
    }

    private void restaurarSharedPreferences() {

        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        isPessoaFisica = preferences.getBoolean("pessoaFisica", true);
        clienteID = preferences.getInt("clienteID", -1);
    }

    private void initFormulario() {

        editPrimeiroNome = findViewById(R.id.editPrimeiroNome);
        editSobrenome = findViewById(R.id.editSobrenome);
        ckPessoaFisica = findViewById(R.id.ckPessoaFisica);
        btnEditarCardCliente = findViewById(R.id.btnEditarCardCliente);
        btnSalvarCardCliente = findViewById(R.id.btnSalvarCardCliente);

        editCpf = findViewById(R.id.editCpf);
        editNomeCompleto = findViewById(R.id.editNomeCompleto);
        btnEditarCardPF = findViewById(R.id.btnEditarCardPF);
        btnSalvarCardPF = findViewById(R.id.btnSalvarCardPF);

        editCnpj = findViewById(R.id.editCnpj);
        editRazaoSocial = findViewById(R.id.editRazaoSocial);
        editDataAbertura = findViewById(R.id.editDataAbertura);
        ckSimplesNacional = findViewById(R.id.ckSimplesNacional);
        ckMei = findViewById(R.id.ckMei);
        btnEditarCardPJ = findViewById(R.id.btnEditarCardPJ);
        btnSalvarCardPJ = findViewById(R.id.btnSalvarCardPJ);

        editEmail = findViewById(R.id.editEmail);
        editSenhaA = findViewById(R.id.editSenhaA);
        btnEditarCredenciais = findViewById(R.id.btnEditarCredenciais);
        btnSalvarCredenciais = findViewById(R.id.btnSalvarCredenciais);

        cliente = new Cliente();
        cliente.setId(clienteID);

        controller = new ClienteController(this);
        controllerPF = new ClientePFController(this);
        controllerPJ = new ClientePJController(this);
    }

    private void popularFormulario() {

        if (clienteID>=1){

            cliente = controller.getClienteByID(cliente);
            cliente.setClientePF(controllerPF.getClientePFByFK(cliente.getId()));

            if (!cliente.isPessoaFisica())
                cliente.setClientePJ(controllerPJ.getClientePJByFK(cliente.getClientePF().getId()));

            // Dados Classe Cliente
            editPrimeiroNome.setText(cliente.getPrimeiroNome());
            editSobrenome.setText(cliente.getSobreNome());
            ckPessoaFisica.setChecked(cliente.isPessoaFisica());

            // Dados Classe ClientePF
            editCpf.setText(cliente.getClientePF().getCpf());
            editNomeCompleto.setText(cliente.getClientePF().getNomeCompleto());

            // Dados Classe ClientePJ
            if (!cliente.isPessoaFisica()) {

                editCnpj.setText(cliente.getClientePJ().getCnpj());
                editRazaoSocial.setText(cliente.getClientePJ().getRazaoSocial());
                editDataAbertura.setText(cliente.getClientePJ().getDataAbertura());
                ckSimplesNacional.setChecked(cliente.getClientePJ().isSimplesNacional());
                ckMei.setChecked(cliente.getClientePJ().isMei());
            }

            // Dados Classe Credenciais do Cliente
            editEmail.setText(cliente.getEmail());
            editSenhaA.setText(cliente.getSenha());

        }else{

            FancyAlertDialog.Builder
                    .with(AtualizarMeusDadosActivity.this)
                    .setTitle("ATENÇÃO!!!!")
                    .setBackgroundColor(Color.parseColor("#303F9F"))  // for @ColorRes use setBackgroundColorRes(R.color.colorvalue)
                    .setMessage("Não foi possível recuperar os dados do cliente")
                    .setNegativeBtnText("RETORNAR")
                    .setNegativeBtnBackground(Color.parseColor("#4ECA25"))  // for @ColorRes use setNegativeBtnBackgroundRes(R.color.colorvalue)
                    .isCancellable(true)
                    .setAnimation(Animation.POP)
                    .setIcon(R.mipmap.ic_launcher_round, View.VISIBLE)
                    .onNegativeClicked(new FancyAlertDialogListener() {
                        @Override
                        public void onClick(Dialog dialog) {

                            Intent intent = new Intent(AtualizarMeusDadosActivity.this, MainActivity.class);
                            startActivity(intent);

                        }
                    })
                    .build()
                    .show();

        }

    }

    public void editarCardCliente(View view) {

        btnEditarCardCliente.setEnabled(false);
        btnSalvarCardCliente.setEnabled(true);
    }

    public void editarCardPF(View view) {

        btnEditarCardPF.setEnabled(false);
        btnSalvarCardPF.setEnabled(true);
    }

    public void editarCardPJ(View view) {

        btnEditarCardPJ.setEnabled(false);
        btnSalvarCardPJ.setEnabled(true);
    }

    public void editarCardCredenciais(View view) {

        btnEditarCredenciais.setEnabled(false);
        btnSalvarCredenciais.setEnabled(true);
    }

    public void voltar(View view) {

        Intent intent = new Intent(AtualizarMeusDadosActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


}