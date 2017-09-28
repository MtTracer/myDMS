package thirdpower.mydms.category.ui

import javafx.scene.control.TreeItem
import tornadofx.*


class CategoryMainView : View() {
    override val root = borderpane {
        top = label("Categories")
        center = treeview<String> {
            root = TreeItem("Root")

            populate { parent ->
                if (parent == root) {
                    listOf("Child1", "Child2")
                } else {
                    null
                }
            }
            isShowRoot = true
        }
    }
}
