package be.tftic.web2023.demo04_intent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btnGoPage1 : Button
    lateinit var btnGoPage2 : Button
    lateinit var btnLeave : Button
    lateinit var btnQuestion : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Binding
        btnGoPage1 = findViewById(R.id.btn_main_page1)
        btnGoPage2 = findViewById(R.id.btn_main_page2)
        btnLeave = findViewById(R.id.btn_main_leave)
        btnQuestion = findViewById(R.id.btn_main_question)

        // Listener "onClick"
        btnGoPage1.setOnClickListener(this)
        btnGoPage2.setOnClickListener(this)
        btnLeave.setOnClickListener(this)
        btnQuestion.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_main_page1 -> goToPage1()
            R.id.btn_main_page2 -> goToPage2()
            R.id.btn_main_leave -> finish()
            R.id.btn_main_question -> goToQuestion()
        }
    }

    private fun goToPage1() {
        // Intent Explicite
        val intentPage1 : Intent = Intent(this, PageOneActivity::class.java)

        // Envoi à Android
        startActivity(intentPage1)
    }

    private fun goToPage2() {
        // Intent Implicite
        val intentPage2 : Intent = Intent().apply {
            action = "be.tftic.web2023.demo04_intent.PAGE2"
        }

        // /!\ Necessite de modifier "AndroidManifest.xml"
        // Ajouter un "intent-filter" sur l'activité avec :
        //  - Action avec un "name"
        //  - Category avec en "name" la valeur "android.intent.category.DEFAULT"

        // Envoi à Android
        startActivity(intentPage2)
    }

    val getQuestion = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Traitement de la réponse de l'activité "QuestionActivity"
        val tvResponse : TextView =  findViewById(R.id.tv_main_response)

        when (it.resultCode) {
            RESULT_OK -> {
                val color = it.data?.getStringExtra(QuestionActivity.ANSWER_COLOR)
                tvResponse.setText(getString(R.string.response_like).format(color))
            }
            RESULT_CANCELED -> {
                tvResponse.setText(R.string.response_none)
            }
        }
    }

    private fun goToQuestion() {
        val questionIntent : Intent = Intent(this, QuestionActivity::class.java)
        getQuestion.launch(questionIntent);
    }

}