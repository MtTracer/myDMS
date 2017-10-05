package thirdpower.mydms.client.app

import tornadofx.App
import tornadofx.launch

class MyDmsApp : App(MainView::class)

fun main(args: Array<String>) = launch<MyDmsApp>(args)

