package com.example.pamuts.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pamuts.LoginActivity
import com.example.pamuts.MainActivity
import com.example.pamuts.databinding.FragmentProfileBinding
import com.example.pamuts.databinding.MovieDetailBinding
import com.google.firebase.database.DatabaseReference

class MovieDetailFragment : Fragment() {

    private var _binding: MovieDetailBinding? = null
    private lateinit var database : DatabaseReference

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        var EXTRA_NAME = "extra_name"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailBinding.inflate(inflater, container, false)
        binding.goBack.setOnClickListener {
            activity?.let {
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
            }
        }
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.getString(EXTRA_NAME).toString()
        if (arguments != null) {
            binding.textName.text = name
        }
    }
}