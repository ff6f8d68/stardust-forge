package cool.ender.stardust.sandbox;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptSystem {
    public static ScriptEngineManager manager = new ScriptEngineManager();
    public ScriptEngine engine;

    public ScriptSystem() {
        engine = manager.getEngineByName("javascript");
    }

    public ScriptSystem(String language) {
        engine = manager.getEngineByName(language);
    }

    /**
     * load script libs providing apis
     * */
    private void loadLibraries() {
    }

    /**
     * execute code from event
     * */
    public String execute(String code) throws ScriptException {
        Object object = this.engine.eval(code);
        return object.toString();
    }

    /**
     * get a global var from sandbox
     * */
    public <T> T getValue(String varName, Class<T> type) {
        return (T) this.engine.get(varName);
    }


}
