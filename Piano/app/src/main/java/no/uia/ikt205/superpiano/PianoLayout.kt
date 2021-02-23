package no.uia.ikt205.superpiano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_piano_layout.view.*
import no.uia.ikt205.superpiano.databinding.FragmentPianoLayoutBinding
import no.uia.ikt205.superpiano.databinding.FragmentPianoLayoutBinding.*


class PianoLayout : Fragment() {

    private var _binding:FragmentPianoLayoutBinding? = null
    private val binding get() = _binding!!

    private val whiteFullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C2", "D2", "E2", "F2", "G2", "A2", "B2") // 14 her

    private val blackHalfTones = listOf("C#", "D#", "F#", "G#", "A#", "C#2", "D#2", "F#2", "G#2", "A#2") // 10 her



    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = inflate(layoutInflater)
        val view = binding.root

        val fm_white = childFragmentManager //fm (fragmentManager)
        val ft_white = fm_white.beginTransaction() // ft (fragmentTransaction)

        val fm_black = childFragmentManager
        val ft_black = fm_black.beginTransaction()

        whiteFullTones.forEach() {
            val whitePianoKey = FulltonePianoKeyFragment.newInstance(it)

            whitePianoKey.onKeyDown = {
                println("White piano key down: $it")
            }

            whitePianoKey.onKeyUp = {
                println("White piano key up: $it")
            }

            ft_white.add(view.layoutWhitePianoKeys.id, whitePianoKey, "note_$it")

        }

        ft_white.commit()


        blackHalfTones.forEach() {
            val blackPianoKey = HalftonePianoKeyFragment.newInstance(it)

            blackPianoKey.onKeyDown = {
                println("Black piano key down: $it")
            }

            blackPianoKey.onKeyUp = {
                println("Black piano key up: $it")
            }

            ft_black.add(view.layoutBlackPianoKeys.id, blackPianoKey, "note_$it")

        }

        ft_black.commit()


        return view

        // Inflate the layout for this fragment
       //return inflater.inflate(R.layout.fragment_piano_layout, container, false)

    }


}
