package parser

import cg_models.Fs
import cg_models.OBJ
import cg_models.Vs
import cg_models.Vt
import java.io.File
import java.io.FileNotFoundException

class Parser {

    companion object {

        private var root = OBJ()

        @Throws(FileNotFoundException::class)
        fun parse(file: File): OBJ {

            if (!file.exists()) throw FileNotFoundException()

            file.forEachLine {
                var params = it.split(" ")
                var key = params.first()
                params = params.subList(params.indexOf(key) + 1, params.size)
                mapToModel(key, params)
            }

            return root
        }

        private fun mapToModel(key: String, params: List<String>) {
            when (key) {
                "v" -> {
                    val (x, y, z) = params.map { it -> it.toDouble() }.toCollection(mutableListOf())
                    root.vs.add(Vs(x, y, z))
                }
                "f" -> {
                    val (x, y, z) = params.map{ it -> it.split("/")}
                                          .map { it.first() }
                                          .map { it -> it.toDouble() }
                                          .toCollection(mutableListOf())

                    val (xx, yy, zz) = params.map{ it -> it.split("/")}
                            .map { it[1] }
                            .map { it -> it.toDouble() }
                            .toCollection(mutableListOf())
                    root.fs.add(Fs(x, y, z , Vt(xx , yy, zz)))



                }
                "vt" -> {
                    val (xx , yy , zz) = params.drop(1)
                            .map { it -> it.toDouble() }
                            .toCollection(mutableListOf())
                    root.vt.add(Vt(xx , yy , zz))
                }
                else -> null
            }
        }

    }
}
