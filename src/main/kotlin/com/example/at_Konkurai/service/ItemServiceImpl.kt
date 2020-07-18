package com.example.at_Konkurai.service

import com.example.at_Konkurai.app.EditForm
import com.example.at_Konkurai.dao.ItemDao
import com.example.at_Konkurai.entity.Item
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemServiceImpl @Autowired constructor(private val itemDao: ItemDao): ItemService {
    override fun findUser(username: String?, password: String?): Int {
        return itemDao.findUser(username, password)
    }

    override fun makeTodayItemInfo(id: Int) {
        itemDao.makeTodayItemInfo(id)
    }

    override fun findItemInfo(id :Int): List<Item> {
        return itemDao.findItemInfo(id)
    }

    override fun updateItemInfo(editForm: EditForm) {
        itemDao.updateItemInfo(editForm)
    }

    override fun deleteItemInfo(editForm: EditForm) {
        itemDao.deleteItemInfo(editForm)
    }

    override fun insertItem(editForm: EditForm) {
        itemDao.insertItem(editForm)
    }
}