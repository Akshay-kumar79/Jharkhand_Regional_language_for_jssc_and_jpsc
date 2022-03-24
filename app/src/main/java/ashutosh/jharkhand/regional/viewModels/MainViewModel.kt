package ashutosh.jharkhand.regional.viewModels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.jharkhand.regional.models.Category
import ashutosh.jharkhand.regional.models.Question
import ashutosh.jharkhand.regional.models.Set
import ashutosh.jharkhand.regional.models.Topic
import ashutosh.jharkhand.regional.utils.Constants
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val LOADING_STATUS_LOADING = "loading"
        const val LOADING_STATUS_COMPLETE = "complete"
        const val LOADING_STATUS_NO_FOUND = "no_found"
    }

    private val db = Firebase.firestore

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    // utils
    private val _categoryLoadingStatus = MutableLiveData<String>()
    val categoryLoadingStatus: LiveData<String>
        get() = _categoryLoadingStatus


    init {
        _categoryLoadingStatus.value = LOADING_STATUS_LOADING
        getDataFromFirebase()
    }

    private fun getDataFromFirebase() {
        db.collection(Constants.CATEGORIES_COLLECTION).orderBy(Constants.CATEGORIES_TIME_FIELD).get()
            .addOnSuccessListener { documents ->
                if (documents != null && documents.size() > 0) {
                    val newCategories = ArrayList<Category>()
                    for (document in documents) {
                        newCategories.add(
                            Category(
                                document.id,
                                document.getString(Constants.CATEGORIES_NAME_FIELD) ?: "",
                                document.getString(Constants.CATEGORIES_IMAGE_FIELD) ?: "",
                                document.getLong(Constants.CATEGORIES_TIME_FIELD) ?: 0
                            )
                        )
                    }
                    _categories.value = newCategories
                    _categoryLoadingStatus.value = LOADING_STATUS_COMPLETE
                }else{
                    _categoryLoadingStatus.value = LOADING_STATUS_NO_FOUND
                }
            }
            .addOnFailureListener {
                Toast.makeText(getApplication(), it.message, Toast.LENGTH_SHORT).show()
                _categoryLoadingStatus.value = LOADING_STATUS_NO_FOUND
            }

    }

}