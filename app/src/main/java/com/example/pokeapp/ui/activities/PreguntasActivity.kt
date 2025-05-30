package com.example.pokeapp.ui.activities

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pokeapp.R
import com.example.pokeapp.databinding.ActivityPreguntasBinding
import com.example.pokeapp.ui.Dificultad
import com.example.pokeapp.ui.QuizManager

class PreguntasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreguntasBinding
    private lateinit var quizManager: QuizManager
    private lateinit var opcionesBotones: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreguntasBinding.inflate(layoutInflater)
        quizManager = QuizManager(this)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        seleccionarDificultad()
        setListeners()
    }

    private fun seleccionarDificultad() {
        val difficultyString = intent.getStringExtra("QUIZ_DIFFICULTY")
        val selectedDifficulty = if (difficultyString != null) {
            Dificultad.valueOf(difficultyString) // Convertir de String a enum
        } else {
            Dificultad.PRINCIPIANTE // Dificultad por defecto si no se pasa nada
        }

        quizManager = QuizManager(this)
        quizManager.setDifficulty(selectedDifficulty)
    }

    private fun setListeners() {
        opcionesBotones = listOf(binding.btnOpcion1, binding.btnOpcion2, binding.btnOpcion3, binding.btnOpcion4)
        opcionesBotones.forEachIndexed { index, button ->
            button.setOnClickListener {
                handleAnswer(index)
            }
        }
        startGame()
    }

    private fun startGame() {
        quizManager.startNewQuiz()
        displayQuestion()
        updateScoreAndQuestionNumber()
    }

    private fun displayQuestion() {
        val currentQuestion = quizManager.getCurrentQuestion()
        if (currentQuestion != null) {
            binding.tvPregunta.text = currentQuestion.text

            // Mostrar opciones
            opcionesBotones.forEachIndexed { index, button ->
                if (index < currentQuestion.options.size) {
                    button.text = currentQuestion.options[index]
                    button.visibility = Button.VISIBLE // Hacer visible si hay opción
                    button.isEnabled = true // Habilitar el botón
                    button.setShadowLayer(0f, 0f, 0f, Color.TRANSPARENT)
                } else {
                    button.visibility = Button.GONE // Ocultar si no hay opción (ej. Verdadero/Falso)
                }
            }
        } else {
            // Esto no debería pasar si isQuizFinished se maneja correctamente
            showGameOverDialog()
        }
    }

    private fun handleAnswer(selectedOptionIndex: Int) {
        // Deshabilitar los botones para evitar múltiples clics
        opcionesBotones.forEach { it.isEnabled = false }

        val isCorrect = quizManager.checkAnswer(selectedOptionIndex)
        val currentQuestion = quizManager.getCurrentQuestion() // Necesario para obtener el índice correcto

        if (isCorrect) {
            // Resaltar la respuesta correcta en verde
            opcionesBotones[selectedOptionIndex].setShadowLayer(10f,0f,0f,Color.GREEN)
        } else {
            // Resaltar la respuesta incorrecta en rojo
            opcionesBotones[selectedOptionIndex].setShadowLayer(10f,0f,0f,Color.RED)
            // Resaltar la respuesta correcta en verde
            currentQuestion?.correctAnswerIndex?.let {
                opcionesBotones[it].setShadowLayer(10f,0f,0f,Color.GREEN)
            }
        }

        updateScoreAndQuestionNumber()

        // Pequeña pausa para que el usuario vea la retroalimentación
        binding.tvPregunta.postDelayed({
            if (quizManager.isQuizFinished()) {
                showGameOverDialog()
            } else {
                quizManager.moveToNextQuestion()
                displayQuestion()
                updateScoreAndQuestionNumber()
            }
        }, 2000) // 3 segundos de retraso
    }

    private fun updateScoreAndQuestionNumber() {
        binding.tvPuntuacion.text = "Puntuación: ${quizManager.score}"
        binding.tvNumeroPregunta.text = "Pregunta ${quizManager.getCurrentQuestionNumber()}/${quizManager.totalQuestionsInQuiz}"
    }

    private fun showGameOverDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¡Quiz Terminado!")
        builder.setMessage("Tu puntuación final: ${quizManager.score} / ${quizManager.totalQuestionsInQuiz}")
        builder.setPositiveButton("Jugar de nuevo") { dialog, _ ->
            dialog.dismiss()
            startGame()
        }
        builder.setNegativeButton("Salir") { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        builder.setCancelable(false) // El usuario debe elegir una opción
        builder.show()
    }
}