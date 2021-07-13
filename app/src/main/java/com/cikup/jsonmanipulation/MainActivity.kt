package com.cikup.jsonmanipulation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val jsonFileString = getJsonDataFromAsset(applicationContext, "data.json")
//        Log.e("data", jsonFileString!!)

        val gson = Gson()
        val listInventoryType = object : TypeToken<List<InventoryModel>>() {}.type

        val inventory: List<InventoryModel> = gson.fromJson(jsonFileString, listInventoryType)

        inventory.forEachIndexed { idx, inventory ->
//            Log.e("data", "> Item $idx:\n$inventory")
            //find item in meeting room
            if (inventory.placement.name == "Meeting Room") {
                inventory.tags.forEach {
                    println(it)
//                    Log.e("data", "items: $it", )
                }
            }

            //Find all electronic devices.
            if (inventory.type == "electronic") {
                inventory.tags.forEach {
                    println(it)
//                    Log.e("data", "items: $it", )
                }
            }
            //Find all the furniture.
            if (inventory.type == "furniture") {
//                Log.e("data", "items: $inventory", )
                print(inventory)

            }
            //Find all items were purchased on 16 Januari 2020.
            if (inventory.purchased_at == 1579190642) {
//                Log.e("data", "items: $inventory", )
                print(inventory)

            }

            //Find all items with brown color.
            inventory.tags.forEach {
                if (it == "brown") {
                    print(inventory)
//                    Log.e("data", "items: $inventory")

                }
            }

        }


    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}



