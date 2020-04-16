package pwr.edu.mymlkit.`object`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import de.crysxd.cameraXTracker.BuildConfig
import de.crysxd.cameraXTracker.CameraFragment
import de.crysxd.cameraXTracker.ar.BoundingBoxArOverlay
import de.crysxd.cameraXTracker.ar.PathInterpolator
import de.crysxd.cameraXTracker.ar.PositionTranslator
import pwr.edu.mymlkit.R
import pwr.edu.mymlkit.databinding.ObjectFragmentBinding

class ObjectFragment : Fragment() {

    private lateinit var imageAnalyzer: MyImageAnalyzer
    private lateinit var camera: CameraFragment
    private lateinit var binding: ObjectFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.object_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        camera = childFragmentManager.findFragmentById(R.id.cameraFragment) as CameraFragment

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val boundingBoxArOverlay = BoundingBoxArOverlay(context!!, BuildConfig.DEBUG)
        imageAnalyzer = ViewModelProvider(this).get(MyImageAnalyzer::class.java)
        camera.imageAnalyzer = imageAnalyzer

        camera.arOverlayView.observe(viewLifecycleOwner, Observer {
            it.doOnLayout { view ->
                imageAnalyzer.arObjectTracker
                    .pipe(PositionTranslator(view.width, view.height))
                    .pipe(PathInterpolator())
                    .addTrackingListener(boundingBoxArOverlay)
            }

            it.add(boundingBoxArOverlay)
        })

    }
}
