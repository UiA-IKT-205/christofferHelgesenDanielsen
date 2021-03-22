package no.uia.ikt205.superpiano.data

data class Note(val value:String, val start:Long, val end:Long){

    override fun toString(): String {

        val time :Long = end-start

        return "$value, $start, $time"
    }
}