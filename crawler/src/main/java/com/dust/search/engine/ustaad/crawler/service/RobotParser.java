//package com.dust.search.engine.ustaad.crawler.service;
//
//import org.apache.tomcat.util.http.fileupload.IOUtils;
//
//import java.net.URL;
//import java.net.http.HttpResponse;
//import java.util.Scanner;
//
//public class RobotParser {
//    public static boolean isAllowed(String robotsTxtContent, String userAgent, String url) throws Exception {
//        URL urlObj = new URL(url);
//        String hostId = urlObj.getProtocol() + "://" + urlObj.getHost()
//                + (urlObj.getPort() > -1 ? ":" + urlObj.getPort() : "");
//        Map<String, BaseRobotRules> robotsTxtRules = new HashMap<String, BaseRobotRules>();
//        BaseRobotRules rules = robotsTxtRules.get(hostId);
//        if (rules == null) {
//            HttpGet httpget = new HttpGet(hostId + "/robots.txt");
//            HttpContext context = new BasicHttpContext();
//            HttpResponse response = httpclient.execute(httpget, context);
//            if (response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 404) {
//                rules = new SimpleRobotRules(RobotRulesMode.ALLOW_ALL);
//                // consume entity to deallocate connection
//                EntityUtils.consumeQuietly(response.getEntity());
//            } else {
//                BufferedHttpEntity entity = new BufferedHttpEntity(response.getEntity());
//                SimpleRobotRulesParser robotParser = new SimpleRobotRulesParser();
//                rules = robotParser.parseContent(hostId, IOUtils.toByteArray(entity.getContent()),
//                        "text/plain", USER_AGENT);
//            }
//            robotsTxtRules.put(hostId, rules);
//        }
//        boolean urlAllowed = rules.isAllowed(url);
//    }
//
//    public static void main(String[] args) throws Exception {
//        String site = "https://example.com";
//        URL robotsUrl = new URL(site + "/robots.txt");
//        Scanner scanner = new Scanner(robotsUrl.openStream());
//        while (scanner.hasNextLine()) {
//            System.out.println(scanner.nextLine());
//        }
//        scanner.close();
//    }
//}
//
