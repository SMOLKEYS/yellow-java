package yellow.ui.windows;

import arc.func.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;

/**
 * A Window is a draggable/resizeable {@link Table} that consists of the following components:<br />
 * - A top bar Table consisting of an icon (optional), title (optional) and 4 buttons for closing
 *   the window, minimizing/maximizing the window, and "rolling up" the content window
 *   (hiding the content window without hiding the top bar)<br />
 * - A lower Table where the actual window contents are displayed<br />
 * <br />
 * Windows are created by providing info to a window manager (window templates or transmuting
 * {@link Dialog}s), which then returns a handle to that specific window for only that
 * specific window manager.<br />
 * <br />
 * Windows can be dragged by holding the top bar and dragging it around.
 * <br />
 * Clicking on a Window currently behind another one pulls it above others, unless the
 * upper window is configured to always stay on top; in which case, it tries going
 * above other "always stay on top" windows.
 */
public class Window extends Table{

    /** The window manager that handles this window. */
    private WindowManager manager;
    /** Core {@link Table}s of the window. */
    public Table topBar = new Table(), cont = new Table();

    public Window(float initialWidth, float initialHeight, Cons<Table> cont){
        add(topBar).height(10f).row();
        add(this.cont).grow().row();

        setSize(initialWidth, initialHeight);

        cont.get(this.cont);
    }

    public void resize(float width, float height){
        this.setSize(width, height);
    }

}
