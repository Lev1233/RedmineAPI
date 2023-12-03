package com.example.redmineapi.network


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Issue (
    @SerializedName("id"              ) var id             : Int                     ,
    @SerializedName("project"         ) var project        : Project?                = Project(),
    @SerializedName("tracker"         ) var tracker        : Tracker?                = Tracker(),
    @SerializedName("status"          ) var status         : Status?                 = Status(),
    @SerializedName("priority"        ) var priority       : Priority?               = Priority(),
    @SerializedName("author"          ) var author         : Author?                 = Author(),
//    @SerializedName("assigned_to"     )var  assignee        :Assignee?                = Assignee(),
    @SerializedName("fixed_version"     )var  version        :Version?                = Version(),
    @SerializedName("subject"         ) var subject        : String?                 = null,
    @SerializedName("description"     ) var description    : String?                 = null,
    @SerializedName("start_date"      ) var startDate      : String?                 = null,
//    @SerializedName("done_ratio"      ) var doneRatio      : Int?                    = null,
    @SerializedName("estimated_hours" ) val estimatedHours : Double?                 = null,


)

@Serializable////Статус
data class Status (
    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null

)

data class Version(
    @SerializedName ("id")  var id: Int?  = null,
    @SerializedName("name") var name: String? = null
)

@Serializable////Пріорітет
data class Priority (
    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null

)
@Serializable////Пріорітет
data class Project (
    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null

)

@Serializable
data class Author (

    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null

)
//@Serializable
//data class CustomFields (
//
//    @SerializedName("id"    ) var id    : Int?    = null,
//    @SerializedName("name"  ) var name  : String? = null,
//    @SerializedName("value" ) var value: Any? = null,
//    @SerializedName ("multiple")  var multiple: Boolean
//
//
//)
@Serializable////Трекер
data class Tracker (

    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null

)
//
//data class Assignee(
// @SerializedName ("id")  var id: Int?  = null,
// @SerializedName("name") var name: String? = null
//)

