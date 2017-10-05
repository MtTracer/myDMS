package thirdpower.mydms.client.rest

import tornadofx.Rest

class MyDmsRestApi : Rest() {

    init {
        baseURI = "http://localhost:8080/rest"
    }
}