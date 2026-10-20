package dev.gillinet.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import dev.gillinet.components.GilliPlayerHomeComponent;

import javax.annotation.Nonnull;
import dev.gillinet.utils.Utility;

public class GilliHomeCommand extends AbstractPlayerCommand {

    public GilliHomeCommand(String name, String description) {
        super(name, description);
    }

    @Override
    protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store,
        @Nonnull Ref<EntityStore> ref,
        @Nonnull PlayerRef playerRef,
        @Nonnull World world
    ) { 
        GilliPlayerHomeComponent homeComp = store.getComponent(ref, GilliPlayerHomeComponent.getComponentType());

        if (homeComp == null) {
            context.sendMessage(Message.raw("Player home not set! Use /gsethome to set your home."));
            return;
        }

        if (!world.getName().equals(homeComp.getHomeWorld())) {
            context.sendMessage(Message.raw("Cannot teleport home from here!"));
            return;
        }

        Utility.teleportPlayer(ref, store, world, homeComp.getHomeX(), homeComp.getHomeY(), homeComp.getHomeZ());
    }
}