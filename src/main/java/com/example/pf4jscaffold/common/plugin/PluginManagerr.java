package com.example.pf4jscaffold.common.plugin;

import org.pf4j.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PluginManagerr {

    @Autowired
    private PluginManager pluginManager;

    public PluginManager getPluginManager() {
        return pluginManager;
    }
}