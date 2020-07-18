package com.example.at_Konkurai.service

import com.example.at_Konkurai.app.EditForm
import com.example.at_Konkurai.entity.Item

interface ItemService {
    fun findUser(username: String?, password: String?): Int
    fun makeTodayItemInfo(id: Int)
    fun findItemInfo(id: Int): List<Item>
    fun updateItemInfo(editForm: EditForm)
    fun deleteItemInfo(editForm: EditForm)
    fun insertItem(editForm: EditForm)
}