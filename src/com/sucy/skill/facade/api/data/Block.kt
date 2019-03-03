package com.sucy.skill.facade.api.data

/**
 * SkillAPIKotlin © 2018
 */
interface Block {
    val type: String
    val location: Location
    val isSolid: Boolean
    val isAir: Boolean

    val state: BlockState
}