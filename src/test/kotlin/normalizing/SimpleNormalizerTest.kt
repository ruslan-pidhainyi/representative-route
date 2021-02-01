package normalizing

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pipeline.Normalizer
import pipeline.Trip

class SimpleNormalizerTest {

    @Test
    fun `should filter observations with insufficient number of points`(){
        //given
        val normalizer : Normalizer = SimpleNormalizer(minNumberOfPoints = 8, maxDuration = 20, fromPort = "D")
        val observations : List<Trip> = listOf(
            Trip("D", "F", 10, listOf(), 13),
            Trip("D", "F", 4, listOf(), 11),
            Trip("D", "F", 8, listOf(), 11)
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
        val observations : List<Trip> = listOf(
            Trip("D", "F", 10, listOf(), 13),
            Trip("D", "F", 4, listOf(), 9),
            Trip("D", "F", 8, listOf(), 8),
            Trip("D", "F", 8, listOf(), 7)
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
        val observations : List<Trip> = listOf(
            Trip("D", "F", 10, listOf(), 13),
            Trip("W", "F", 4, listOf(), 9)
        )
        //when
        val normalizedData = normalizer.normalize(observations)
        //then
        Assertions.assertEquals(1, normalizedData.size)
    }
}
