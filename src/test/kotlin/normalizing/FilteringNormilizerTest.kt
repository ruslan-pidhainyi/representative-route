package normalizing

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import pipeline.Normalizer
import pipeline.Trip

class FilteringNormilizerTest {

    @Test
    fun `should filter observations with insufficient number of points`(){
        //given
        val normalizer : Normalizer = FilteringNormalizer(minNumberOfPoints = 8, maxDuration = 20)
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
        val normalizer : Normalizer = FilteringNormalizer(minNumberOfPoints = 2, maxDuration = 10,)
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
}
