package com.itoys.network.json

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 18/03/2023
 * @desc
 */
object JsonMapper {

    private val mapper: JsonMapper by lazy {
        jsonMapper {
            addModule(kotlinModule())
            // 对象的所有字段全部列入
            serializationInclusion(JsonInclude.Include.ALWAYS)
            // 取消默认转换timestamps形式
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            // 忽略空Bean转json的错误
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            // 忽略在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            // 所有的日期格式都统一为yyyy-MM-dd HH:mm:ss
            defaultDateFormat(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()))
        }
    }

    /**
     * obj to json string.
     */
    fun objToString(obj: Any?): String {
        if (obj == null) return ""

        if (obj is String) return obj

        return try {
            mapper.writeValueAsString(obj)
        } catch (e: JsonProcessingException) {
            ""
        }
    }

    fun <T> stringToObj(jsonString: String, objClass: Class<T>): T? {
        return try {
            mapper.readValue(jsonString, objClass)
        } catch (e: JacksonException) {
            null
        }
    }

    fun <T> stringToList(jsonString: String, objClass: Class<T>): List<T>? {
        return try {
            mapper.readValue(
                jsonString,
                mapper.typeFactory.constructCollectionType(List::class.java, objClass)
            )
        } catch (e: JacksonException) {
            null
        }
    }

    fun <K, V> stringToMap(
        jsonString: String,
        keyClass: Class<K>,
        valueClass: Class<V>
    ): Map<K, V>? {
        return try {
            mapper.readValue(
                jsonString,
                mapper.typeFactory.constructMapType(Map::class.java, keyClass, valueClass)
            )
        } catch (e: JacksonException) {
            null
        }
    }
}