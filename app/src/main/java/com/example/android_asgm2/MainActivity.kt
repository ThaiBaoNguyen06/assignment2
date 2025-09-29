package com.example.android_asgm2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import android.widget.*
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.activity.viewModels
import android.app.*
import android.view.*
import androidx.lifecycle.ViewModelProvider


@Parcelize
data class Cars(val name: String, val model: String, val year: Int, val rating: Float, val km: Int, val cost: Int,var available:Boolean, var rent:Boolean, var favorite: Boolean, val image: Int) : Parcelable

class MainActivity : AppCompatActivity() {
    val vm: CarVM by viewModels {ViewModelProvider.AndroidViewModelFactory.getInstance(application)}
    lateinit var availableCarList: ArrayList<Cars>
    lateinit var rentCarList: ArrayList<Cars>
    lateinit var favCarList: ArrayList<Cars>
    lateinit var creditView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        creditView = findViewById(R.id.credit)
        val rentButton = findViewById<Button>(R.id.rentButton)
        val favButton = findViewById<Button>(R.id.favoriteButton)
        availableCarList = ArrayList(vm.availableCarList)
        rentCarList = ArrayList(vm.rentCar)
        favCarList = ArrayList(vm.favoriteCars)
        updateScreen()
        onActivityResult(100, Activity.RESULT_OK, intent)

        rentButton.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("index", vm.index)
            intent.putExtra("credit", vm.credit)
            startActivity(intent)
        }
        favButton.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.favoriteFragment,
                FavoriteCarFragment()).addToBackStack(null).commit()
        }
    }
    fun updateScreen() {
        creditView.text = getString(R.string.credit) + "${vm.credit}"
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            availableCarList = data?.getParcelableArrayListExtra("availableCarList") ?: arrayListOf()
            rentCarList = data?.getParcelableArrayListExtra("rentCarList") ?: arrayListOf()
            favCarList = data?.getParcelableArrayListExtra("favoriteCarList") ?: arrayListOf()
            vm.rentCar.clear()
            vm.rentCar.addAll(rentCarList)
            vm.availableCarList.clear()
            vm.availableCarList.addAll(availableCarList)
            vm.favoriteCars.clear()
            vm.favoriteCars.addAll(favCarList)

        }
    }
    override fun onCreateOptionsMenu(menu:Menu):Boolean{
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterCars(query)
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                filterCars(query)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sort_rating ->{
                vm.availableCarList.sortByDescending{it.rating}
                Toast.makeText(this, "Sorted by rating", Toast.LENGTH_SHORT).show()
            }
            R.id.sort_year->{
                vm.availableCarList.sortByDescending {it.year}
                Toast.makeText(this, "Sorted by year", Toast.LENGTH_SHORT).show()
            }
            R.id.sort_cost->{
                vm.availableCarList.sortBy{it.cost}
                Toast.makeText(this, "Sorted by cost", Toast.LENGTH_SHORT).show()
            }
        }
        vm.index=0
        return super.onOptionsItemSelected(item)
    }
    fun filterCars(query: String?){
        if(query.isNullOrEmpty()){
            vm.index=0
        }
        else{
            val result = vm.availableCarList.indexOfFirst{
                it.name.contains(query, ignoreCase = true) || it.model.contains(query, ignoreCase=true)
            }
            if(result>0){
                vm.index=result
                Toast.makeText(this, "Found: ${vm.availableCarList[vm.index]}", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}