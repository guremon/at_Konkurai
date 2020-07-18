package com.example.at_Konkurai.app

import java.time.LocalDateTime

class EditForm (
        val id: Int = 0,
        val itemId: Int = 0,
        val itemname: String = "",
        val category: Int = 0,
        val total_amount: Int = 0,
        val remaining_amount: Int = 0,
        val unit: Int = 0,
        val amount_to_use: Int = 0,
        val amount_score: Int = 0,
        val frequency_of_use: Int = 0,
        val stock: Int = 0,
        val registeredDateTime: LocalDateTime?,
        val lastModifiedDateTime: LocalDateTime?)