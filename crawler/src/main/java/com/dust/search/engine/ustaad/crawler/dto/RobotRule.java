package com.dust.search.engine.ustaad.crawler.dto;

public class RobotRule {
    public String userAgent;
    public String rule;

    public RobotRule() {

    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append("   userAgent: " + this.userAgent + NEW_LINE);
        result.append("   rule: " + this.rule + NEW_LINE);
        result.append("}");
        return result.toString();
    }
}
