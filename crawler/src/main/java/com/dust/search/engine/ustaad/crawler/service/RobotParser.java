package com.dust.search.engine.ustaad.crawler.service;

import com.dust.search.engine.ustaad.crawler.dto.RobotRule;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.dust.search.engine.ustaad.crawler.util.RobotCache.addRobot;
import static com.dust.search.engine.ustaad.crawler.util.RobotCache.getRobot;

public class RobotParser {


    public static boolean robotSafe(URL url) {
        String strHost = url.getHost();

        ArrayList<RobotRule> robotRules = getRobot(strHost);
        Boolean containsDisallow = false;

        if (robotRules == null) {
            String strRobot = "https://" + strHost + "/robots.txt";
            URL urlRobot;
            try {
                urlRobot = new URL(strRobot);
            } catch (MalformedURLException e) {
                // something weird is happening, so don't trust it
                return false;
            }

            String strCommands;
            try {
                InputStream urlRobotStream = urlRobot.openStream();
                byte b[] = new byte[1000];
                int numRead = urlRobotStream.read(b);
                strCommands = new String(b, 0, numRead);
                while (numRead != -1) {
                    numRead = urlRobotStream.read(b);
                    if (numRead != -1) {
                        String newCommands = new String(b, 0, numRead);
                        strCommands += newCommands;
                    }
                }
                urlRobotStream.close();
            } catch (IOException e) {
                return true; // if there is no robots.txt file, it is OK to search
            }

            if (StringUtils.containsAnyIgnoreCase(strCommands, "DISALLOW")) // if there are no "disallow" values, then they are not blocking anything.
            {
                String[] split = strCommands.split("\n");
                robotRules = new ArrayList<>();
                String mostRecentUserAgent = null;
                for (int i = 0; i < split.length; i++) {
                    String line = split[i].trim();
                    if (line.toLowerCase().startsWith("user-agent")) {
                        int start = line.indexOf(":") + 1;
                        int end = line.length();
                        mostRecentUserAgent = line.substring(start, end).trim();
                    } else if (line.startsWith("DISALLOW")) {
                        if (mostRecentUserAgent != null) {
                            RobotRule r = new RobotRule();
                            r.userAgent = mostRecentUserAgent;
                            int start = line.indexOf(":") + 1;
                            int end = line.length();
                            r.rule = line.substring(start, end).trim();
                            robotRules.add(r);
                        }
                    }
                }

                addRobot(strHost, robotRules);
                containsDisallow = true;
            }
        }
        if (containsDisallow) // if there are no "disallow" values, then they are not blocking anything.
        {
            for (RobotRule robotRule : robotRules) {
                String path = url.getPath();
                if (robotRule.rule.length() == 0) return true; // allows everything if BLANK
                if (robotRule.rule == "/") return false;       // allows nothing if /

                if (robotRule.rule.length() <= path.length()) {
                    String pathCompare = path.substring(0, robotRule.rule.length());
                    if (pathCompare.equals(robotRule.rule)) return false;
                }
            }
        }
        return true;
    }
}

