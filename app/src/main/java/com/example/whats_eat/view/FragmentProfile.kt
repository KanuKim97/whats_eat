package com.example.whats_eat.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.whats_eat.R
import com.example.whats_eat.data.di.dispatcherQualifier.MainDispatcher
import com.example.whats_eat.databinding.FragmentProfileBinding
import com.example.whats_eat.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentProfile: Fragment(), View.OnClickListener {
    @MainDispatcher @Inject lateinit var mainDispatcher: CoroutineDispatcher
    private var _proFileBinding: FragmentProfileBinding? = null
    private val proFileBinding get() = _proFileBinding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _proFileBinding = FragmentProfileBinding.inflate(layoutInflater)
        return proFileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        proFileBinding.signOutBtn.setOnClickListener(this)
        proFileBinding.updateBtn.setOnClickListener(this)
        proFileBinding.deleteBtn.setOnClickListener(this)

        lifecycleScope.launch(mainDispatcher) {
            profileViewModel.userFlow.collect {
                proFileBinding.nickNameTxt.text = it.userName
                proFileBinding.userEmailTxt.text = it.userEmail
            }
        }

        lifecycleScope.launch(mainDispatcher) {
            profileViewModel.collectionFlow.collect {
                proFileBinding.profileRateNum.text = it
            }
        }

        profileViewModel.isAccountDeleteSuccess.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireContext(), ActivityLogIn::class.java))
                activity?.finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _proFileBinding = null
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.signOut_Btn -> showSignOutUserDialog()
            R.id.deleteBtn ->  showDeleteUserDialog()
            R.id.updateBtn -> {  }
        }
    }

    private fun signOutUserAccount(): Job = profileViewModel.signOutUserAccount()

    private fun deleteUserAccount(): Job = profileViewModel.deleteUserAccount()

    private fun showSignOutUserDialog() =
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.LogOut_Content)
            .setPositiveButton(R.string.btn_delBox_Yes) {_, _ -> signOutUserAccount()}
            .setNegativeButton(R.string.btn_delBox_No) {_, _ -> }
            .create()
            .show()


    private fun showDeleteUserDialog() =
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.Box_delAccount)
            .setPositiveButton(R.string.btn_delBox_Yes) { _, _ -> deleteUserAccount() }
            .setNegativeButton(R.string.btn_delBox_No) {_, _ -> }
            .create()
            .show()
}