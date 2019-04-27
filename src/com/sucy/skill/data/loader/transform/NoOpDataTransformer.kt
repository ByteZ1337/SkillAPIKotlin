package com.sucy.skill.data.loader.transform

import com.sucy.skill.util.io.Data

/**
 * Transformer that returns the data as-is, used for latest configurations matching the code
 */
class NoOpDataTransformer : DataTransformer {
    override fun transform(data: Data): Data {
        return data
    }
}