package com.sucy.skill.data.loader

import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.data.loader.transform.NoOpDataTransformer
import com.sucy.skill.util.io.Config
import com.sucy.skill.util.io.Data

interface DataLoader<T> {
    val requiredKeys: Array<String>
    val transformers: Map<Int, DataTransformer>
    val latestVersion: Int
        get() = 2

    fun transformAndLoad(key: String, data: Data): T {
        val version = data.getVersion()
        return transformAndLoad(key, data, version)
    }

    fun transformAndLoad(key: String, data: Data, version: Int): T {
        val transformer = transformers.getOrDefault(version, NoOpDataTransformer)
        val config = transformer.transform(key, data)

        requiredKeys.forEach {
            if (!config.has(it)) {
                throw InvalidDataException("Key \"$it\" is required")
            }
        }

        return load(key, config)
    }

    fun load(key: String, data: Data) : T

    fun writeTo(config: Config, data: T) {
        val serialized = serialize(data)
        serialized.setVersion(latestVersion)
        config.data = serialized
        config.save()
    }

    fun serialize(data: T): Data

    fun getKey(data: T): String? = null
}