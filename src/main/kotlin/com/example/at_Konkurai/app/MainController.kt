package com.example.at_Konkurai.app

import com.example.at_Konkurai.service.ItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.tags.Param

@Controller
@RequestMapping("/front")
class MainController @Autowired constructor(private val itemService: ItemService) {

    @GetMapping
    fun login(model: Model, loginForm: LoginForm): String {
        model.addAttribute("title", "ログインしてください")
        return "front/index"
    }

    @PostMapping("/main")
    fun showContents(model: Model,
                     loginForm: LoginForm,
                     redirectAttributes: RedirectAttributes
    ): String {
        val userId = itemService.findUser(loginForm.username, loginForm.password)
        if (userId == 0) {
            redirectAttributes.addFlashAttribute("reLogin", "ユーザが見つかりませんでした。ユーザネーム、パスワードを確認してください。")
            return "redirect:/front"
        } else {
            itemService.makeTodayItemInfo(userId)
            val itemList = itemService.findItemInfo(userId)

            model.addAttribute("id", userId)
            model.addAttribute("username", loginForm.username)
            model.addAttribute("list", itemList)
            return ("front/main")
        }
    }

    @PostMapping("/edit")
    fun editItem(model: Model,
                 editForm: EditForm, @RequestParam("update") update: String?): String {

        val itemList = itemService.findItemInfo(editForm.id)
        model.addAttribute("id", editForm.id)
        model.addAttribute("list", itemList)
        return ("front/edit")
    }

    @PostMapping("update")
     fun update(model: Model, editForm: EditForm): String {

         if (editForm.itemname != "") {
             itemService.updateItemInfo(editForm)
             val itemList = itemService.findItemInfo(editForm.id)
             model.addAttribute("id", editForm.id)
             model.addAttribute("list", itemList)
             model.addAttribute("updateAlert", "アイテム: ${editForm.itemname} の更新が完了しました！")
             return ("front/edit")
         }
         val itemList = itemService.findItemInfo(editForm.id)
         model.addAttribute("id", editForm.id)
         model.addAttribute("list", itemList)
         return ("front/edit")
     }

     @PostMapping("delete")
     fun delete(model: Model, editForm: EditForm): String {

         if (editForm.itemname != "") {
             itemService.deleteItemInfo(editForm)
             val itemList = itemService.findItemInfo(editForm.id)
             model.addAttribute("id", editForm.id)
             model.addAttribute("list", itemList)
             model.addAttribute("deleteAlert", "アイテム: ${editForm.itemname} を削除しました！")
             return ("front/edit")
         }
         val itemList = itemService.findItemInfo(editForm.id)
         model.addAttribute("id", editForm.id)
         model.addAttribute("list", itemList)
         return ("front/edit")
     }
}