package org.lexize.fegex;

import org.moon.figura.lua.LuaWhitelist;
import org.moon.figura.lua.docs.LuaMethodDoc;
import org.moon.figura.lua.docs.LuaTypeDoc;

import java.util.LinkedList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;


@LuaTypeDoc(value = "FegexGroup",  name = "FegexGroup")
@LuaWhitelist
public class FegexGroup {

    public Integer _start;
    public Integer _end;
    public String _content;

    private FegexGroup(String content, int start, int end) {
        this._content = content;
        this._start = start;
        this._end = end;
    }

    public static FegexGroup[] RegexGroupsFromMatcher(Matcher matcher) {
        LinkedList<FegexGroup> groups = new LinkedList<>();
        for (int i = 0; i < matcher.groupCount()+1; i++) {
            groups.add(new FegexGroup(matcher.group(i), matcher.start(i), matcher.end(i)));
        }
        return groups.toArray(new FegexGroup[groups.size()]);
    }

    public static FegexGroup[] RegexGroupsFromMatchResult(MatchResult result) {
        LinkedList<FegexGroup> groups = new LinkedList<>();
        for (int i = 0; i < result.groupCount()+1; i++) {
            groups.add(new FegexGroup(result.group(i), result.start(i), result.end(i)));
        }
        return groups.toArray(new FegexGroup[groups.size()]);
    }

    @LuaWhitelist
    public int start() {return _start;}
    @LuaWhitelist
    public int end() {return _end;}
    @LuaWhitelist
    public String content() {return _content;}

    @Override
    public String toString() {
        return _content;
    }
}
