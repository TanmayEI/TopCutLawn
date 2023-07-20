package com.topcutlawn.API

class APIUtils {
    companion object{

        private const val BASE_URL = "https://demo.equalinfotech.com/top_cut_lawn/"
        fun getServiceAPI(): APIConfiguration? {
            return  APIClient.getApiClient(BASE_URL)!!.create(APIConfiguration::class.java)
        }

    }

}