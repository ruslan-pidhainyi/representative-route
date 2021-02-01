package normalizing

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pipeline.Normalizer
import pipeline.Observation

class SimpleNormalizerTest {

    @Test
    fun `should filter observations with insufficient number of points`(){
        //given
        val normalizer : Normalizer = SimpleNormalizer(minNumberOfPoints = 8, maxDuration = 20, fromPort = "D")
        val observations : List<Observation> = listOf(
            Observation("D", "F", 10, "11", 13),
            Observation("D", "F", 4, "11", 11),
            Observation("D", "F", 8, "11", 11)
        )
        //when
        val normalizedData = normalizer.normalize(observations)
        //then
        Assertions.assertEquals(2, normalizedData.size)
    }

    @Test
    fun `should filter too long trips`(){
        //given
        val normalizer : Normalizer = SimpleNormalizer(minNumberOfPoints = 2, maxDuration = 10, "D")
        val observations : List<Observation> = listOf(
            Observation("D", "F", 10, "11", 13),
            Observation("D", "F", 10, "11", 9),
            Observation("D", "F", 4, "11", 8),
            Observation("D", "F", 8, "11", 5)
        )
        //when
        val normalizedData = normalizer.normalize(observations)
        //then
        Assertions.assertEquals(3, normalizedData.size)
    }

    @Test
    fun `should filter trips with wrong direction`(){
        //given
        val normalizer : Normalizer = SimpleNormalizer(minNumberOfPoints = 2, maxDuration = 20, "D")
        val observations : List<Observation> = listOf(
            Observation("D", "F", 10, "11", 13),
            Observation("W", "F", 10, "11", 9),
            Observation("W", "F", 4, "11", 8),
        )
        //when
        val normalizedData = normalizer.normalize(observations)
        //then
        Assertions.assertEquals(1, normalizedData.size)
    }
}
