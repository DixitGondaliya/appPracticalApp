//dependency 

    //Scalable DP and SP
    implementation("com.intuit.ssp:ssp-android:1.0.6")
    implementation ("com.intuit.sdp:sdp-android:1.0.6")

    //Network
    implementation 'com.squareup.okhttp3:okhttp:5.0.0-alpha.8'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.8'

    //image load
    implementation 'com.squareup.picasso:picasso:2.71828'

    // Gson
    implementation("com.google.code.gson:gson:2.8.9")

    // coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")

    //Api Interface
    interface ApiInterface {

    @GET("qfonapp-passenger")
    fun getAirWaysList(@Query("page") page: Int,
                       @Query("size") size: Int): Call<AirwaysModelClass?>?
                       
                       }

// retrofit client

object RetrofitClient {

    var BASE_URL = " https://general.63-141-249-130.plesk.page/"

    var apiInterface: ApiInterface? = null

    fun getApiInterfaceWithoutAuth(): ApiInterface? {
        return if (apiInterface == null) {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient =
                OkHttpClient.Builder()  .addInterceptor(interceptor).addInterceptor(
                    Interceptor { chain ->
                        val request =
                            chain.request().newBuilder().addHeader("app_type", "user")
                                .addHeader("device_type", "android")
                                .addHeader("version", BuildConfig.VERSION_NAME).build()
                        chain.proceed(request)
                    })
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .retryOnConnectionFailure(true)
                    .cache(null)
                    .readTimeout(1, TimeUnit.MINUTES).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            apiInterface = retrofit.create(ApiInterface::class.java)
            apiInterface
        } else {
            apiInterface
        }
    }
}

//repository

class AirWaysListRepository {


    fun airWaysList(page : Int,size : Int): MutableLiveData<Response<AirwaysModelClass?>> {
        val res: MutableLiveData<Response<AirwaysModelClass?>> = MutableLiveData()
        RetrofitClient.getApiInterfaceWithoutAuth()?.getAirWaysList(page,size)
            ?.enqueue(object : Callback<AirwaysModelClass?> {
                override fun onResponse(call: Call<AirwaysModelClass?>, response: Response<AirwaysModelClass?>) {
                    res.value = response
                }

                override fun onFailure(call: Call<AirwaysModelClass?>, t: Throwable) {
                    res.value = null
                }
            })
        return res
    }
}

//viewmodel

class AirWaysListViewModel:ViewModel() {
    private val airWaysListRepository: AirWaysListRepository = AirWaysListRepository()
    fun airWaysList(page : Int,size : Int): MutableLiveData<Response<AirwaysModelClass?>> {
       return airWaysListRepository.airWaysList(page,size)
    }
}

// activity

 private lateinit var binding: ActivityCryptocurrencyBinding
    private lateinit var viewModel: CryptoViewModel
    private lateinit var adapter: CryptoAdapter
    private var arrayList = ArrayList<CryptoDataModel>()
    private var currentPage = 1
    private var isLoading = false

     binding = ActivityCryptocurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)

        setupRecyclerView()
        setupScrollListener()
        fetchCoinData(currentPage)

        private fun setupRecyclerView() {
        binding.rvCryptocurrency.layoutManager = LinearLayoutManager(this)
        adapter = CryptoAdapter(arrayList, this)
        binding.rvCryptocurrency.adapter = adapter
    }

    private fun setupScrollListener() {
        binding.rvCryptocurrency.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadMoreData()
                }
            }
        })
    }

    private fun loadMoreData() {
        isLoading = true
        binding.idPBLoading.visibility = View.VISIBLE
        currentPage++
        fetchCoinData(currentPage)
    }

    private fun fetchCoinData(currentPage: Int) {
        if (currentPage == 1) {
            showProgressBar()
        }

        val vs_currency = "usd"
        val order = "market_cap_desc"
        val per_page = 10
        val sparkline = false

        viewModel.getCryptoDetails(vs_currency, order, per_page, currentPage, sparkline).observe(this, Observer { result ->
            isLoading = false
            binding.idPBLoading.visibility = View.GONE
            hideProgressBar()

            result.fold(onSuccess = { response ->
                response.body()?.let { data ->
                    arrayList.addAll(data)
                    adapter.notifyDataSetChanged()
                }
            }, onFailure = {
                Toast.makeText(this, "Error occurred: ${it.message}", Toast.LENGTH_SHORT).show()
            })
        })
    }

    private fun getAirWaysLIst(page: Int) {
        if (page == 1){
            showProgressBar()
        }else{
            hideProgressBar()
        }


        viewModel.airWaysList(this.page, size).observe(this, Observer {
            mBinding.idPBLoading.visibility = View.GONE
            hideProgressBar()
            if (it == null) {
                Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT)
                    .show()
                return@Observer
            }
            if (it.body() == null) {
                Toast.makeText(this, it.message(), Toast.LENGTH_SHORT).show()
                return@Observer
            }
            if (it.body()?.data?.isEmpty()!!) {
                Toast.makeText(this, it.message(), Toast.LENGTH_SHORT).show()
                return@Observer
            }
            arrayList.addAll(it.body()?.data!!)
            Log.d("TAG", "DIXIT getAirWaysLIst: "+arrayList.size + " " + page)
            airWaysAdapter?.notifyDataSetChanged()



        })
    }


    private fun showProgressBar() {
        binding.pbLoad.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbLoad.visibility = View.GONE
    }
