package thirdpower.mydms.client

import javafx.scene.control.TabPane
import thirdpower.mydms.category.ui.CategoryMainView
import thirdpower.mydms.document.ui.DocumentMainView
import thirdpower.mydms.type.ui.TypeMainView
import tornadofx.View
import tornadofx.tab
import tornadofx.tabpane
import tornadofx.useMaxSize

class MainView : View("My View") {

    private val categoryMainView: CategoryMainView by inject()
    private val typeMainView: TypeMainView by inject()
    private val documentMainView: DocumentMainView by inject()

    override val root = tabpane {
        useMaxSize = true
        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

        tab("Categories", categoryMainView.root)
        tab("Types", typeMainView.root)
        tab("Documents", documentMainView.root)
    }
}
