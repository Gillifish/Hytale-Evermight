package dev.gillinet.systems;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.SystemGroup;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joml.Vector3d;
import org.joml.Vector3dc;

import dev.gillinet.components.FireWispComponent;

public class FireWispSystem extends EntityTickingSystem<EntityStore> {
	
	private final ComponentType<EntityStore, FireWispComponent> fireWispComponentType;

	public FireWispSystem(ComponentType<EntityStore, FireWispComponent> fireWispComponentType) {
        this.fireWispComponentType = fireWispComponentType;
    }

	@Override
    public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
                     @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {

		FireWispComponent fireWisp = archetypeChunk.getComponent(index, fireWispComponentType);
		Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);

        fireWisp.addElapsedTime(dt);

        if (fireWisp.getElapsedTime() >= fireWisp.getTickInterval()) {
            fireWisp.resetElapsedTime();

            fireWisp.decrementRemainingTicks();
        }

        if (fireWisp.isExpired()) {
            commandBuffer.removeComponent(ref, fireWispComponentType);
        }
    }

	@Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(this.fireWispComponentType);
    }
}
