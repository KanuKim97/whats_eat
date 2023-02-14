package com.example.whats_eat.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.whats_eat.R
import com.example.whats_eat.databinding.FragmentProFileBinding
import com.example.whats_eat.viewModel.fragment.ProfileViewModel
import com.google.firebase.database.*

class ProfileFragment: Fragment(), View.OnClickListener {
    private var _proFileBinding: FragmentProFileBinding? = null
    private val proFileBinding get() = _proFileBinding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _proFileBinding = FragmentProFileBinding.inflate(layoutInflater)

        proFileBinding.updateBtn.setOnClickListener(this)
        proFileBinding.deleteBtn.setOnClickListener(this)

        return proFileBinding.root
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
        .setMessage("계정을 삭제하시겠습니까? \n다시 복구할 수 없습니다.")
        .setPositiveButton("네") {_, _ -> }
        .setNegativeButton("아니요") {_, _ -> }
        .show()
}