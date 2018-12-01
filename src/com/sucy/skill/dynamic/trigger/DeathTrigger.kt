package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ActorDeathEvent
import com.sucy.skill.util.io.Data

/**
 * SkillAPI © 2018
 */
class DeathTrigger : Trigger<ActorDeathEvent> {

    /** {@inheritDoc}  */
    override val key: String
        get() = "DEATH"

    /** {@inheritDoc}  */
    override val event: Class<ActorDeathEvent>
        get() = ActorDeathEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: ActorDeathEvent, level: Int, settings: Data): Boolean {
        return !isTargetingKiller(settings) || event.killer != null
    }

    /** {@inheritDoc}  */
    override fun setValues(event: ActorDeathEvent, data: MutableMap<String, Any>) {}

    /** {@inheritDoc}  */
    override fun getCaster(event: ActorDeathEvent): Actor {
        return event.actor
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: ActorDeathEvent, settings: Data): Actor {
        return event.actor
    }

    private fun isTargetingKiller(settings: Data): Boolean {
        return settings.getString("killer", "false").equals("true", true)
    }
}
