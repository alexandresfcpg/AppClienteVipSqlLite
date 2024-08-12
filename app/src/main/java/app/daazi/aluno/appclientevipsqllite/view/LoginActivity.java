package app.daazi.aluno.appclientevipsqllite.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.squareup.picasso.Picasso;

import app.daazi.aluno.appclientevipsqllite.R;
import app.daazi.aluno.appclientevipsqllite.api.AppUtil;
import app.daazi.aluno.appclientevipsqllite.controller.ClienteController;
import app.daazi.aluno.appclientevipsqllite.model.Cliente;

public class LoginActivity extends AppCompatActivity {

    Cliente cliente;

    private SharedPreferences preferences;

    TextView txtRecuperarSenha, txtLerPolitica, btnSejaVip;
    EditText editEmail, editSenha;
    CheckBox ckLembrar;
    Button btnAcessar;
    ImageView imgBackground, imgLogo;

    boolean isFormularioOK, isLembrarSenha;

    ClienteController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_novo);

        initFormulario();

        loadImagens();

        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFormularioOK = validarFormulario()) {

                    if (validarDadosDoUsuario()) {

                        salvarSharedPreferences();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;

                    } else {
                        Toast.makeText(getApplicationContext(), "Verifique os dados...", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnSejaVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, ClienteVipActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });

        txtRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Carregando tela de recuperação de senha...",
                        Toast.LENGTH_LONG).show();

            }
        });

        txtLerPolitica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FancyAlertDialog.Builder
                        .with(LoginActivity.this)
                        .setTitle("Política de Privacidade e Termos de Uso")
                        .setBackgroundColor(Color.parseColor("#303F9F"))  // for @ColorRes use setBackgroundColorRes(R.color.colorvalue)
                        .setMessage("Do you really want to Exit ?")
                        .setNegativeBtnText("Discordo")
                        .setPositiveBtnBackground(Color.parseColor("#FF4081"))  // for @ColorRes use setPositiveBtnBackgroundRes(R.color.colorvalue)
                        .setPositiveBtnText("Concordo")
                        .setNegativeBtnBackground(Color.parseColor("#4ECA25"))  // for @ColorRes use setNegativeBtnBackgroundRes(R.color.colorvalue)
                        .isCancellable(true)
                        .setIcon(R.mipmap.ic_launcher_round, View.VISIBLE)
                        .onPositiveClicked(dialog -> Toast.makeText(LoginActivity.this, "Obrigado, seja bem-vindo e conclua o seu cadastro", Toast.LENGTH_SHORT).show())
                        .onNegativeClicked(dialog -> Toast.makeText(LoginActivity.this, "Lamentamos, mas é necessário concordar com a política de privacidade e os termos de uso", Toast.LENGTH_SHORT).show())
                        .build()
                        .show();
            }
        });
    }

    private boolean validarFormulario() {

        boolean retorno = true;

        if (TextUtils.isEmpty(editEmail.getText().toString())) {
            editEmail.setError("*");
            editEmail.requestFocus();
            retorno = false;
        }

        if (TextUtils.isEmpty(editSenha.getText().toString())) {
            editSenha.setError("*");
            editSenha.requestFocus();
            retorno = false;
        }

        return retorno;
    }

    private void initFormulario() {

        txtRecuperarSenha = findViewById(R.id.txtRecuperarSenha);
        txtLerPolitica = findViewById(R.id.txtLerPolitica);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        ckLembrar = findViewById(R.id.ckLembrar);
        btnAcessar = findViewById(R.id.btnAcessar);
        btnSejaVip = findViewById(R.id.btnSejaVip);
        imgBackground = findViewById(R.id.imgBackground);
        imgLogo = findViewById(R.id.imgLogo);

        isFormularioOK = false;

        controller = new ClienteController(getApplicationContext());
        cliente = new Cliente();

        //Código para incluir
        /*cliente.setPrimeiroNome("Cliente 3");
        cliente.setSobreNome("Sobrenome 3");
        cliente.setEmail("Email 3");
        cliente.setSenha("02468");
        cliente.setPessoaFisica(false);*/

        //Código para alterar
        /*cliente.setId(1);
        cliente.setPrimeiroNome("Cliente alterado");
        cliente.setSobreNome("Sobrenome alterado");
        cliente.setEmail("Email alterado");
        cliente.setSenha("654321");
        cliente.setPessoaFisica(false);*/

        //Código para excluir
        //cliente.setId(2);

        //controller.incluir(cliente);
        //controller.alterar(cliente);
        //controller.deletar(cliente);
        //List<Cliente> clientes = controller.listar();

        restaurarSharedPreferences();
    }

    private void loadImagens() {

        Picasso.get().load(AppUtil.URL_IMG_BACKGROUND).placeholder(R.drawable.carregando_animacao).into(imgBackground);
        Picasso.get().load(AppUtil.URL_IMG_LOGO).placeholder(R.drawable.carregando_animacao).into(imgLogo);

    }

    public void lembrarSenha(View view) {

        isLembrarSenha = ckLembrar.isChecked();
    }

    public boolean validarDadosDoUsuario() {

        return true;
    }

    private void salvarSharedPreferences() {

        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dados = preferences.edit();
        dados.putBoolean("loginAutomatico", isLembrarSenha);
        dados.putString("emailCliente", editEmail.getText().toString());
        dados.apply();

    }

    private void restaurarSharedPreferences() {

        preferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        isLembrarSenha = preferences.getBoolean("loginAutomatico", false);

        cliente.setEmail(preferences.getString("email", "teste@teste.com"));
        cliente.setSenha(preferences.getString("senha", "12345"));
        cliente.setPrimeiroNome(preferences.getString("primeiroNome", "Cliente"));
        cliente.setSobreNome(preferences.getString("sobreNome", "Fake"));
        cliente.setPessoaFisica(preferences.getBoolean("pessoaFisica", true));

    }
}