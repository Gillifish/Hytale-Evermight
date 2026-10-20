package dev.gillinet.utils;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class Utility {
	public static void teleportPlayer(Ref<EntityStore> ref, 
		Store<EntityStore> store, 
		World targetWorld, 
		double x, 
		double y, 
		double z
	) {
		Transform transform = new Transform(x, y, z);
		Teleport teleport = Teleport.createForPlayer(targetWorld, transform);
		store.addComponent(ref, Teleport.getComponentType(), teleport);
	}
}
