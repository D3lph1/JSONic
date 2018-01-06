package com.github.d3lph1.jsonic.naming;

/**
 * Translates a word from camelCase to under_case.
 *
 * @author D3lph1
 */
public class UnderCaseNamingStrategy implements NamingStrategy
{
    @Override
    public String resolve(String name)
    {
        return name
                .replaceAll("([a-z])([A-Z]+)", "$1_$2")
                .toLowerCase();
    }
}
