// Importing processing core, awt, opengl
import processing.core.PApplet;
import processing.core.PSurface;
import processing.awt.PSurfaceAWT;
import processing.awt.PSurfaceAWT.SmoothCanvas;
import processing.opengl.PSurfaceJOGL;

// To handle EDT, and JFrame
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// GLWindow
import com.jogamp.newt.opengl.GLWindow;

// Java AWT classes
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.Graphics;

// AWT Events
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Exceptions
import java.lang.reflect.InvocationTargetException;

// Defining main class, to work with
public class JSurface {
    private PSurface surface;

    // Constructor when given a PApplet
    public JSurface(PApplet papplet) {
        this.surface = papplet.getSurface();
    }

    // Constructor when given a PSurface
    public JSurface(PSurface surface) {
        this.surface = surface;
    }

    // Enum for render types
    // Used for inner methods, classes
    public enum Render {
        Java2D, // Java AWT Renderer (JAVA2D)
        OpenGL, // GLWindow, aka OpenGL Renderer (P2D, P3D)
        UNKNOWN; // Not normal in the Processing context, but if some other libraries provide a new render core

        @Override
        public String toString() {
            switch (ordinal()) {
                case 0:
                    return "Java2D";
                case 1:
                    return "OpenGL";
                case 2:
                    return "Unknown";
                default:
                    return null;
            }
        }
    }

    // Get render type (returns Render.UNKNOWN if no PSurfaceAWT, PSurfaceJOGL)
    public Render getRender() {
        if (surface instanceof PSurfaceAWT) { // if Java2D
            return Render.Java2D;
        } else if (surface instanceof PSurfaceJOGL) { // if OpenGL
            return Render.OpenGL;
        } else {
            return Render.UNKNOWN;
        }
    }

    // Additional methods to check render types
    public boolean isJava2DRender() {
        return getRender() == Render.Java2D;
    }

    public boolean isOpenGLRender() {
        return getRender() == Render.OpenGL;
    }

    // Get native object of drawing canvas (e.g. SmoothCanvas or GLwindow)
    public Object getNative() {
        return surface.getNative();
    }

    // Get PSurface native objects
    public PSurfaceAWT.SmoothCanvas getSmoothCanvas() {
        if (isJava2DRender()) {
            return (PSurfaceAWT.SmoothCanvas) getNative();
        } else {
            error("getSmoothCanvas", "Render is not Java2D");
            return null;
        }
    }

    public Frame getFrame() {
        if (isJava2DRender()) {
            return getSmoothCanvas().getFrame();
        } else {
            error("getFrame", "Render is not Java2D");
            return null;
        }
    }

    public JFrame getJFrame() {
        if (isJava2DRender()) {
            return (JFrame) getFrame();
        } else {
            error("getJFrame", "Render is not Java2D");
            return null;
        }
    }

    public GLWindow getGLWindow() {
        if (isOpenGLRender()) {
            return (GLWindow) getNative();
        } else {
            error("getGLWindow", "Render is not OpenGL");
            return null;
        }
    }

    // Senseless method. PSurface can be achieved from PApplet (Processing Core)
    public PSurface getPSurface() {
        return surface;
    }

    // Basic window control methods
    public void setVisible(boolean value) {
        OnRender onrender = new OnRender() {
            @Override
            public void java2d() {
                getJFrame().setVisible(value);
            }

            @Override
            public void opengl() {
                getGLWindow().setVisible(value);
            }
        };
        runOnRender(onrender);
    }

    public void setResizable(boolean value) {
        OnRender onrender = new OnRender() {
            @Override
            public void java2d() {
                getJFrame().setResizable(value);
            }

            @Override
            public void opengl() {
                getGLWindow().setResizable(value);
            }
        };
        runOnRender(onrender);
    }

    public void setLocation(int x, int y) {
        OnRender onrender = new OnRender() {
            @Override
            public void java2d() {
                getJFrame().setLocation(x, y);
            }

            @Override
            public void opengl() {
                getGLWindow().setPosition(x, y);
            }
        };
        runOnRender(onrender);
    }

    public void setSize(int x, int y) {
        OnRender onrender = new OnRender() {
            @Override
            public void java2d() {
                getJFrame().setSize(x, y);
            }

            @Override
            public void opengl() {
                getGLWindow().setSize(x, y);
            }
        };
        runOnRender(onrender);
    }

    public void dispose() {
        OnRender onrender = new OnRender() {
            @Override
            public void java2d() {
                getJFrame().dispose();
            }

            @Override
            public void opengl() {
                getGLWindow().setVisible(false); // Changed from destroy() to setVisible(false)
            }
        };
        runOnRender(onrender);
    }

    // OnRender interface
    public interface OnRender {
        public void java2d();

        public void opengl();

        // Default method for unknown render types
        public default void unknown() {
            error("OnRender.unknown", "Not implemented because the render type is undefined");
        }
    }

    // Method to run actions based on the render type
    public void runOnRender(OnRender onrender) {
        if (onrender == null) {
            error("runOnRender", "Input param is null");
            return;
        }
        switch (getRender()) {
            case Java2D:
                onrender.java2d();
                break;
            case OpenGL:
                onrender.opengl();
                break;
            case UNKNOWN:
            default:
                onrender.unknown();
                break;
        }
    }

    // Method to handle errors
    private static void error(String function, String message) {
        System.out.println(" < JSurface Error >");
        System.out.println(function + "(): " + message);
    }
}