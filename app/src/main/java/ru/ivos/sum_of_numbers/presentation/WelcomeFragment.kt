package ru.ivos.sum_of_numbers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ru.ivos.sum_of_numbers.R
import ru.ivos.sum_of_numbers.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding get() = _binding ?: throw RuntimeException("Binding is null")
    private lateinit var btnUnderstand: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnUnderstand = binding.buttonUnderstand
        btnUnderstand.setOnClickListener {
            launchLevelFragment()
        }
    }

    private fun launchLevelFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_container, ChooseLevelFragment.newInstance())
            ?.addToBackStack(ChooseLevelFragment.CHOOSE_FRAGMENT_NAME)
            ?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}