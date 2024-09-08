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
import android.widget.Toast;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;

import app.daazi.aluno.appclientevipsqllite.R;
import app.daazi.aluno.appclientevipsqllite.api.AppUtil;
import app.daazi.aluno.appclientevipsqllite.controller.ClienteController;
import app.daazi.aluno.appclientevipsqllite.controller.ClientePFController;
import app.daazi.aluno.appclientevipsqllite.model.Cliente;
import app.daazi.aluno.appclientevipsqllite.model.ClientePF;
import app.daazi.aluno.appclientevipsqllite.model.ClientePJ;

public class MeusDadosActivity extends AppCompatActivity {

    EditText editPrimeiroNome, editSobrenome, editCpf, editNomeCompleto, editCnpj, editRazaoSocial;
    EditText editDataAbertura, editEmail, editSenhaA;
    CheckBox ckPessoaFisica, ckSimplesNacional, ckMei;

    Cliente cliente;
    ClienteController controller;
    ClientePFController controllerPF;

    SharedPreferences preferences;

    int clienteID;
    boolean isPessoaFisica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_dados);

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

        editCpf = findViewById(R.id.editCpf);
        editNomeCompleto = findViewById(R.id.editNomeCompleto);

        editCnpj = findViewById(R.id.editCnpj);
        editRazaoSocial = findViewById(R.id.editRazaoSocial);
        editDataAbertura = findViewById(R.id.editDataAbertura);
        ckSimplesNacional = findViewById(R.id.ckSimplesNacional);
        ckMei = findViewById(R.id.ckMei);

        editEmail = findViewById(R.id.editEmail);
        editSenhaA = findViewById(R.id.editSenhaA);

        cliente = new Cliente();
        cliente.setId(clienteID);

        controller = new ClienteController(this);
        controllerPF = new ClientePFController(this);
    }

    private void popularFormulario() {

        if (clienteID>=1){

            cliente = controller.getClienteByID(cliente);
            cliente.setClientePF(controllerPF.getClientePFByFK(cliente.getId()));

            editPrimeiroNome.setText(cliente.getPrimeiroNome());
            editSobrenome.setText(cliente.getSobreNome());
            ckPessoaFisica.setChecked(cliente.isPessoaFisica());

            editCpf.setText(cliente.getClientePF().getCpf());
            editNomeCompleto.setText(cliente.getClientePF().getNomeCompleto());

            editEmail.setText(cliente.getEmail());
            editSenhaA.setText(cliente.getSenha());

        }else{

            FancyAlertDialog.Builder
                    .with(MeusDadosActivity.this)
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

                            Intent intent = new Intent(MeusDadosActivity.this, MainActivity.class);
                            startActivity(intent);

                        }
                    })
                    .build()
                    .show();

        }

    }

    public void voltar(View view) {

        Intent intent = new Intent(MeusDadosActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}