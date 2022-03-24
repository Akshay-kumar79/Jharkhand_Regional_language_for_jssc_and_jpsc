package ashutosh.jharkhand.regional.viewModels

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.jharkhand.regional.R
import ashutosh.jharkhand.regional.models.Question
import ashutosh.jharkhand.regional.utils.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class QuizViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val LOADING_STATUS_LOADING = "loading"
        const val LOADING_STATUS_COMPLETE = "complete"
        const val LOADING_STATUS_NO_FOUND = "no_found"
    }

    private val db = Firebase.firestore

    val questionNumber = MutableLiveData<String>()

    private val _question = MutableLiveData<String>()
    val question: LiveData<String>
    get() = _question

    val opt1 = MutableLiveData<String>()
    val opt2 = MutableLiveData<String>()
    val opt3 = MutableLiveData<String>()
    val opt4 = MutableLiveData<String>()

    val opt_1_background = MutableLiveData<Drawable>()
    val opt_2_background = MutableLiveData<Drawable>()
    val opt_3_background = MutableLiveData<Drawable>()
    val opt_4_background = MutableLiveData<Drawable>()

    val isOptionClickable = MutableLiveData<Boolean>()

    var currentQuestions = ArrayList<Question>()
    var isOptionClickedForCurrentQuestion: Boolean = false

    var currentPos = 0
    var currentScore = 0

    // utils
    private val _loadingStatus = MutableLiveData<String>()
    val loadingStatus: LiveData<String>
        get() = _loadingStatus

    private val _isQuizDone = MutableLiveData<Boolean>()
    val isQuizDone: LiveData<Boolean>
        get() = _isQuizDone

    private val _showScore = MutableLiveData<Boolean>()
    val showScore: LiveData<Boolean>
        get() = _showScore

    init {
        _loadingStatus.value = LOADING_STATUS_LOADING
        _isQuizDone.value = false
        _showScore.value = false
        _question.value = ""
    }

    fun opt1Click() {
        isOptionClickable.value = false
        isOptionClickedForCurrentQuestion = true
        if (currentQuestions[currentPos].correctAnswer == 0) {
            Toast.makeText(getApplication(), "right answer", Toast.LENGTH_SHORT).show()
            currentScore++
            opt_1_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
        } else {
            Toast.makeText(getApplication(), "wrong answer", Toast.LENGTH_SHORT).show()
            opt_1_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_wrong)

            when (currentQuestions[currentPos].correctAnswer) {
                0 -> opt_1_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
                1 -> opt_2_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
                2 -> opt_3_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
                else -> opt_4_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
            }
        }
    }

    fun opt2Click() {
        isOptionClickable.value = false
        isOptionClickedForCurrentQuestion = true
        if (currentQuestions[currentPos].correctAnswer == 1) {
            Toast.makeText(getApplication(), "right answer", Toast.LENGTH_SHORT).show()
            currentScore++
            opt_2_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
        } else {
            Toast.makeText(getApplication(), "wrong answer", Toast.LENGTH_SHORT).show()
            opt_2_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_wrong)

            when (currentQuestions[currentPos].correctAnswer) {
                0 -> opt_1_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
                1 -> opt_2_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
                2 -> opt_3_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
                else -> opt_4_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
            }
        }
    }

    fun opt3Click() {
        isOptionClickable.value = false
        isOptionClickedForCurrentQuestion = true
        if (currentQuestions[currentPos].correctAnswer == 2) {
            Toast.makeText(getApplication(), "right answer", Toast.LENGTH_SHORT).show()
            currentScore++
            opt_3_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
        } else {
            Toast.makeText(getApplication(), "wrong answer", Toast.LENGTH_SHORT).show()
            opt_3_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_wrong)

            when (currentQuestions[currentPos].correctAnswer) {
                0 -> opt_1_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
                1 -> opt_2_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
                2 -> opt_3_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
                else -> opt_4_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
            }
        }
    }

    fun opt4Click() {
        isOptionClickable.value = false
        isOptionClickedForCurrentQuestion = true
        if (currentQuestions[currentPos].correctAnswer == 3) {
            Toast.makeText(getApplication(), "right answer", Toast.LENGTH_SHORT).show()
            currentScore++
            opt_4_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
        } else {
            Toast.makeText(getApplication(), "wrong answer", Toast.LENGTH_SHORT).show()
            opt_4_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_wrong)

            when (currentQuestions[currentPos].correctAnswer) {
                0 -> opt_1_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
                1 -> opt_2_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
                2 -> opt_3_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
                else -> opt_4_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_right)
            }
        }
    }

    fun getQuestionsFromFirebase(categoryId: String, topicId: String, setId: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId)
            .collection(Constants.TOPIC_COLLECTION).document(topicId)
            .collection(Constants.SET_COLLECTION).document(setId)
            .collection(Constants.QUESTION_COLLECTION).orderBy(Constants.QUESTION_TIME_FIELD).get()
            .addOnSuccessListener { documents ->
                if (documents != null && documents.size() > 0) {
                    val newQuestions = ArrayList<Question>()
                    for (document in documents) {
                        newQuestions.add(
                            Question(
                                document.id,
                                document.getString(Constants.QUESTION_QUESTION_FIELD) ?: "",
                                document.getString(Constants.QUESTION_OPTION_1_FIELD) ?: "",
                                document.getString(Constants.QUESTION_OPTION_2_FIELD) ?: "",
                                document.getString(Constants.QUESTION_OPTION_3_FIELD) ?: "",
                                document.getString(Constants.QUESTION_OPTION_4_FIELD) ?: "",
                                (document.get(Constants.QUESTION_CORRECT_ANSWER_FIELD) as Long?)?.toInt() ?: 0,
                                document.getLong(Constants.QUESTION_TIME_FIELD) ?: 0
                            )
                        )
                    }
                    currentQuestions = newQuestions
                    _loadingStatus.value = LOADING_STATUS_COMPLETE
                    setDataToViews()
                } else {
                    _loadingStatus.value = LOADING_STATUS_NO_FOUND
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), it.message, Toast.LENGTH_SHORT).show()
                _loadingStatus.value = LOADING_STATUS_NO_FOUND
            }
    }

    private fun setDataToViews() {
        _question.value = "Q- " + currentQuestions[currentPos].question
        questionNumber.value = (currentPos + 1).toString() + " out of " + currentQuestions.size.toString()

        opt1.value = currentQuestions[currentPos].opt1
        opt2.value = currentQuestions[currentPos].opt2
        opt3.value = currentQuestions[currentPos].opt3
        opt4.value = currentQuestions[currentPos].opt4

        opt_1_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_normal)
        opt_2_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_normal)
        opt_3_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_normal)
        opt_4_background.value = ContextCompat.getDrawable(getApplication(), R.drawable.custom_button_normal)

        isOptionClickable.value = true
        isOptionClickedForCurrentQuestion = false
    }

    fun onNextClick() {
        if (isOptionClickedForCurrentQuestion) {
            if (currentPos < currentQuestions.size - 1) {
                currentPos++
                setDataToViews()
            } else {
                _showScore.value = true
            }
        } else {
            Toast.makeText(getApplication(), "select option", Toast.LENGTH_SHORT).show()
        }
    }

    fun restartQuiz() {
        currentPos = 0
        currentScore = 0
        setDataToViews()
    }

    fun setQuizDone() {
        _isQuizDone.value = true
    }

    fun showScoreDone() {
        _showScore.value = false
    }


}