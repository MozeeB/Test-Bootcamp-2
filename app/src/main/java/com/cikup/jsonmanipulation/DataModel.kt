package com.cikup.jsonmanipulation


data class InventoryModel(
    val inventory_id:Int,
    val name:String,
    val type:String,
    val tags:ArrayList<String>,
    val purchased_at:Int,
    val placement:Placement
)

data class Placement(
    val room_id:Int,
    val name:String
)