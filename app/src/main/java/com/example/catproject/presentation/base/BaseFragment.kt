package com.example.catproject.presentation.base

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.catproject.presentation.utils.showToast
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val viewModelClass: Class<VM>
) : Fragment() {

    protected val context: Context
        @JvmName("requireContextKtx") get() = requireContext()

    private var _binding: VB? = null
    protected val binding get() = requireNotNull(_binding) { "Binding not init" }

    private var _viewModel: VM? = null
    protected val viewModel get() = requireNotNull(_viewModel) { "ViewModel isn't init" }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val downloadReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val reference = intent.getLongExtra(
                DownloadManager.EXTRA_DOWNLOAD_ID, -1
            )
            if (getDownloadId() == reference) {
                context.showToast("Loading completed")
            }
        }
    }

    protected abstract fun createBinding(): VB

    protected abstract fun getDownloadId(): Long?

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass]
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = createBinding()
        return binding.root
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        requireActivity().registerReceiver(downloadReceiver, intentFilter)
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(downloadReceiver)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}