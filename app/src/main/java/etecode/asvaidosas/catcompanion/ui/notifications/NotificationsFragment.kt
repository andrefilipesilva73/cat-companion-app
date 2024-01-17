// Define the package where this class belongs
package etecode.asvaidosas.catcompanion.ui.notifications

// Import necessary Android and app-related libraries
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

// Import the generated binding class for the NotificationsFragment layout
import etecode.asvaidosas.catcompanion.databinding.FragmentNotificationsBinding

// Declare the NotificationsFragment class, extending Fragment
class NotificationsFragment : Fragment() {

    // Declare a nullable variable to hold the binding for the associated layout
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    // Declare a private property to access the non-null binding when needed
    private val binding get() = _binding!!

    // Override the onCreateView method to inflate the layout and initialize the UI components
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Create an instance of NotificationsViewModel using ViewModelProvider
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        // Inflate the layout using the generated binding class
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        // Get a reference to the root view of the inflated layout
        val root: View = binding.root

        // Get a reference to the TextView from the inflated layout
        val textView: TextView = binding.textNotifications

        // Observe changes in the text property of the ViewModel and update the TextView accordingly
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Return the root view
        return root
    }

    // Override the onDestroyView method to release the binding when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
