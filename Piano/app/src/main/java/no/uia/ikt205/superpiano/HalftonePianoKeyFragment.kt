package no.uia.ikt205.superpiano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_halftone_piano_key.view.*
import no.uia.ikt205.superpiano.databinding.FragmentHalftonePianoKeyBinding


class HalftonePianoKeyFragment : Fragment() {
    private var _binding:FragmentHalftonePianoKeyBinding? = null
    private val binding get() = _binding!!

    private lateinit var note:String

    var onKeyDown:((note:String) -> Unit)? = null
    var onKeyUp:((note:String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getString("NOTE") ?: "?"

            // ?: "?" = if note returns null, the note is set to ?.
            // ?: er en elvis operator, brukes til å sette en standardverdi. Prøv å unngå bruk av denne.
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentHalftonePianoKeyBinding.inflate(inflater)
        val view = binding.root

        view.blackPianoKeyButton.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> this@HalftonePianoKeyFragment.onKeyDown?.invoke(note)
                    MotionEvent.ACTION_UP -> this@HalftonePianoKeyFragment.onKeyUp?.invoke(note)
                }
                return true
            }
        })

        return view
        // return inflater.inflate(R.layout.fragment_white_piano_key, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(note: String) =
            HalftonePianoKeyFragment().apply {
                arguments = Bundle().apply {
                    putString("NOTE", note)

                }
            }
    }
}