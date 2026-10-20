package dev.gillinet;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;

import dev.gillinet.commands.*;
import dev.gillinet.components.FireWispComponent;
import dev.gillinet.components.GilliPlayerHomeComponent;
import dev.gillinet.config.ExampleConfig;
import dev.gillinet.events.ExampleEvent;

import dev.gillinet.systems.FireWispAttachmentSystem;
import dev.gillinet.systems.FireWispSystem;

import javax.annotation.Nonnull;

public class Evermight extends JavaPlugin {

    private static Config<ExampleConfig> config = null;

    private ComponentType<EntityStore, GilliPlayerHomeComponent> gilliPlayerHomeComponent;

    public Evermight(@Nonnull JavaPluginInit init) {
        super(init);
        config = this.withConfig("example_config", ExampleConfig.CODEC);
    }

    @Override
    protected void setup() {
        config.save();
        FireWispComponent.setComponentType(
                this.getEntityStoreRegistry().registerComponent(FireWispComponent.class, "FireWisp", FireWispComponent.CODEC));
        GilliPlayerHomeComponent.setComponentType(
            this.getEntityStoreRegistry().registerComponent(GilliPlayerHomeComponent.class, "GilliPlayerHome", GilliPlayerHomeComponent.CODEC));
        this.getCommandRegistry().registerCommand(new FireWispCommand("firewisp", "Spawns a fire wisp for debugging"));
        this.getCommandRegistry().registerCommand(new GilliHomeCommand("ghome", "Teleports player back to set home location."));
        this.getCommandRegistry().registerCommand(new GilliSetHomeCommand("gsethome", "Sets the player home at the current coordinates of the player."));

        this.gilliPlayerHomeComponent = this.getEntityStoreRegistry().registerComponent(
            GilliPlayerHomeComponent.class,
            "GilliPlayerHomeComponent",
            GilliPlayerHomeComponent.CODEC
        );

        this.getEntityStoreRegistry().registerSystem(new FireWispSystem(FireWispComponent.getComponentType()));
        this.getEntityStoreRegistry().registerSystem(new FireWispAttachmentSystem());

        if (getConfig().get().isEnabledWelcomeMessage()) {
            this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ExampleEvent::onPlayerReady);
        }
    }

    public static Config<ExampleConfig> getConfig() {
        return config;
    }

    public ComponentType<EntityStore, GilliPlayerHomeComponent> getGilliPlayerHomeComponent() {
        return gilliPlayerHomeComponent;
    }
}