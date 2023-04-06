package com.itoys.logcat

import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 01/04/2023
 * @desc utils about logcat.
 */
object LogcatUtils {

    private val SDF_THREAD_LOCAL: ThreadLocal<HashMap<String, SimpleDateFormat>> =
        object : ThreadLocal<HashMap<String, SimpleDateFormat>>() {
            override fun initialValue(): HashMap<String, SimpleDateFormat>? {
                return hashMapOf()
            }
        }

    private fun getSafeDateFormat(pattern: String): SimpleDateFormat {
        val safeDateFormatMap = SDF_THREAD_LOCAL.get()
        var simpleDateFormat = safeDateFormatMap?.get(pattern)
        if (simpleDateFormat == null) {
            simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            safeDateFormatMap[pattern] = simpleDateFormat
        }
        return simpleDateFormat
    }

    private val defaultFormat by lazy { getSafeDateFormat("yyyy-MM-dd HH:mm:ss") }

    private val fileNameFormat by lazy { getSafeDateFormat("yyyy/MM/dd") }

    private val SEP = File.separatorChar

    fun nowTime(): String {
        return defaultFormat.format(System.currentTimeMillis())
    }

    fun todayLogFile(): String {
        val today = fileNameFormat.format(System.currentTimeMillis())
        val todayDir = getInternalAppCachePath() + SEP + today
        createOrExistsDirs(todayDir)
        val log = todayDir + "${SEP}log.log"
        createOrExistsFile(log)
        return log
    }

    private fun createOrExistsDirs(dirPath: String): Boolean {
        return createOrExistsDirs(getFileByPath(dirPath))
    }

    private fun createOrExistsDirs(dirFile: File?): Boolean {
        if (dirFile == null) return false
        if (dirFile.exists()) return dirFile.isDirectory
        return dirFile.mkdirs()
    }

    private fun createOrExistsFile(filePath: String): Boolean {
        return createOrExistsFile(getFileByPath(filePath))
    }

    private fun createOrExistsFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists()) return file.isFile
        return file.createNewFile()
    }

    /**
     * Return the file by path.
     */
    private fun getFileByPath(filePath: String): File? {
        return File(filePath)
    }

    /**
     * Return the path of /data/data/package/cache.
     */
    private fun getInternalAppCachePath(): String {
        return getAbsolutePath(LogcatInitialization.requireApp().cacheDir)
    }

    private fun getAbsolutePath(file: File?): String {
        return file?.absolutePath ?: ""
    }
}