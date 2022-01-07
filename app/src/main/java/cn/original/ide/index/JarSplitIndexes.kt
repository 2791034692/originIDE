package cn.original.ide.index

import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile

class JarSplitIndexes {
    val enumeration: Enumeration<JarEntry>;

    constructor(jarFileString: String) {
        var jarFile: JarFile = JarFile(jarFileString)
        enumeration = jarFile.entries();
    }

    open fun size(): Int {
        return enumeration.toList().size
    }

    open fun split(): Array<String>? {
        var array: Array<String> = arrayOf("")
        while (enumeration.hasMoreElements()) {
            var jarEntry: JarEntry = enumeration.nextElement();
            if (jarEntry.isDirectory) continue
            if (jarEntry.name.equals("class")) {
                array[array.size] = jarEntry.name.replace("/", ".")
            }
        }
        return array;
    }
}