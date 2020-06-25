package com.example.at_Konkurai.dao

import com.example.at_Konkurai.app.EditForm
import com.example.at_Konkurai.entity.Item
import jdk.jfr.Percentage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

@Repository
class ItemDaoImpl @Autowired constructor(val jdbcTemplate: JdbcTemplate) : ItemDao {
    override fun findUser(username: String?, password: String?): Int {
        val findUserSql = "SELECT id FROM user WHERE username = ? AND password = ?"

        val result = jdbcTemplate.queryForList(findUserSql, username, password)

        return if (result.isNotEmpty()) result[0]["id"] as Int else 0
    }

    override fun makeTodayItemInfo(id: Int) {
        val itemList = findItemInfo(id)

        if (itemList.isNotEmpty()) {
            itemList.forEach() { item ->

                // 当日初回アクセスの場合のみアイテムの各情報を更新
                if (item.lastModifiedDateTime.toLocalDate() != LocalDate.now()) {

                    // ログイン時点でのアイテムの残量を計算
                    var nowRemaining = item.remaining_amount - (item.amount_to_use
                            * (ChronoUnit.DAYS.between(item.registeredDateTime, LocalDateTime.now()).toInt()
                            / item.frequency_of_use))

                    // 現在の在庫を保持する変数
                    var nowStock = item.stock

                    // アイテムの残量がマイナスの場合、在庫があれば残量を補充
                    while (nowRemaining <= 0) {
                        if (nowStock > 0) {
                            nowRemaining += item.total_amount
                            nowStock -= 1
                        } else {
                            nowRemaining = 0
                            break
                        }
                    }

                    // 総量と残量の割合から残量のスコアを計算
                    val nowPercentage = if (nowRemaining > 0) (nowRemaining.toDouble() / item.total_amount.toDouble() * 100).toInt() else nowRemaining
                    var score = 0

                    when (nowPercentage) {
                        in 0..20 -> score = 1
                        in 21..40 -> score = 2
                        in 41..60 -> score = 3
                        in 61..80 -> score = 4
                        in 81..100 -> score = 5
                    }

                    jdbcTemplate.update("UPDATE item SET remaining_amount = ?, amount_score = ?, stock = ?, lastModifiedDateTime = ? WHERE itemname = ?",
                            nowRemaining, score, nowStock, LocalDateTime.now(), item.itemname)
                }
            }
        }
    }

    override fun findItemInfo(id: Int): List<Item> {
        val findItemInfoSql = "SELECT * FROM item WHERE id = ?"
        val result = jdbcTemplate.queryForList(findItemInfoSql, id)
        val itemList: MutableList<Item> = ArrayList()
        result.forEach{
            it -> val item = Item(
                it["id"] as Int,
                it["itemId"] as Int,
                it["itemname"] as String,
                it["category"] as Int,
                it["total_amount"] as Int,
                it["remaining_amount"] as Int,
                it["unit"] as Int,
                it["amount_to_use"] as Int,
                it["amount_score"] as Int,
                it["frequency_of_use"] as Int,
                it["stock"] as Int,
                (it["registeredDateTime"] as Timestamp?)!!.toLocalDateTime(),
                (it["lastModifiedDateTime"] as Timestamp?)!!.toLocalDateTime()
            )
            itemList.add(item)
        }
        return itemList
    }

    override fun updateItemInfo(editForm: EditForm) {
        jdbcTemplate.update("UPDATE item SET itemname = ?, category = ?, total_amount = ?, remaining_amount = ?, unit = ?, " +
                "amount_to_use = ?, frequency_of_use = ?, stock = ?, registeredDateTime = ?, lastModifiedDateTime = ?" +
                "WHERE id = ? AND itemId = ?",
                editForm.itemname, editForm.category, editForm.total_amount, editForm.remaining_amount, editForm.unit,
                editForm.amount_to_use, editForm.frequency_of_use, editForm.stock, LocalDateTime.now(), LocalDateTime.now(),
                editForm.id, editForm.itemId)
    }

    override fun deleteItemInfo(editForm: EditForm) {
        jdbcTemplate.update("DELETE FROM item WHERE id = ? AND itemId = ?", editForm.id, editForm.itemId)
    }
}