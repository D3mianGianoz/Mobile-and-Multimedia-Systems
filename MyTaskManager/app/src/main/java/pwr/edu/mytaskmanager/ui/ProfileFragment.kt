package pwr.edu.mytaskmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pwr.edu.mytaskmanager.R
import pwr.edu.mytaskmanager.databinding.ProfileFragmentBinding


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // For safety
        setHasOptionsMenu(false)
        val binding: ProfileFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)

        return binding.root
    }

}
