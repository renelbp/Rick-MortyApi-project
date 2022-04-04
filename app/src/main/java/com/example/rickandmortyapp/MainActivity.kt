package com.example.rickandmortyapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmortyapp.databinding.ActivityMainBinding
import com.example.rickandmortyapp.epoxy.CharacterDetailsEpoxyController
import com.example.rickandmortyapp.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }
    private val epoxyController = CharacterDetailsEpoxyController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getIntExtra(Constants.INTENT_EXTRA_CHARACTER_ID,1)
        viewModel.refreshCharacter(characterId = id)
        viewModel.characterByIdLiveData.observe(this) { character ->
            epoxyController.character = character
            if (character == null) {
                Toast.makeText(this@MainActivity, "Unsuccesful Network Call", Toast.LENGTH_SHORT)
                    .show()
                return@observe
            }
        }
        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.recycler_view)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
        /*
        //WHITOUTVIEWMODEL
        rickAndMortService.getCharacterById(100).enqueue(object: Callback<GetCharacterByIdResponse>{
              override fun onResponse(call: Call<GetCharacterByIdResponse>, response: Response<GetCharacterByIdResponse>) {
              Log.i("MAIN_ACTIVITY", response.toString())

                  if (!response.isSuccessful){
                      Toast.makeText(this@MainActivity,"Unsuccesful Network Call", Toast.LENGTH_SHORT).show()
                      return
                  }
                  val body = response.body()!!

                  Picasso.get().load(body.image).into(binding.ivHeader)
                  binding.tvName.text = body.name
                  binding.tvAlive.text = body.status
                  binding.tvOrigin.text = body.origin.name
                  binding.tvSpecies.text = body.species
                  if (body.gender.contentEquals("Male")){
                      binding.ivGender.setImageResource(R.drawable.ic_gender_male_24)
                  }else binding.ivGender.setImageResource(R.drawable.ic_gender_female_24)
              }
              override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
                  Log.i("MAIN_ACTIVITY", t.message ?: "Null mesage")
              }
          })
         */

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}