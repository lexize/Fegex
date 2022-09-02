package org.lexize.fegex;

import org.moon.figura.lua.LuaWhitelist;
import org.moon.figura.lua.docs.LuaMethodDoc;
import org.moon.figura.lua.docs.LuaTypeDoc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

@LuaWhitelist
public class FegexMatch {

    private Integer _start;
    private Integer _end;
    private String _content;
    private Map<Integer, FegexGroup> _groups;

    private FegexMatch(Matcher parent) {
        _start = parent.start();
        _end = parent.end();
        _content = parent.group();
        HashMap<Integer, FegexGroup> groupTable = new HashMap<>();
        for (FegexGroup group:
                FegexGroup.RegexGroupsFromMatcher(parent)) {
            groupTable.put(groupTable.size(), group);
        }
        _groups = groupTable;
    }

    public FegexMatch(MatchResult result) {
        _start = result.start();
        _end = result.end();
        _content = result.group();
        HashMap<Integer, FegexGroup> groupTable = new HashMap<>();
        for (FegexGroup group:
                FegexGroup.RegexGroupsFromMatchResult(result)) {
            groupTable.put(groupTable.size(), group);
        }
        _groups = groupTable;
    }

    public static FegexMatch[] MatchesByMatcher(Matcher matcher) {
        LinkedList<FegexMatch> matches = new LinkedList<>();
        while (matcher.find()) {
            matches.add(new FegexMatch(matcher));
        }
        return matches.toArray(new FegexMatch[0]);
    }


    @LuaWhitelist
    public int start() {return _start;}
    @LuaWhitelist
    public int end() {return _end;}
    @LuaWhitelist
    public String content() {return _content;}
    @LuaWhitelist
    public Map<Integer, FegexGroup> groups() {return _groups;}

    @Override
    public String toString() {
        return _content;
    }
}