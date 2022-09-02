package org.lexize.fegex;

import org.luaj.vm2.LuaFunction;
import org.moon.figura.avatars.Avatar;
import org.moon.figura.lua.FiguraAPI;
import org.moon.figura.lua.LuaWhitelist;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@LuaWhitelist
public class Fegex implements FiguraAPI {

    private Avatar _avatar;

    @LuaWhitelist
    public static final Map<String, Integer> flags;

    static  {
        flags = new HashMap<>();
        flags.put("UNIX_LINES",                 Pattern.UNIX_LINES);
        flags.put("CASE_INSENSITIVE",           Pattern.CASE_INSENSITIVE);
        flags.put("COMMENTS",                   Pattern.COMMENTS);
        flags.put("MULTILINE",                  Pattern.MULTILINE);
        flags.put("LITERAL",                    Pattern.LITERAL);
        flags.put("DOTALL",                     Pattern.DOTALL);
        flags.put("UNICODE_CASE",               Pattern.UNICODE_CASE);
        flags.put("CANON_EQ",                   Pattern.CANON_EQ);
        flags.put("UNICODE_CHARACTER_CLASS",    Pattern.UNICODE_CHARACTER_CLASS);
    }

    public Fegex(Avatar avatar) {
        _avatar = avatar;
    }

    public Fegex() {
        
    }


    @LuaWhitelist
    public boolean isMatches(String pattern_string, String input) {
        return Pattern.matches(pattern_string, input);
    }

    @LuaWhitelist
    public Map<Integer, FegexMatch> matches(String pattern_string, String input, Integer flags) {
        Pattern pattern;
        if (flags != null) pattern = Pattern.compile(pattern_string, flags);
        else pattern = Pattern.compile(pattern_string);
        FegexMatch[] matches = FegexMatch.MatchesByMatcher(pattern.matcher(input));
        HashMap<Integer, FegexMatch> matchTable = new HashMap<>();
        for (FegexMatch match:
             matches) {
            matchTable.put(matchTable.size()+1, match);
        }
        return matchTable;
    }

    @LuaWhitelist
    public String replace(String pattern_string, String input, Object replaceValue, Integer flags) {
        Pattern pattern;

        LuaFunction replaceFunction = null;
        String replaceString = null;

        if (replaceValue instanceof LuaFunction f) replaceFunction = f;
        if (replaceValue instanceof String f) replaceString = f;

        if (flags != null) pattern = Pattern.compile(pattern_string, flags);
        else pattern = Pattern.compile(pattern_string);

        Matcher m = pattern.matcher(input);

        if (replaceFunction != null) {
            LuaFunction finalReplaceFunction = replaceFunction;
            return m.replaceAll((mr) -> {

                var output = finalReplaceFunction.invoke(_avatar.luaRuntime.typeManager.javaToLua(new FegexMatch(mr)));
                System.out.println(output.tojstring());
                return output.tojstring();
            });
        }
        else {
            return m.replaceAll(replaceString);
        }
    }

    @Override
    public FiguraAPI build(Avatar avatar) {
        return new Fegex(avatar);
    }

    @Override
    public String getName() {
        return "fegex";
    }

    @Override
    public Collection<Class<?>> getWhitelistedClasses() {
        return List.of(Fegex.class, FegexGroup.class, FegexMatch.class);
    }

    @LuaWhitelist
    public Object __index(String args) {
        if (args.equals("flags")) return flags;
        return null;
    }
}
