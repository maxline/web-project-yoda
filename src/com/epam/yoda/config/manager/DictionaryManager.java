package com.epam.yoda.config.manager;

import com.epam.yoda.config.enums.EDictionaryKey;
import com.epam.yoda.config.enums.ELocale;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Sergey Mikhluk.
 */
public class DictionaryManager {

    private static volatile DictionaryManager instance;
    private ResourceBundle resource;
    private static final String DICTIONARY_BUNDLE = "dictionary";
    private static final int INITIAL_CAPACITY = 20;
    private Map<String, String> bundleData;

    private DictionaryManager() {
    }

    public static DictionaryManager getInstance() {
        DictionaryManager localInstance = instance;
        if (localInstance == null) {
            synchronized (DictionaryManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DictionaryManager();
                    instance.resource = ResourceBundle.getBundle(DICTIONARY_BUNDLE, Locale.getDefault());
                }
            }
        }
        return localInstance;
    }

    private void fillBundleData() {
        bundleData = new HashMap<>(INITIAL_CAPACITY);
        for (EDictionaryKey key : EDictionaryKey.values()) {
            bundleData.put(key.name(), getProperty(key));
        }
    }

    public String getProperty(EDictionaryKey key) {
        return resource.getString(key.name());
    }

    public Map<String, String> getBundleData(ELocale locale) {
        switch (locale) {
            case RU:
                resource = ResourceBundle.getBundle(DICTIONARY_BUNDLE, new Locale("ru", "RU"));
                break;
            default:
                resource = ResourceBundle.getBundle(DICTIONARY_BUNDLE, new Locale("en", "US"));
                break;
        }
        fillBundleData();
        return bundleData;
    }
}