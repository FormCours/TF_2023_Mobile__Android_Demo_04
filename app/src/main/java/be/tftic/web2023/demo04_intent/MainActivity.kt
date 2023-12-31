package be.tftic.web2023.demo04_intent

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import java.time.LocalTime

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btnGoPage1 : Button
    lateinit var btnGoPage2 : Button
    lateinit var btnLeave : Button
    lateinit var btnQuestion : Button
    lateinit var btnAlarm : Button
    lateinit var btnDemoPermission : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Binding
        btnGoPage1 = findViewById(R.id.btn_main_page1)
        btnGoPage2 = findViewById(R.id.btn_main_page2)
        btnLeave = findViewById(R.id.btn_main_leave)
        btnQuestion = findViewById(R.id.btn_main_question)
        btnAlarm = findViewById(R.id.btn_main_alarm)
        btnDemoPermission = findViewById(R.id.btn_main_permission)

        // Listener "onClick"
        btnGoPage1.setOnClickListener(this)
        btnGoPage2.setOnClickListener(this)
        btnLeave.setOnClickListener(this)
        btnQuestion.setOnClickListener(this)
        btnAlarm.setOnClickListener(this)
        btnDemoPermission.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_main_page1 -> goToPage1()
            R.id.btn_main_page2 -> goToPage2()
            R.id.btn_main_leave -> finish()
            R.id.btn_main_question -> goToQuestion()
            R.id.btn_main_alarm -> addNewAlarm()
            R.id.btn_main_permission -> exampleRequestPermission()
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

    private fun addNewAlarm() {

        // L'heure actuelle + 10 minutes
        val now : LocalTime = LocalTime.now()
        val alarmTime = now.plusMinutes(10)

        // Intent pour ajouter une alram
        val intentAlarm : Intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_HOUR, alarmTime.hour)
            putExtra(AlarmClock.EXTRA_MINUTES, alarmTime.minute)
            putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarm_msg))
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }

        // Cas de figure où il faut uniquement une permission via le manifest
        startActivity(intentAlarm)
    }


    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if(isGranted) {
            processWithPermission()
        }
        else {
            Toast.makeText(this, "Dommage...", Toast.LENGTH_LONG).show()
        }
    }
    private fun exampleRequestPermission() {
        // Check de permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Demande de permission
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

            return;
        }

        processWithPermission()
    }

    private fun processWithPermission() {
        // Cas réel : On fait pas un toast :o
        Toast.makeText(this, "Permission accordée :)", Toast.LENGTH_LONG).show()
    }
}