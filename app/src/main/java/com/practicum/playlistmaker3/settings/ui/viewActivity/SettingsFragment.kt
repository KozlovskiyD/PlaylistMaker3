package com.practicum.playlistmaker3.settings.ui.viewActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.practicum.playlistmaker3.databinding.FragmentSettingsBinding
import com.practicum.playlistmaker3.settings.ui.viewModelSettings.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private val viewModel by viewModel<SettingsViewModel>()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchNight = binding.switch1
        val buttonShare = binding.writeTo
        val buttonWriteTo = binding.shar
        val buttonUserAgreement = binding.userAgreement

        viewModel.switchCheck()

        viewModel.switchCheckedLiveData.observe(viewLifecycleOwner) { check ->
            switchNight.isChecked = check
        }

        switchNight.setOnCheckedChangeListener { _, checked ->
            viewModel.saveSwitch(checked)

        }

        buttonShare.setOnClickListener {
            viewModel.write()
        }

        buttonWriteTo.setOnClickListener {
            viewModel.share()
        }

        buttonUserAgreement.setOnClickListener {
            viewModel.agreement()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
