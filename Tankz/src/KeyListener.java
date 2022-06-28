import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class KeyListener {

    private static volatile boolean wPressed = false;
    private static volatile boolean aPressed = false;
    private static volatile boolean sPressed = false;
    private static volatile boolean dPressed = false;
    private static volatile boolean lPressed = false;
    public static boolean isWPressed() {
        synchronized (KeyListener.class) {
            return wPressed;
        }
    }
    public static boolean isAPressed() {
        synchronized (KeyListener.class) {
            return aPressed;
        }
    }
    public static boolean isSPressed() {
        synchronized (KeyListener.class) {
            return sPressed;
        }
    }
    public static boolean isDPressed() {
        synchronized (KeyListener.class) {
            return dPressed;
        }
    }
    public static boolean isLPressed() {
        synchronized (KeyListener.class) {
            return lPressed;
        }
    }

    public static void main(String[] args) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (KeyListener.class) {
                    switch (ke.getID()) {
                        case KeyEvent.KEY_PRESSED:
                            if (ke.getKeyCode() == KeyEvent.VK_W) {
                                wPressed = true;
                            }
                            else if (ke.getKeyCode() == KeyEvent.VK_A) {
                                aPressed = true;
                            }
                            else if (ke.getKeyCode() == KeyEvent.VK_S) {
                            sPressed = true;
                            }
                            else if (ke.getKeyCode() == KeyEvent.VK_D) {
                            dPressed = true;
                            }
                            else if (ke.getKeyCode() == KeyEvent.VK_L) {
                            lPressed = true;
                            }
                            break;

                            case KeyEvent.KEY_RELEASED:
                            if (ke.getKeyCode() == KeyEvent.VK_W) {
                                wPressed = false;
                            }
                            else if (ke.getKeyCode() == KeyEvent.VK_A) {
                                aPressed = false;
                            }
                            else if (ke.getKeyCode() == KeyEvent.VK_S) {
                                sPressed = false;
                            }
                            else if (ke.getKeyCode() == KeyEvent.VK_D) {
                                dPressed = false;
                            }
                            else if (ke.getKeyCode() == KeyEvent.VK_L) {
                                lPressed = false;
                            }
                            break;
                    }
                    return false;
                }
            }
        });
    }
}
