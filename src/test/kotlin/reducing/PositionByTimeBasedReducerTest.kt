package reducing

import org.junit.jupiter.api.Test
import pipeline.Reducer
import pipeline.Trip

class PositionByTimeBasedReducerTest {
    @Test
    fun `should reduce data`(){
        //given
        val reducer : Reducer = PositionByTimeBasedReducer()
        val observations : List<Trip> = listOf()
        //when
        // val coordinate : List<Coordinate> = reducer.reduce(observations)
        //then
    }
}
