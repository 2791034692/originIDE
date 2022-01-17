package cn.original.ide.module.dx

import dalvik.system.DexClassLoader

class ModuleDexClassLoader(
    dexPath: String?,
    optimizedDirectory: String?,
    librarySearchPath: String?,
    parent: ClassLoader?
) : DexClassLoader(dexPath, optimizedDirectory, librarySearchPath, parent) {
    override fun loadClass(name: String?): Class<*>? {
        if (name?.check("cn.original.ide") == true) {
            return null
        }
        return super.loadClass(name)
    }

    override fun loadClass(name: String?, resolve: Boolean): Class<*>? {
        if (name?.check("cn.original.ide") == true) {
            return null
        }
        return super.loadClass(name, resolve)
    }

    private fun String.check(name2: String?): Boolean {
        if (contains(name2.toString()) or equals(name2.toString())) {
            return true
        }
        return false
    }
}