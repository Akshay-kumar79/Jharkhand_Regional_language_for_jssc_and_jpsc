package ashutosh.jharkhand.regional.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.jharkhand.regional.models.Set
import ashutosh.jharkhand.regional.utils.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SetViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Firebase.firestore

    private val _currentSets = MutableLiveData<List<Set>>()
    val currentSets: LiveData<List<Set>>
        get() = _currentSets

    //utils
    private val _setLoadingStatus = MutableLiveData<String>()
    val setLoadingStatus: LiveData<String>
        get() = _setLoadingStatus

    init {
        _setLoadingStatus.value = MainViewModel.LOADING_STATUS_LOADING
    }

    fun getSetsFromFirebase(categoryId: String, topicId: String) {
        db.collection(Constants.CATEGORIES_COLLECTION).document(categoryId)
            .collection(Constants.TOPIC_COLLECTION).document(topicId)
            .collection(Constants.SET_COLLECTION).orderBy(Constants.SET_NUMBER_FIELD).get()
            .addOnSuccessListener { documents ->
                if (documents != null && documents.size() > 0) {
                    val newSets = ArrayList<Set>()
                    for (document in documents) {
                        newSets.add(
                            Set(
                                document.id,
                                (document.get(Constants.SET_NUMBER_FIELD) as Long?)?.toInt() ?: 0
                            )
                        )
                    }
                    _currentSets.value = newSets
                    _setLoadingStatus.value = MainViewModel.LOADING_STATUS_COMPLETE
                }else{
                    _setLoadingStatus.value = MainViewModel.LOADING_STATUS_NO_FOUND
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), it.message, Toast.LENGTH_SHORT).show()
                _setLoadingStatus.value = MainViewModel.LOADING_STATUS_NO_FOUND
            }
    }

}