package ashutosh.jharkhand.regional.models

data class Question(
    val id: String,
    val categoryId: String,
    val topicId: String,
    val setId: String,
    val question: String,
    val opt1: String,
    val opt2: String,
    val opt3: String,
    val opt4: String,
    val correctAnswer: Int,
    val date: Long
)
