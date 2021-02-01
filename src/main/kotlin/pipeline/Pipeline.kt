package pipeline

interface Pipeline {
    fun runForData(fileName : String) : String
}

class SimplePipeline(
    private val uploader: Uploader,
    private val normalizer: Normalizer,
    private val reducer: Reducer,
    private val exporter: Exporter
) : Pipeline {
    override fun runForData(fileName: String) : String {
        val data = uploader.uploadFromFile(fileName)
        val normalizedData = normalizer.normalize(data)
        val coordinates = reducer.reduce(normalizedData)
        return exporter.export(coordinates)
    }
}




