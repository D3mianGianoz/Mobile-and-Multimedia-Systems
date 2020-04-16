package pwr.edu.mymlkit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pwr.edu.mymlkit.databinding.WelcomeFragmentBinding


/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {

    private lateinit var binding: WelcomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.welcome_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val navController = findNavController()
        binding.imageRec.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_pickImageFragment)
        }
        binding.textRec.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_textRecognitionFragment)
        }
        binding.objectRec.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_objectFragment)
        }

        return binding.root
    }

}
