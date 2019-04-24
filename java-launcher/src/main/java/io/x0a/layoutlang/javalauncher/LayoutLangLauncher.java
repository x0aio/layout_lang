package io.x0a.layoutlang.javalauncher;

import io.x0a.layoutlang.LayoutDefinition;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LayoutLangLauncher {

    public static void main(String[] args) throws Exception {

        ScriptEngine engine = new ScriptEngineManager(ClassLoader.getSystemClassLoader()).getEngineByExtension("kts");
        InputStream stream = ClassLoader.getSystemResourceAsStream("test.kts");
        LayoutDefinition result = (LayoutDefinition) engine.eval(new InputStreamReader(stream));
        result.print();
    }
}