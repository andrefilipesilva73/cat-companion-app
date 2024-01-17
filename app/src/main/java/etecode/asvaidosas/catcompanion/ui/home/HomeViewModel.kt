// Define the package where this class belongs
package etecode.asvaidosas.catcompanion.ui.home

// Import necessary Android and app-related libraries for ViewModel and LiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Declare the HomeViewModel class, extending ViewModel
class HomeViewModel : ViewModel() {

    // Declare a MutableLiveData to hold a String value, with initial value "This is home Fragment"
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    // Declare a LiveData property referencing the MutableLiveData, making it publicly accessible as read-only
    val text: LiveData<String> = _text
}
