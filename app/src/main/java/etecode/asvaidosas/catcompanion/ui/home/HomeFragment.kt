// Define the package where this class belongs
package etecode.asvaidosas.catcompanion.ui.home

// Import necessary Android and app-related libraries
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

// Import the generated binding class for the HomeFragment layout
import etecode.asvaidosas.catcompanion.databinding.FragmentHomeBinding

// Declare the HomeFragment class, extending Fragment
class HomeFragment : Fragment() {

    // Declare a nullable variable to hold the binding for the associated layout
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    // Declare a private property to access the non-null binding when needed
    private val binding get() = _binding!!

    // Override the onCreateView method to inflate the layout and initialize the UI components
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * Using ViewModelProvider is a recommended approach for creating
         * instances of ViewModels in Android. The ViewModelProvider is part
         * of the Android Architecture Components and provides a way to create
         * and retrieve ViewModels in a way that is lifecycle-aware.
         */
        // Create an instance of HomeViewModel using ViewModelProvider
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        /**
         * "Inflating the layout and initializing the UI components" refers to
         * the process of converting an XML layout file into corresponding View
         * objects in Android.
         */
        // Inflate the layout using the generated binding class
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Get a reference to the root view of the inflated layout
        val root: View = binding.root

        // Get a reference to the TextView from the inflated layout
        val textView: TextView = binding.textHome

        // Observe changes in the text property of the ViewModel and update the TextView accordingly
        homeViewModel.text.observe(viewLifecycleOwner) {
            /**
             * it: In Kotlin, it is a special keyword used in lambda expressions
             * to refer to the single parameter of the lambda function. In the
             * provided context, it refers to the value emitted by the observed
             * LiveData.
             */
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
