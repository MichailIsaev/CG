package cg_models

data class OBJ(var vs: MutableList<Vs> = mutableListOf(), var fs: MutableList<Fs> = mutableListOf())

data class Vs(var x: Double, var y: Double, var z: Double)

data class Fs(var x: Double, var y: Double, var z: Double)