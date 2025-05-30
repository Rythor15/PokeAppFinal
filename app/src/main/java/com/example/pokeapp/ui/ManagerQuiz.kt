package com.example.pokeapp.ui

import android.content.Context
import com.example.pokeapp.data.models.PreguntaModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class QuizManager(private val context: Context) {

    private var preguntasPrincipiante: List<PreguntaModel> = emptyList()
    private var preguntasLiderGym: List<PreguntaModel> = emptyList()
    private var preguntasMaestroPokemon: List<PreguntaModel> = emptyList()
    private var selectedQuestions: List<PreguntaModel> = emptyList()
    private var currentQuestionIndex: Int = 0
    var score: Int = 0
        // Esto hace que el getter sea publico pero el setter de la variable sea privado
        // para que pueda usar el setter solo desde esta clase y el getter en las demas
        private set
    val totalQuestionsInQuiz = 10 // Número de preguntas en cada partida
    private var dificultadActual: Dificultad = Dificultad.PRINCIPIANTE

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        val jsonString: String? = try {
            context.assets.open("preguntas.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }

        jsonString?.let {
            val listType = object : TypeToken<List<PreguntaModel>>() {}.type
            val preguntas: List<PreguntaModel> = Gson().fromJson(it, listType)

            preguntasPrincipiante = preguntas.filter { it.id in 1..20 }
            preguntasLiderGym = preguntas.filter { it.id in 21..40 }
            preguntasMaestroPokemon = preguntas.filter { it.id in 41..60 }

        }
    }

    fun setDifficulty(dificultad: Dificultad) {
        this.dificultadActual = dificultad
    }

    fun startNewQuiz() {
        score = 0
        currentQuestionIndex = 0
        val preguntasDificultad = when (dificultadActual) {
            Dificultad.PRINCIPIANTE -> preguntasPrincipiante
            Dificultad.LIDER_GIMNASIO -> preguntasLiderGym
            Dificultad.MAESTRO_POKEMON -> preguntasMaestroPokemon
        }
        // Seleccionar 10 preguntas aleatorias de las disponibles
        selectedQuestions = preguntasDificultad.shuffled().take(totalQuestionsInQuiz)

    }

    fun getCurrentQuestion(): PreguntaModel? {
        if (currentQuestionIndex < selectedQuestions.size) {
            return selectedQuestions[currentQuestionIndex]
        }
        return null // No hay más preguntas
    }

    fun checkAnswer(selectedOptionIndex: Int): Boolean {
        val currentQuestion = getCurrentQuestion() ?: return false
        val isCorrect = (selectedOptionIndex == currentQuestion.correctAnswerIndex)
        if (isCorrect) {
            score++
        }
        return isCorrect
    }

    fun moveToNextQuestion(): Boolean {
        currentQuestionIndex++
        return currentQuestionIndex < selectedQuestions.size // Retorna true si hay más preguntas
    }

    fun isQuizFinished(): Boolean {
        return currentQuestionIndex >= selectedQuestions.size
    }

    fun getCurrentQuestionNumber(): Int {
        return currentQuestionIndex + 1 // +1 para que sea 1-basado en lugar de 0-basado
    }
}