package pipeline

interface Pipeline {
    fun runForData(fileName : String) : String
}

class SimplePipeline(
    val uploader: Uploader,
    val normalizer: Normalizer,
    val reducer: Reducer,
    val export: Export
) : Pipeline {
    override fun runForData(fileName: String) : String {
        val data = uploader.uploadFromFile(fileName)
        val normalizedData = normalizer.normalize(data)
        val coordinates = reducer.reduce(normalizedData)
        return export.export(coordinates)
    }
}




