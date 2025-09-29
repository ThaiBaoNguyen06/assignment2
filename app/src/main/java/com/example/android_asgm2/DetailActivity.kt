package com.example.android_asgm2


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import android.widget.*
import androidx.lifecycle.ViewModelProvider


class DetailActivity : AppCompatActivity(){
    val vm: CarVM by viewModels {ViewModelProvider.AndroidViewModelFactory.getInstance(application)}
    lateinit var creditView: TextView
    lateinit var imageView: ImageView
    lateinit var carName: TextView
    lateinit var carModel: TextView
    lateinit var carYear: TextView
    lateinit var carRate: RatingBar
    lateinit var carKm: TextView
    lateinit var carCost: TextView
    lateinit var nextButton: Button
    lateinit var backButton: Button
    lateinit var saveButton: Button
    lateinit var exitButton: Button
    lateinit var favoriteIcon : CheckBox
    lateinit var daysSeekBar : SeekBar
    lateinit var daysLabel : TextView
    var index=0
    var daysSelected=1
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity_main)
        enableEdgeToEdge()
        creditView = findViewById(R.id.credit)
        index = intent.getIntExtra("index", 0)
        favoriteIcon = findViewById(R.id.favoriteToggle)
        imageView   = findViewById(R.id.carsPicture)
        carName     = findViewById(R.id.carName)
        carModel    = findViewById(R.id.carModel)
        carYear     = findViewById(R.id.carYear)
        carRate     = findViewById(R.id.carRate)
        carKm       = findViewById(R.id.carKm)
        carCost     = findViewById(R.id.carCost)
        nextButton = findViewById(R.id.nextButton)
        backButton = findViewById(R.id.backButton)
        saveButton = findViewById(R.id.saveButton)
        exitButton = findViewById(R.id.exitButton)
        daysSeekBar = findViewById(R.id.daysSeekBar)
        daysLabel = findViewById(R.id.daysLabel)

        if (vm.availableCarList.isNotEmpty()) {
            showCar(vm.availableCarList[vm.index])
        } else {
            Toast.makeText(this, "No cars to display", Toast.LENGTH_SHORT).show()
        }

        daysSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                daysSelected = progress + 1
                daysLabel.text = getString(R.string.rentDays)+"$daysSelected"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?){}
            override fun onStopTrackingTouch(seekBar: SeekBar?){}
        })

        nextButton.setOnClickListener{
            vm.next()
            showCar(vm.availableCarList[vm.index])
            return@setOnClickListener
        }
        saveButton.setOnClickListener{
            val totalCost = vm.availableCarList[vm.index].cost * daysSelected
            if(vm.credit>=totalCost){
                vm.credit -= totalCost
                Toast.makeText(this, "Car rented successfully!", Toast.LENGTH_SHORT).show()
                creditView.text = getString(R.string.credit)+"${vm.credit}"
                vm.availableCarList[vm.index].available = false
                vm.rentCar.add(vm.availableCarList[vm.index])
                vm.availableCarList.removeAt(vm.index)
                if(vm.availableCarList.isNotEmpty()){
                    showCar(vm.availableCarList[vm.index])
                }
                else{
                    Toast.makeText(this, "No more cars available", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Not enough credit!", Toast.LENGTH_SHORT).show()
            }
            return@setOnClickListener
        }
        backButton.setOnClickListener{
            Toast.makeText(this,"Your renting has been cancelled!",Toast.LENGTH_SHORT).show()
            setResult(RESULT_CANCELED)
            finish()
        }
        exitButton.setOnClickListener{
            finish()
        }
        favoriteIcon.setOnClickListener{
            vm.availableCarList[vm.index].favorite = !vm.availableCarList[vm.index].favorite
            if(vm.availableCarList[vm.index].favorite){ vm.favoriteCars.add(vm.availableCarList[vm.index])
                Toast.makeText(this,"You added ${vm.availableCarList[vm.index].name} to your Favorite List", Toast.LENGTH_SHORT).show()}
            else{vm.favoriteCars.removeAt(vm.index)
                Toast.makeText(this,"You removed ${vm.favoriteCars[vm.index].name} to your Favorite List", Toast.LENGTH_SHORT).show()}
            favoriteIcon.isChecked = vm.availableCarList[vm.index].favorite
        }
    }
    fun showCar(avaiCars: Cars){
        creditView.text = getString(R.string.credit) + "${vm.credit}"
        carName.text = "${avaiCars.name}"
        carModel.text = "${avaiCars.model}"
        carYear.text = "${avaiCars.year}"
        carRate.rating = avaiCars.rating
        carKm.text = "${avaiCars.km}"
        carCost.text = "${avaiCars.cost}"
        imageView.setImageResource(avaiCars.image)
        favoriteIcon.isChecked = avaiCars.favorite
    }
}