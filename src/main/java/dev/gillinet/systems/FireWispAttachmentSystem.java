package dev.gillinet.systems;

import com.hypixel.hytale.component.system.RefChangeSystem;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.component.DynamicLight;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.modules.interaction.Interactions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.UUID;
import dev.gillinet.components.FireWispComponent;

public class FireWispAttachmentSystem extends RefChangeSystem<EntityStore, FireWispComponent> {
	
	private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    @Nonnull
    @Override
    public ComponentType<EntityStore, FireWispComponent> componentType() {
        return FireWispComponent.getComponentType();
    }

    @Override
    public void onComponentAdded(@Nonnull Ref<EntityStore> ref, @Nonnull FireWispComponent chargeComponent, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        UUIDComponent component = store.getComponent(ref, UUIDComponent.getComponentType());
        UUID playerUuid = component.getUuid();
        PlayerRef pRef = store.getComponent(ref, PlayerRef.getComponentType());
        Player player = store.getComponent(ref, Player.getComponentType());
        World world = player.getWorld();
        
		Holder<EntityStore> holder = store.getRegistry().newHolder();
        ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset("FireWisp");
        Model model = Model.createScaledModel(modelAsset, 1.0f);

        TransformComponent tform = store.getComponent(ref, EntityModule.get().getTransformComponentType());

        holder.addComponent(TransformComponent.getComponentType(), new TransformComponent(tform.getPosition(), tform.getRotation()));
        holder.addComponent(PersistentModel.getComponentType(), new PersistentModel(model.toReference()));
        holder.addComponent(ModelComponent.getComponentType(), new ModelComponent(model));
        holder.addComponent(BoundingBox.getComponentType(), new BoundingBox(model.getBoundingBox()));
        holder.addComponent(NetworkId.getComponentType(), new NetworkId(store.getExternalData().takeNextNetworkId()));
        holder.addComponent(Interactions.getComponentType(), new Interactions());
		holder.addComponent(DynamicLight.getComponentType(), new DynamicLight());
    }

    @Override
    public void onComponentSet(@Nonnull Ref<EntityStore> ref, @Nullable FireWispComponent oldAttachment, @Nonnull FireWispComponent newAttachment, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        UUIDComponent component = store.getComponent(ref, UUIDComponent.getComponentType());
        UUID playerUuid = component.getUuid();

        // Depending on chargetype buff player
        
    }

    @Override
    public void onComponentRemoved(@Nonnull Ref<EntityStore> ref, @Nonnull FireWispComponent fireWispComponent, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        UUIDComponent component = store.getComponent(ref, UUIDComponent.getComponentType());
        UUID playerUuid = component.getUuid();
        PlayerRef pRef = store.getComponent(ref, PlayerRef.getComponentType());

        
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(componentType());
    }
}
