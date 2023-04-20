package com.example.whats_eat.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.whats_eat.R
import com.example.whats_eat.databinding.FragmentProfileBinding
import com.example.whats_eat.viewModel.ProfileViewModel
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment(), View.OnClickListener {
    private var _proFileBinding: FragmentProfileBinding? = null
    private val proFileBinding get() = _proFileBinding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _proFileBinding = FragmentProfileBinding.inflate(layoutInflater)
        return proFileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        proFileBinding.updateBtn.setOnClickListener(this)
        proFileBinding.deleteBtn.setOnClickListener(this)

        profileViewModel.userProfile.observe(viewLifecycleOwner) {
            proFileBinding.nameTxt.text = it.userName
            proFileBinding.emailTxt.text = it.userEmail
        }

        profileViewModel.userCollectionCnt.observe(viewLifecycleOwner) {
            proFileBinding.profileRateNum.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _proFileBinding = null
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.updateBtn -> {  }
            R.id.deleteBtn -> {  }
        }
    }

    private fun deleteUserAccount(): Task<Void>? = profileViewModel.deleteUserAccount()


}