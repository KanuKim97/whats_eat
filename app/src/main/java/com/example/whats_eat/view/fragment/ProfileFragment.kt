package com.example.whats_eat.view.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.whats_eat.R
import com.example.whats_eat.data.remote.AppRepository
import com.example.whats_eat.view.activity.LoginActivity
import com.example.whats_eat.databinding.FragmentProFileBinding
import com.example.whats_eat.viewModel.ViewModelFactory
import com.example.whats_eat.viewModel.fragment.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var vmFactory: ViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var proFileBinding: FragmentProFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmFactory = ViewModelFactory(AppRepository())
        profileViewModel = ViewModelProvider(this, vmFactory)[ProfileViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        proFileBinding = FragmentProFileBinding.inflate(layoutInflater)
        return proFileBinding.root
    }

    override fun onResume() {
        super.onResume()

        proFileBinding.updateBtn.setOnClickListener(this)
        proFileBinding.deleteBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.updateBtn ->
                Toast.makeText(requireContext(), "not updated yet", Toast.LENGTH_SHORT).show()
            R.id.deleteBtn -> showDelAccountDialog()
        }
    }

    private fun showDelAccountDialog() = AlertDialog.Builder(activity)
        .setTitle("계정을 삭제하시겠습니까?")
        .setMessage("계정을 삭제 하시겠습니까? \n다시 복구할 수 없습니다.")
        .setPositiveButton("네") {_, _ -> profileViewModel.deleteAccount() }
        .setNegativeButton("아니요") {_, _ ->}
        .show()
}