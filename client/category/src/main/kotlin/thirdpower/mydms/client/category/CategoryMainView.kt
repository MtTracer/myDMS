package thirdpower.mydms.client.category

import javafx.scene.control.TextField
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableRow
import javafx.scene.control.TreeTableView
import javafx.scene.input.ClipboardContent
import javafx.scene.input.DataFormat
import javafx.scene.input.Dragboard
import javafx.scene.input.TransferMode
import tornadofx.*


class CategoryViewModel : ItemViewModel<Category>() {
    val name = bind(Category::nameProperty)
}

class CategoryMainView : View() {
    private val controller: CategoryController by inject()
    private var treeView: TreeTableView<Category> by singleAssign()
    private var nameField: TextField by singleAssign()

    private var rootCategory: Category = controller.loadCategoryTree()
    private var selectedModel: CategoryViewModel = CategoryViewModel()

    companion object {
        private val SERIALIZED_MIME_TYPE = DataFormat("application/x-java-serialized-object")
    }

    override val root = borderpane {
        top = hbox {
            button("New Category") {
                action {
                    val parentCategory = treeView.selectionModel.selectedItem ?: treeView.root
                    newItem(parentCategory)
                }
            }
            button("Delete") {
                //enableWhen(treeView.selectionModel.selectedItemProperty().isNotNull)
                action {
                    removeItem(treeView.selectionModel.selectedItem)
                }
            }
            button("Save") {
                action {
                    saveTree()
                }
            }
        }
        center = hbox {
            treeView = treetableview {
                column("Name", Category::nameProperty)
                setRowFactory(this@CategoryMainView::dragAndDropRowFactory)

                root = TreeItem(rootCategory)
                populate { parent -> parent.value.children }

                isShowRoot = false
                root.isExpanded = true
                root.children.forEach { it.isExpanded = true }

                bindSelected(selectedModel)
            }

            form {
                fieldset {
                    nameField = textfield("Name") {
                        bind(selectedModel.name)

                        setOnKeyPressed {
                            selectedModel.commit(selectedModel.name)
                        }
                    }
                }
            }

        }
    }

    private fun saveTree() {
        treeView.isDisable = true
        runAsync {
            controller.saveCategoryTree(rootCategory)
        } ui {
            treeView.root.value = it
            treeView.isDisable = false
        }

        val newRoot = controller.saveCategoryTree(rootCategory)
        treeView.root.value = newRoot
    }

    private fun removeItem(selectedItem: TreeItem<Category>) {
        selectedItem.parent.value.children.remove(selectedItem.value)
        treeView.selectionModel.select(selectedItem.parent)
    }

    private fun newItem(parent: TreeItem<Category>) {
        val newCat = Category(name = "New Category")
        parent.value.children.add(newCat)

        parent.isExpanded = true
        treeView.selectionModel.select(parent.children.last())

        with(nameField) {
            requestFocus()
            selectAll()
        }
    }

    private fun dragAndDropRowFactory(view: TreeTableView<Category>): TreeTableRow<Category> {
        val row = TreeTableRow<Category>()
        row.setOnDragDetected { event ->
            if (!row.isEmpty) {
                val db = row.startDragAndDrop(TransferMode.MOVE)
                db.dragView = row.snapshot(null, null)
                val cc = ClipboardContent()
                cc.put(SERIALIZED_MIME_TYPE, row.index)
                db.setContent(cc)
                event.consume()
            }
        }

        row.setOnDragOver { event ->
            val db = event.dragboard
            if (acceptable(db, row)) {
                treeView.selectionModel.select(row.index)
                event.acceptTransferModes(*TransferMode.COPY_OR_MOVE)
                event.consume()
            }
        }

        row.setOnDragDropped { event ->
            val db = event.dragboard
            if (acceptable(db, row)) {
                val index = db.getContent(SERIALIZED_MIME_TYPE) as Int
                val item = view.getTreeItem(index)
                item.parent.children.remove(item)
                getTarget(row).children.add(item)
                event.isDropCompleted = true
                view.selectionModel.select(item)
                event.consume()
            }
        }

        return row
    }

    private fun acceptable(db: Dragboard, row: TreeTableRow<Category>): Boolean {
        var result = false
        if (db.hasContent(SERIALIZED_MIME_TYPE)) {
            val index = db.getContent(SERIALIZED_MIME_TYPE) as Int
            if (row.index != index) {
                val target = getTarget(row)
                val item = treeView.getTreeItem(index)
                result = !isParent(item, target)
            }
        }
        return result
    }

    private fun getTarget(row: TreeTableRow<Category>): TreeItem<Category> {
        return if (row.isEmpty) {
            treeView.root
        } else {
            row.treeItem
        }
    }

    // prevent loops in the tree
    private fun isParent(parent: TreeItem<Category>, possibleChild: TreeItem<Category>): Boolean {
        var child = possibleChild
        while (child.parent != null) {
            if (child.parent === parent) {
                return true
            }

            child = child.parent
        }
        return false
    }
}
