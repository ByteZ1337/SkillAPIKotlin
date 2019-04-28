package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import com.sucy.skill.facade.api.event.actor.DamageSource
import com.sucy.skill.util.match
import com.sucy.skill.util.text.enumName

/**
 * SkillAPIKotlin © 2018
 */
class DamageTrigger : Trigger<ActorDamagedByActorEvent>() {
    private var min = 0.0
    private var max = 1.0e10
    private var attacker = false
    private var sources = DamageSource.values().toSet()

    /** {@inheritDoc}  */
    override val key: String
        get() = "DAMAGE"

    /** {@inheritDoc}  */
    override val event: Class<ActorDamagedByActorEvent>
        get() = ActorDamagedByActorEvent::class.java

    override fun initialize() {
        min = metadata.getDouble("min", min)
        max = metadata.getDouble("max", max)
        attacker = metadata.getBoolean("attacker", attacker)
        sources = metadata.getStringList("source")
                .mapTo(HashSet()) { DamageSource::class.match(it, DamageSource.OTHER) }
                .ifEmpty { sources }
    }

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: ActorDamagedByActorEvent, level: Int): Boolean {
        return event.amount in min..max && sources.contains(event.source)
    }

    /** {@inheritDoc}  */
    override fun setValues(event: ActorDamagedByActorEvent, target: Actor) {
        target.metadata["api-taken"] = event.amount
        target.metadata["api-dealt"] = event.amount
    }

    /** {@inheritDoc}  */
    override fun getCaster(event: ActorDamagedByActorEvent): Actor? {
        return if (attacker) event.attacker else event.actor
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: ActorDamagedByActorEvent): Actor? {
        return if (attacker) event.actor else event.attacker
    }
}