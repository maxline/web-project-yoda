package com.epam.yoda.config.manager;

import com.epam.yoda.config.enums.EConfigKey;

import java.util.ResourceBundle;

/**
 * @author Sergey Mikhluk.
 */
public class ConfigManager {

    private static volatile ConfigManager instance;
    private ResourceBundle resource;
    private static final String BUNDLE_NAME = "config";

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        ConfigManager localInstance = instance;
        if (localInstance == null) {
            synchronized (ConfigManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConfigManager();
                    instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
                }
            }
        }
        return localInstance;
    }

    public String getProperty(EConfigKey key) {
        return resource.getString(key.getId());
    }
}