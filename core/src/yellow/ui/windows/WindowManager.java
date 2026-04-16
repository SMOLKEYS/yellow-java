package yellow.ui.windows;

import arc.scene.*;

/**
 * A WindowManager is a special {@link Group} class which is responsible for handling
 * {@link Window}s.
 * <br />
 * Window managers can provide configuration rules that ALL windows under it cannot
 * override.
 * <br />
 * <em>In simpler terms: WindowManager rules override Window rules.</em>
 * <br />
 * There are three possible states for a rule:<br />
 * - {@code INDEPENDENT} - The window is free to configure this rule<br />
 * - {@code TRUE} - The window must ALWAYS assume this rule is true<br />
 * - {@code FALSE} - The window must ALWAYS assume this rule is false
 */

public class WindowManager extends Group{

    public enum WindowRule{
        INDEPENDENT(0),
        TRUE(1),
        FALSE(2);

        public final int rule;

        WindowRule(int rule){
            this.rule = rule;
        }
    }
}
