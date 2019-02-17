package parser

import cg_models.Fs
import cg_models.OBJ
import cg_models.Vs
import java.io.File
import java.io.FileNotFoundException

class Parser {

    companion object {

        private var root = OBJ()

        @Throws(FileNotFoundException::class)
        fun parse(file: File): OBJ {

            if (!file.exists()) throw FileNotFoundException()

            file.forEachLine {
                val params = it.split(" ")
                mapToModel(params)
            }

            return root
        }

        private fun mapToModel(params: List<String>) {
            val key = params[0]
            when (key) {
                "v" -> root.vs.add(Vs((params[1]).toDouble(), params[2].toDouble(), params[3].toDouble()))
                "f" -> {
                    root.fs.add(
                        Fs(
                            params[1].split("/")[0].toDouble(),
                            params[2].split("/")[0].toDouble(),
                            params[3].split("/")[0].toDouble()
                        )
                    )
                }
                else -> null
            }
        }

    }
}
