package parser

import org.junit.Test
import java.io.File
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ParserTest {

    @Test
    fun test(){
        val source = File(this.javaClass.classLoader.getResource("parser/test.obj")?.toURI())
        var obj = Parser.parse(source)
        assertNotNull(obj)
        assertNotNull(obj.fs)
        assertNotNull(obj.vs)
        assertTrue {
            obj.fs.isNotEmpty() && obj.vs.isNotEmpty()
        }
    }
}