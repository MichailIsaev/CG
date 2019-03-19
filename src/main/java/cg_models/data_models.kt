package cg_models

data class OBJ(var vs: MutableList<Vs> = mutableListOf(), var fs: MutableList<Fs> = mutableListOf() , var vt: MutableList<Vt> = mutableListOf())

data class Vs(var x: Double, var y: Double, var z: Double)

data class Fs(var x: Double, var y: Double, var z: Double , var vt: Vt)

data class Vt(var x: Double , var y: Double , var z: Double)

data class Point(var x: Double, var y: Double, var z: Double){

    fun scMul(p: Point): Double{
        return this.x * p.x + this.y * p.y + this.z * p.z
    }

}

data class Polygon(var p1: Point, var p2: Point, var p3: Point) {

    fun normal(): Point {
        /*return listOf(
            /*p1.*/ p3.y - p1.y  /*p2.*/,
            /*p1.x * /*p2.*/z - /*p1.*/z * p2.x*/0.0,
             - (p3.y  - p1.y) *  (p2.x - p1.x) + (p3.x - p1.x) * (p2.y - p1.y)
        )*/

        val p: Point = Point(
                ((p3.y - p1.y)*(p2.z - p1.z) - ((p3.z - p1.z)*(p2.y - p1.y))) ,
                (p3.z - p1.z)*(p2.x - p1.x) - (p3.x - p1.x)*(p2.z - p1.z) ,
                (p3.x - p1.x)*(p2.y - p1.y) - (p3.y - p1.y)*(p2.x - p1.x)
        )
        val length = Math.sqrt(Math.pow(p.x , 2.0) + Math.pow(p.y , 2.0) + Math.pow(p.z , 2.0))
        p.x /= length
        p.y /= length
        p.z /= length
        return p

        //val max = normal.map(::abs).max()

        //return normal.map { e -> e / max !!}
    }


    fun max_X(): Int {
        return Math.max(Math.max(p1.x, p2.x), p3.x).toInt()
    }

    fun max_Y(): Int {
        return Math.max(Math.max(p1.y, p2.y), p3.y).toInt()
    }

    fun min_X(): Int {
        return Math.min(Math.min(p1.x, p2.x), p3.x).toInt()
    }

    fun min_Y(): Int {
        return Math.min(Math.min(p1.y, p2.y), p3.y).toInt()
    }
}