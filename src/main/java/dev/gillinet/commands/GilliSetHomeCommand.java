package dev.gillinet.commands;

import com.hypixel.hytale.builtin.instances.InstancesPlugin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import dev.gillinet.components.GilliPlayerHomeComponent;

import com.hypixel.hytale.server.core.command.system.CommandContext;

import javax.annotation.Nonnull;

import org.joml.Vector3d;

public class GilliSetHomeCommand extends AbstractPlayerCommand {

    public GilliSetHomeCommand(String name, String description) {
        super(name, description);
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
        @Nonnull Ref<EntityStore> ref,
        @Nonnull PlayerRef playerRef,
        @Nonnull World world
    ) {
        if (world.getName().startsWith(InstancesPlugin.INSTANCE_PREFIX)) {
            context.sendMessage(Message.raw("You cannot set a home here."));
            return;
        }

        TransformComponent transform = store.getComponent(ref, EntityModule.get().getTransformComponentType());
        Vector3d playerPos = transform.getPosition();

        // Checks if player has the player home component
        if (store.getComponent(ref, GilliPlayerHomeComponent.getComponentType()) != null) {
            var currentPlayerHomeComp = store.getComponent(ref, GilliPlayerHomeComponent.getComponentType());
            currentPlayerHomeComp.setHomePos(playerPos);
            store.replaceComponent(ref, GilliPlayerHomeComponent.getComponentType(), currentPlayerHomeComp);

        } else {
            var playerHomeComp = new GilliPlayerHomeComponent();
            playerHomeComp.setHomeWorld(world.getName());
            playerHomeComp.setHomeX(playerPos.x());
            playerHomeComp.setHomeY(playerPos.y());
            playerHomeComp.setHomeZ(playerPos.z());
            store.putComponent(ref, GilliPlayerHomeComponent.getComponentType(), playerHomeComp);
            context.sendMessage(Message.raw("Home set at current position."));
        }
    }

}