package com.app.qfonapppracticalapp.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.qfonapppracticalapp.model.Datum
import com.app.qfonapppracticalapp.R
import com.app.qfonapppracticalapp.ui.adapter.AirWaysRecyclerviewAdapter
import com.app.qfonapppracticalapp.ui.viewmodel.AirWaysListViewModel
import com.app.qfonapppracticalapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding
    private val viewModel: AirWaysListViewModel = AirWaysListViewModel()
    private var airWaysAdapter: AirWaysRecyclerviewAdapter? = null
    private var arrayList = ArrayList<Datum>()
    private var filterList = ArrayList<Datum>()

    var page = 1
    var size = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.rvAirways.layoutManager = LinearLayoutManager(this)
        airWaysAdapter = AirWaysRecyclerviewAdapter(arrayList)
        mBinding.rvAirways.adapter = airWaysAdapter
        mBinding.idPBLoading.visibility = View.GONE
        getAirWaysLIst(page)


        mBinding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                mBinding.idPBLoading.visibility = View.VISIBLE
                if (page <= 20) {
                    getAirWaysLIst(page)
                }
            }
        })

        mBinding.toolbar.imgSearch.setOnClickListener {
            if (mBinding.editSearch.visibility == View.VISIBLE) {
                mBinding.editSearch.visibility = View.GONE
                mBinding.editSearch.text?.clear()
                hideKeyboard()
            } else {
                mBinding.editSearch.visibility = View.VISIBLE
            }
        }

        mBinding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    fun filter(s: String) {
        try {
            filterList =
                arrayList.filter {
                    it.name?.contains(
                        s,
                        ignoreCase = true
                    )!! || it.airline?.get(0)?.country?.contains(s, ignoreCase = true)!!
                } as ArrayList<Datum>
            if (filterList.isNotEmpty()) {
                airWaysAdapter?.filter(filterList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
        mBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        mBinding.progressBar.visibility = View.GONE
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mBinding.editSearch.windowToken, 0)
    }
}