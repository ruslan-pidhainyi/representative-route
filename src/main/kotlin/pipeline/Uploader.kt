package pipeline

interface Uploader {
    fun uploadFromFile(fileName : String) : List<Trip>
}
