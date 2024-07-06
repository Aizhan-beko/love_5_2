package com.geeks.my_application.ui.fragments.calculate_fragment


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geeks.my_application.data.local.HistoryDao
import com.geeks.my_application.data.local.HistoryEntity
import com.geeks.my_application.data.network.LoveApiService
import com.geeks.my_application.ui.fragments.result_fragment.LoveResult
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CalculationViewModel @Inject constructor(
    private val api:LoveApiService,
    private val dao: HistoryDao
):ViewModel() {

    val loveResultData = MutableLiveData<LoveResult>()
    val errorData = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    fun getPercentage(firstName:String, secondName:String){
        loading.postValue(true)

    api.getPercentage(
    firstName = firstName,
    key = "b828eb47e0msh5b8e1cabea9eef6p152ea6jsn7e42ac9c829c",
    host = "love-calculator.p.rapidapi.com",
    secondName = secondName
    ).enqueue(object : Callback<LoveResult> {

        override fun onResponse(call: Call<LoveResult>, response: Response<LoveResult>) {
            loading.postValue(false)
            if (response.isSuccessful && response.body() != null) {
                val loveResult = response.body()!!
                saveToHistory(firstName, secondName, loveResult)
                loveResultData.postValue(loveResult)
            } else {
                errorData.postValue("Could not get a correct answer")
            }
        }

        override fun onFailure(call: Call<LoveResult>, t: Throwable) {
            loading.postValue(false)
           errorData.postValue( "Connection error")
        }
    })
}
    private fun saveToHistory(firstName: String, secondName: String, loveResult: LoveResult) {
            val historyEntity = HistoryEntity(
                firstName = firstName,
                secondName = secondName,
                result = loveResult.result,
                percentage = loveResult.percentage
            )
            dao.insertHistory(historyEntity)

        }
    }