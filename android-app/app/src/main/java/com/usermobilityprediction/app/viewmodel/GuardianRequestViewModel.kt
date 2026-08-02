package com.usermobilityprediction.app.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usermobilityprediction.app.data.model.PendingRequestResponse
import com.usermobilityprediction.app.data.repository.GuardianRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class GuardianRequestViewModel : ViewModel() {


    private val repository =
        GuardianRepository()



    private val _requests =
        MutableStateFlow<List<PendingRequestResponse>>(
            emptyList()
        )

    val requests:
            StateFlow<List<PendingRequestResponse>> =
        _requests



    private val _loading =
        MutableStateFlow(false)

    val loading:
            StateFlow<Boolean> =
        _loading



    private val _message =
        MutableStateFlow<String?>(null)

    val message:
            StateFlow<String?> =
        _message



    private val _error =
        MutableStateFlow<String?>(null)

    val error:
            StateFlow<String?> =
        _error




    // Load pending guardian requests

    fun loadRequests() {


        viewModelScope.launch {


            _loading.value = true


            try {


                val response =
                    repository.getPendingRequests()



                if(response.isSuccessful) {


                    _requests.value =
                        response.body()
                            ?: emptyList()


                }
                else {


                    _error.value =
                        response.errorBody()
                            ?.string()
                            ?: "Unable to load requests"


                }



            }
            catch(e: Exception) {


                _error.value =
                    e.localizedMessage
                        ?: "Network Error"


            }
            finally {


                _loading.value = false


            }


        }


    }





    // Accept guardian

    fun acceptRequest(
        requestId: Int
    ) {


        viewModelScope.launch {


            try {


                val response =
                    repository.acceptRequest(
                        requestId
                    )


                if(response.isSuccessful) {


                    _message.value =
                        "Guardian connected successfully"


                    loadRequests()


                }


            }
            catch(e: Exception) {


                _error.value =
                    e.localizedMessage


            }


        }


    }





    // Reject guardian

    fun rejectRequest(
        requestId: Int
    ) {


        viewModelScope.launch {


            try {


                val response =
                    repository.rejectRequest(
                        requestId
                    )


                if(response.isSuccessful) {


                    _message.value =
                        "Request rejected"


                    loadRequests()


                }


            }
            catch(e: Exception) {


                _error.value =
                    e.localizedMessage


            }


        }


    }

}