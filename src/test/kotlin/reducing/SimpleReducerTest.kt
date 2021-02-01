package reducing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import pipeline.Coordinate
import pipeline.Observation
import pipeline.Reducer

class SimpleReducerTest {
    @Test
    fun `should reduce data`(){
        //given
        val reducer : Reducer = SimpleReducer()
        val observations : List<Observation> = listOf()
        //when
        val coordinate : List<Coordinate> = reducer.reduce(observations)
        //then
    }
}
