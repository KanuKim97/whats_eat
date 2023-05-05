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
        proFileBinding.accountSignOutBtn.setOnClickListener(this)
        proFileBinding.accountDeleteBtn.setOnClickListener(this)

        setUserProfile()
        setUserCollectionCount()

        profileViewModel.isAccountDeleteSuccess.observe(viewLifecycleOwner) { isDeleteSuccess ->
            if (isDeleteSuccess) {
                startActivity(Intent(requireContext(), ActivityLogIn::class.java))
                activity?.finish()
            }
        }

        profileViewModel.isSignOutSuccess.observe(viewLifecycleOwner) { isSignOutSuccess ->
            if (isSignOutSuccess) {
                startActivity(Intent(requireContext(), ActivityLogIn::class.java))
                activity?.finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _proFileBinding = null
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.accountSignOut_Btn -> showSignOutUserDialog()
            R.id.accountDelete_Btn ->  showDeleteUserDialog()
        }
    }

    private fun setUserProfile(): Job = lifecycleScope.launch(mainDispatcher) {
        profileViewModel.userFlow.collect {
            proFileBinding.userNickNameTxt.text = it.userName
            proFileBinding.userEmailTxt.text = it.userEmail
        }
    }

    private fun setUserCollectionCount(): Job = lifecycleScope.launch(mainDispatcher) {
        lifecycleScope.launch(mainDispatcher) {
            profileViewModel.collectionFlow.collect {
                proFileBinding.userCollectionCount.text = it
            }
        }
    }

    private fun signOutUserAccount(): Job = profileViewModel.signOutUserAccount()

    private fun deleteUserAccount(): Job = profileViewModel.deleteUserAccount()

    private fun showSignOutUserDialog(): Unit =
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.LogOut_Content)
            .setPositiveButton(R.string.btn_delBox_Yes) {_, _ -> signOutUserAccount()}
            .setNegativeButton(R.string.btn_delBox_No) {_, _ -> }
            .create()
            .show()


    private fun showDeleteUserDialog(): Unit =
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.Box_delAccount)
            .setPositiveButton(R.string.btn_delBox_Yes) { _, _ -> deleteUserAccount() }
            .setNegativeButton(R.string.btn_delBox_No) {_, _ -> }
            .create()
            .show()
}