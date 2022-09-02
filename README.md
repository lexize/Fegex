# Fegex
Addon that adds Regex into Figura.

#### Example
```lua
local regex = fegex;
local pattern = "(.+)=(.+)";
local str = "foo=bar";

print(regex:isMatches(pattern, str)) -- true
print(regex:isMatches(pattern, "foo-bar")) -- false

local matches = regex:matches(pattern, str);
local match = matches[1];
local groups = match:groups();

for k,v in pairs(groups) do
    print(k..": "..v:content());
end
-- 1: foo
-- 2: bar
-- 0: foo=bar

local replace_string = regex:replace(pattern, str, "$2=$1");
print(replace_string) -- bar=foo
local replace_lambda = regex:replace(pattern, str, function (m)
    local g = m:groups();
    return g[1]:content()..": "..g[2]:content();
end);
print(replace_lambda) -- foo: bar
```

### Short referece
#### Fegex
`isMatches(pattern,str) - Bool`\
Return, does string matches pattern, or no.

`matches(pattern, str) - FegexMatch[]`\
Returns table with found matches.

`replace(pattern, str, replacementString, [flags]) - String`\
Replaces string by pattern and replacement string.
`flags` is optional argument.

`replace(pattern, str, replacementLambda, [flags]) - String`\
Replaces string by pattern and replacement lambda.
`flags` is optional argument.

`flags`\
Enum with available flags.\
Available keys:
* `UNIX_LINES`
* `CASE_INSENSITIVE`
* `COMMENTS`
* `MULTILINE`
* `LITERAL`
* `DOTALL`
* `UNICODE_CASE`
* `CANON_EQ`
* `UNICODE_CHARACTER_CLASS`

#### FegexMatch
`start() - Integer`\
Returns start index of match.

`end() - Integer`\
Return end index of match.

`content() - String`\
Returns content of match.

`groups() - FegexGroup`\
Return groups of match.

#### FegexGroup
`start() - Integer`\
Returns start index of stoup.

`end() - Integer`\
Return end index of group.

`content() - String`\
Returns content of group.


