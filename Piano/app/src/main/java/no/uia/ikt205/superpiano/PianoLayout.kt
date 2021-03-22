package no.uia.ikt205.superpiano

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.fragment_piano_layout.view.*
import no.uia.ikt205.superpiano.databinding.FragmentPianoLayoutBinding
import no.uia.ikt205.superpiano.databinding.FragmentPianoLayoutBinding.*
import no.uia.ikt205.superpiano.data.Note
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class PianoLayout : Fragment() {

    private var _binding:FragmentPianoLayoutBinding? = null
    private val binding get() = _binding!!

    private val whiteFullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C2", "D2", "E2", "F2", "G2", "A2", "B2") // 14 her

    private val blackHalfTones = listOf("C#", "D#", "F#", "G#", "A#", "C#2", "D#2", "F#2", "G#2", "A#2") // 10 her

    private var score:MutableList<Note> = mutableListOf() 	// score == Noteark?


    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)}

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = inflate(layoutInflater)
        val view = binding.root

        val fm = childFragmentManager //fm (fragmentManager)

        val ft = fm.beginTransaction() // ft (fragmentTransaction)

        whiteFullTones.forEach() {originalWhiteNoteValue ->
            val whitePianoKey = FulltonePianoKeyFragment.newInstance(originalWhiteNoteValue)
            var startPlay:Long = 0

            whitePianoKey.onKeyDown = {noteW ->
                startPlay = System.currentTimeMillis()

                println("White piano key down: $noteW")
            }

            whitePianoKey.onKeyUp = {
                var endPlay = System.currentTimeMillis()

                val noteW = Note(it, startPlay, endPlay)

                score.add(noteW)

                println("White piano key up: $noteW")
            }

            ft.add(view.layoutWhitePianoKeys.id, whitePianoKey, "note_$originalWhiteNoteValue")

        }



        blackHalfTones.forEach() {originalBlackNoteValue ->
            val blackPianoKey = HalftonePianoKeyFragment.newInstance(originalBlackNoteValue)
            var startPlay:Long = 0

            blackPianoKey.onKeyDown = {noteB ->
                startPlay = System.currentTimeMillis()

                println("Black piano key down: $noteB")
            }

            blackPianoKey.onKeyUp = {
                var endPlay = System.currentTimeMillis()

                val noteB = Note(it, startPlay, endPlay)

                println("Black piano key up: $noteB")
            }

            ft.add(view.layoutBlackPianoKeys.id, blackPianoKey, "note_$originalBlackNoteValue")

        }

        ft.commit()

        view.saveScoreBt.setOnClickListener {
            var fileName = view.fileNameTextEdit.text.toString()
            fileName = "$fileName.musikk"
            val path = this.activity?.getExternalFilesDir(null)


            // test om fil med samme navn allerede eksisterer, tenker å flytte testen senere
            if (File(path, fileName).exists()) {

                val fileExists = AlertDialog.Builder(it.context)
                fileExists.setMessage("A file with that name already exists")
                fileExists.setNeutralButton("ok", {dialogInterface: DialogInterface, i: Int ->})
                fileExists.show()
            }

            else
            {       // hvis antall noter er mer enn 0 og filen har navn og pathen ikke er 0
                if (score.count() > 0 && fileName.isNotEmpty() && path != null){

                    FileOutputStream(File(path,fileName),true).bufferedWriter().use { writer ->
                        // bufferedWriter lever her
                        score.forEach {
                            writer.write("${it.toString()}\n")
                        }
                    }
                }
                else {
                    val fileNotSaved= AlertDialog.Builder(it.context)
                    fileNotSaved.setMessage("ERROR: File NOT saved")
                    fileNotSaved.setNeutralButton("ok", {dialogInterface: DialogInterface, i: Int ->})
                    fileNotSaved.show()
                     }
            }
        }


        view.showScoreBt.setOnClickListener {
            getAllFilesInResources()
        }

        return view

        // Inflate the layout for this fragment
       //return inflater.inflate(R.layout.fragment_piano_layout, container, false)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllFilesInResources()
    {
        var savedScore:String = ""
        val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
        val resourcesPath = Paths.get(projectDirAbsolutePath, "/sdcard/Android/data/no.uia.ikt205.superpiano/files")
        val paths = Files.walk(resourcesPath)
                .filter { item -> Files.isRegularFile(item) }
                .filter { item -> item.toString().endsWith(".musikk") }
                .forEach { item -> savedScore += " ${item.fileName}\n " + showNotesInScore(item) + "    " }

        val showSavedScoresPopup = AlertDialog.Builder(requireContext())
        showSavedScoresPopup.setTitle("Saved Scores: ")
        showSavedScoresPopup.setMessage(savedScore)
        showSavedScoresPopup.setNeutralButton("ok", {dialogInterface: DialogInterface, i: Int ->})
        showSavedScoresPopup.show()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotesInScore(x: Path):String {
        val bufferedReader: BufferedReader = File ("$x").bufferedReader()
        val scoreInnhold = bufferedReader.use {it.readText()}

        return scoreInnhold

    }

}



