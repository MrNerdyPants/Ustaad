package com.dust.search.engine.ustaad.crawler.util;

import com.dust.search.engine.ustaad.crawler.dto.RobotRule;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class RobotCache {
    private static ConcurrentHashMap<String, ArrayList<RobotRule>> robotCache = new ConcurrentHashMap<>();


    public static ArrayList<RobotRule> getRobot(String host) {
        if (containsRobot(host)) {
            return robotCache.get(host);
        }
        return null;
    }

    public static Boolean containsRobot(String host) {
        return robotCache.containsKey(host);
    }

    public static void removeRobot(String host) {
        robotCache.remove(host);
    }

    public static ArrayList<RobotRule> addRobot(String host, ArrayList<RobotRule> robotRules) {
        return robotCache.putIfAbsent(host, robotRules);
    }


}
