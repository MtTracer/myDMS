package thirdpower.mydms.client.category

import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import tornadofx.*
import javax.json.JsonObject

class Category(var id: Long? = null, name: String? = null, var children: ObservableList<Category> = mutableListOf<Category>().observable()) : JsonModel {
    val nameProperty = SimpleStringProperty(this, "Name", name)
    var name by nameProperty

    override fun toJSON(json: JsonBuilder) {
        json.add("id", id)
                .add("name", name)
                .add("children", children).toString()
    }

    override fun updateModel(json: JsonObject) {
        json.jsonArray()
        with(json) {
            id = long("id")
            name = string("name")
            children = jsonArray("children")?.toModel() ?: mutableListOf<Category>().observable()
        }

    }

    override fun toString(): String {
        return StringBuilder("Category(").append("name=").append(name).append(")").toString()
    }
}