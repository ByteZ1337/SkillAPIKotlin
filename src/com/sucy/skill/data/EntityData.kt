package com.sucy.skill.data

import com.sucy.skill.api.attribute.AttributeSet
import com.sucy.skill.api.attribute.FlagSet
import com.sucy.skill.api.player.PlayerAccounts
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
class EntityData {
    val attributes = HashMap<UUID, AttributeSet>()
    val flags = HashMap<UUID, FlagSet>()
    val accounts = HashMap<UUID, PlayerAccounts>()
}