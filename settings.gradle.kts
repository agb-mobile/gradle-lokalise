include(":plugin")
include(":example")

val projectName: String by settings
project(":plugin").name = projectName
