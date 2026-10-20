package dev.gillinet.components;

import javax.annotation.Nonnull;

import org.joml.Vector3d;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class GilliPlayerHomeComponent implements Component<EntityStore> {
	private static ComponentType<EntityStore, GilliPlayerHomeComponent> type;
	
	private String home_world;
	private double home_x;
	private double home_y;
	private double home_z;

	public static final BuilderCodec<GilliPlayerHomeComponent> CODEC = 
		BuilderCodec.builder(GilliPlayerHomeComponent.class, GilliPlayerHomeComponent::new)
		.append(new KeyedCodec<>("Home_World", Codec.STRING),
			(data, value) -> data.home_world = value,
			data -> data.home_world)
		.add()
		.append(new KeyedCodec<>("Home_x", Codec.DOUBLE),
			(data, value) -> data.home_x = value,
			data -> data.home_x)
		.addValidator(Validators.nonNull())
		.add()
		.append(new KeyedCodec<>("Home_y", Codec.DOUBLE),
			(data, value) -> data.home_y = value,
			data -> data.home_y)
		.addValidator(Validators.nonNull())
		.add()
		.append(new KeyedCodec<>("Home_z", Codec.DOUBLE),
			(data, value) -> data.home_z = value,
			data -> data.home_z)
		.addValidator(Validators.nonNull())
		.add()
		.build();

	public GilliPlayerHomeComponent() {
		this.home_world = "";
		this.home_x = 0;
		this.home_y = 0;
		this.home_z = 0;
	}

	public GilliPlayerHomeComponent(GilliPlayerHomeComponent clone) {
		this.home_world = clone.home_world;
		this.home_x = clone.home_x;
		this.home_y = clone.home_y;
		this.home_z = clone.home_z;
	}

	public String getHomeWorld() {
		return this.home_world;
	}

	public double getHomeX() {
		return this.home_x;
	}

	public double getHomeY() {
		return this.home_y;
	}

	public double getHomeZ() {
		return this.home_z;
	}

	public void setHomeWorld(String world) {
		this.home_world = world;
	}

	public void setHomeX(double x) {
		this.home_x = x;
	}

	public void setHomeY(double y) {
		this.home_y = y;
	}

	public void setHomeZ(double z) {
		this.home_z = z;
	}

	public void setHomePos(double x, double y, double z) {
		this.home_x = x;
		this.home_y = y;
		this.home_z = z;
	}

	public void setHomePos(Vector3d vecPos) {
		this.home_x = vecPos.x;
		this.home_y = vecPos.y;
		this.home_z = vecPos.z;
	}

	@Nonnull 
	@Override 
	public Component<EntityStore> clone() {
		return new GilliPlayerHomeComponent(this);
	}

	public static ComponentType<EntityStore, GilliPlayerHomeComponent> getComponentType() {
		return type;
	}

	public static void setComponentType(ComponentType<EntityStore, GilliPlayerHomeComponent> type) {
        GilliPlayerHomeComponent.type = type;
    }
}
