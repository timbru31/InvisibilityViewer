package com.cyprias.invisibilityviewer;

import org.bukkit.configuration.Configuration;

public class Config {
    private Configuration config;
    public boolean checkNewVersionOnStartup, togglePlayerByDefault, toggleOtherByDefault, distanceEnabled, debugMessages, debugNoIntercept;
    public int distanceRadius;
    public long distanceFrequency;
    private InvisibilityViewer plugin;

    public Config(InvisibilityViewer plugin) {
	this.plugin = plugin;
	config = plugin.getConfig();
	config.options().copyDefaults(true);
	plugin.saveConfig();
    }

    public void reloadOurConfig() {
	plugin.reloadConfig();
	config = plugin.getConfig();
	loadConfigOpts();
    }

    private void loadConfigOpts(){
	checkNewVersionOnStartup = config.getBoolean("checkNewVersionOnStartup");
	togglePlayerByDefault = config.getBoolean("toggledByDefault.player");
	toggleOtherByDefault = config.getBoolean("toggledByDefault.other");

	distanceEnabled = config.getBoolean("distance.enabled");
	distanceRadius = config.getInt("distance.radius");
	distanceFrequency = config.getLong("distance.frequency");

	debugMessages = config.getBoolean("debugMessages");
	debugNoIntercept = config.getBoolean("debugNoIntercept");
    }
}