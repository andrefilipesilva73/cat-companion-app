// Define the package where this class belongs
package etecode.asvaidosas.catcompanion.ui.dashboard

// Import necessary Android and app-related libraries
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

// Import the generated binding class for the DashboardFragment layout
import etecode.asvaidosas.catcompanion.databinding.FragmentDashboardBinding

// Declare the DashboardFragment class, extending Fragment
class DashboardFragment : Fragment() {

    // Declare a nullable variable to hold the binding for the associated layout
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    // Declare a private property to access the non-null binding when needed
    private val binding get() = _binding!!

    // Override the onCreateView method to inflate the layout and initialize the UI components
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Create an instance of DashboardViewModel using ViewModelProvider
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        // Inflate the layout using the generated binding class
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        // Get a reference to the root view of the inflated layout
        val root: View = binding.root

        // Get a reference to the TextView from the inflated layout
        val textView: TextView = binding.textDashboard

        // Observe changes in the text property of the ViewModel and update the TextView accordingly
        dashboardViewModel.text.observe(viewLifecycleOwner) {
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