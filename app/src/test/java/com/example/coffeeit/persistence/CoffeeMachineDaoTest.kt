package com.example.coffeeit.persistence

import com.example.coffeeit.model.CoffeeMachine
import com.example.coffeeit.utils.MockTestUtil.mockCoffeeMachine
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class CoffeeMachineDaoTest : LocalDatabase() {

    private lateinit var coffeeMachineDao: CoffeeMachineDao

    @Before
    fun init() {
        coffeeMachineDao = db.coffeeMachineDao()
    }

    @Test
    fun insertAndLoadCoffeeMachineTest() = runBlocking {
        val mockData = mockCoffeeMachine()
        coffeeMachineDao.insertCoffeeMachineData(mockData)

        val loadFromDB = coffeeMachineDao.getCoffeeMachineData()
        assertThat(loadFromDB.toString(), `is`(mockData.toString()))

        val mockDataTest = CoffeeMachine.mock()
        assertThat(loadFromDB.toString(), `is`(mockDataTest.toString()))
    }
}
