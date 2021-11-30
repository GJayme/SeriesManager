package sc3005054.jayme.seriesmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import sc3005054.jayme.seriesmanager.view.utils.AuthenticacaoFirebase
import sc3005054.jayme.seriesmanager.databinding.ActivityCadastrarUsuarioBinding

class CadastrarUsuarioActivity : AppCompatActivity() {
    private val cadastrarUsuarioBinding: ActivityCadastrarUsuarioBinding by lazy {
        ActivityCadastrarUsuarioBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cadastrarUsuarioBinding.root)
        supportActionBar?.subtitle = "Cadastrar usuário"

        with(cadastrarUsuarioBinding) {
            cadastrarUsuarioBt.setOnClickListener {
                val email = emailEt.text.toString()
                val senha = senhaEt.text.toString()
                val repetirSenha = repetirSenhaEt.text.toString()
                if (senha == repetirSenha) {
                    AuthenticacaoFirebase.firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnSuccessListener {
                        Toast.makeText(this@CadastrarUsuarioActivity, "Usuário $email cadastrado", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this@CadastrarUsuarioActivity, "Falha no cadastro", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@CadastrarUsuarioActivity, "Senhas não coincidem", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}