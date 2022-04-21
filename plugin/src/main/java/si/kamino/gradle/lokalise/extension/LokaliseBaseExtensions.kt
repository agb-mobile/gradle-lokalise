package si.kamino.gradle.lokalise.extension

open class LokaliseBaseExtensions {

    var `id`: String? = null
    var token: String? = null
    var indentation: String = "default"
    var exportEmptyAs: String = "skip"
    var pluralFormat:String = "generic"
    var includeComments:Boolean = true
    var replaceBreaks:Boolean = true
    var filterData : Array<String> = arrayOf("translated, reviewed")
    var filterLangs: Array<String>? = null
    var directoryPrefix: String? = null

}
