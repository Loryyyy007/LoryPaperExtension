package me.loryyyy.loryPaperExtensions.miscellaneous

import java.io.*

fun deleteFolder(path: File): Boolean {
    if (path.exists()) {
        path.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                deleteFolder(file)
            } else {
                file.delete()
            }
        }
    }
    return path.delete()
}

fun copy(source: File, destination: File) {
    if (source.isDirectory()) {
        if (!destination.exists()) destination.mkdir()
        val files = source.list()
        if (files == null) return
        for (file in files) {
            val newSource = File(source, file)
            val newDestination = File(destination, file)
            copy(newSource, newDestination)
        }
    } else {
        val `in`: InputStream = FileInputStream(source)
        val out: OutputStream = FileOutputStream(destination)
        val buffer = ByteArray(1024)

        var length: Int
        while ((`in`.read(buffer).also { length = it }) > 0) {
            out.write(buffer, 0, length)
        }
        `in`.close()
        out.close()
    }
}