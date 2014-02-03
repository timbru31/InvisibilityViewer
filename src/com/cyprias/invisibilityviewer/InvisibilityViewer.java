package com.cyprias.invisibilityviewer;

import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

public class InvisibilityViewer extends JavaPlugin {
    private ArrayList<String> optOutList = new ArrayList<String>();
    private ProtocolManager protocolManager;
    private Commands commands;
    private PacketAdapter pAdapter;

    public void onEnable() {
	commands = new Commands(this);
	getCommand("iv").setExecutor(commands);

	protocolManager = ProtocolLibrary.getProtocolManager();
	addPacketListener();
    }

    public void onDisable() {
	protocolManager.removePacketListeners(this);
	optOutList.clear();
    }

    public boolean toggleOptOutOfList(String name) {
	if (optOutList.contains(name)) {
	    optOutList.remove(name);
	    return false;
	} else {
	    optOutList.add(name);
	    return true;
	}
    }

    private void addPacketListener() {
	pAdapter = new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA) {
	    @Override
	    public void onPacketSending(PacketEvent event) {
		// Clone the packet first
		event.setPacket(event.getPacket().deepClone());

		WrappedDataWatcher watcher = new WrappedDataWatcher(event.getPacket().getWatchableCollectionModifier().read(0));
		Byte flags = watcher.getByte(0);

		if (flags != null && flags == 0x20) {
		    if (event.getPlayer().hasPermission("iv.view") && !optOutList.contains(event.getPlayer().getName())) {
			// Remove invisible flag
			watcher.setObject(0, (byte) (flags - 0x20), true);

			event.getPacket().getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
		    }
		}
	    }
	};
	protocolManager.addPacketListener(pAdapter);
    }
}