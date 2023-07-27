package be.tftic.web2023.demo04_intent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class QuestionActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        // Element static de la classe "QuestionActivity"
        val ANSWER_COLOR = "ANSWER_COLOR"
    }

    lateinit var btnGreen : Button
    lateinit var btnPink : Button
    lateinit var btnCyan : Button
    lateinit var btnNoAnswer : Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        
        btnGreen = findViewById(R.id.btn_question_green)
        btnPink = findViewById(R.id.btn_question_pink)
        btnCyan = findViewById(R.id.btn_question_cyan)
        btnNoAnswer= findViewById(R.id.btn_question_no_answer)

        btnGreen.setOnClickListener(this)
        btnPink.setOnClickListener(this)
        btnCyan.setOnClickListener(this)
        btnNoAnswer.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_question_green -> answerSuccess("Vert")
            R.id.btn_question_pink -> answerSuccess("Rose")
            R.id.btn_question_cyan -> answerSuccess("Cyan")
            R.id.btn_question_no_answer -> answerNone()
        }
    }

    private fun answerSuccess(color: String) {
        // Création d'un intent pour stocker des données
        val data : Intent = Intent().apply {
            putExtra(ANSWER_COLOR, color)
        }

        setResult(RESULT_OK, data)
        finish()
    }

    private fun answerNone() {
        setResult(RESULT_CANCELED)
        finish()
    }
}