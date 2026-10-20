package dev.gillinet.components;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nullable;

public class FireWispComponent implements Component<EntityStore> {

    private float tickInterval;
    private int remainingTicks;
    private float elapsedTime;

	private static ComponentType<EntityStore, FireWispComponent> type;
	
	public static final BuilderCodec<FireWispComponent> CODEC = BuilderCodec
            .builder(FireWispComponent.class, FireWispComponent::new)
            .append(
                    new KeyedCodec<>("TickInterval", Codec.FLOAT),
                    (component, value) -> component.tickInterval = value,
                    component -> component.tickInterval
            ).add()
            .append(
                    new KeyedCodec<>("RemainingTicks", Codec.INTEGER),
                    (component, value) -> component.remainingTicks = value,
                    component -> component.remainingTicks
            )
            .add()
            .append(
                    new KeyedCodec<>("ElapsedTime", Codec.FLOAT),
                    (component, value) -> component.elapsedTime = value,
                    component -> component.elapsedTime
            )
            .add()
            .build();

	public FireWispComponent() {
        this(1.0f, 10);
    }

    public FireWispComponent(float tickInterval, int totalTicks) {
        this.tickInterval = tickInterval;
        this.remainingTicks = totalTicks;
        this.elapsedTime = 0f;
    }

    public FireWispComponent(FireWispComponent other) {
        this.tickInterval = other.tickInterval;
        this.remainingTicks = other.remainingTicks;
        this.elapsedTime = other.elapsedTime;
    }

	public static ComponentType<EntityStore, FireWispComponent> getComponentType() {
        return type;
    }

    public static void setComponentType(ComponentType<EntityStore, FireWispComponent> type) {
        FireWispComponent.type = type;
    }

	@Nullable
    @Override
    public Component<EntityStore> clone() {
        return new FireWispComponent(this);
    }
	
	public float getTickInterval() {
        return tickInterval;
    }

    public int getRemainingTicks() {
        return remainingTicks;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void addElapsedTime(float dt) {
        this.elapsedTime += dt;
    }

    public void resetElapsedTime() {
        this.elapsedTime = 0f;
    }

    public void decrementRemainingTicks() {
        this.remainingTicks--;
    }

    public boolean isExpired() {
        return this.remainingTicks <= 0;
    }
}
