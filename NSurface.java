import processing.core.PApplet;
import processing.core.PSurface;
import processing.awt.PSurfaceAWT.SmoothCanvas;

import javax.swing.JFrame;
import java.awt.Frame;
import com.jogamp.newt.opengl.GLWindow;

public class NSurface {
  public PSurface surface;

  public NSurface (PApplet applet) {
    if (applet == null) {
      throw new NSurface.Exception("PApplet is null");
    }
    this.surface = applet.getSurface();
  }

  public boolean isJava2DRender() {
    return !(surface.getNative() instanceof GLWindow);
  }

  public Object getNative() {
    return surface.getNative();
  }
  
  public SmoothCanvas getCanvas() {
    if (isJava2DRender()) {
      return (SmoothCanvas)getNative();
    } else {
      System.out.println("< NSurface Error >");
      System.out.println("getCanvas(): Render is not JAVA2D");
      return null;
    }
  }
  
  public Frame getFrame() {
    if (isJava2DRender()) {
      return getCanvas().getFrame();
    } else {
      System.out.println("< NSurface Error >");
      System.out.println("getFrame(): Render is not JAVA2D");
      return null;
    }
  }
  
  public JFrame getJFrame() {
    if (isJava2DRender()) {
      return (JFrame)getFrame();
    } else {
      System.out.println("< NSurface Error >");
      System.out.println("getJFrame(): Render is not JAVA2D");
      return null;
    }
  }
  
  // Thread control
  public void stopThread() {
    surface.stopThread();
  }
  
  public void startThread() {
    surface.startThread();
  }
  
  public void pauseThread() {
    surface.pauseThread();
  }
  
  public void resumeThread() {
    surface.resumeThread();
  }
  
  // Frame control
  public void setVisible(boolean value) {
    getJFrame().setVisible(value);
  }
  
  public void setResizable(boolean value) {
    getJFrame().setResizable(value);
  }
  
  public void setTitle(String value) {
    getJFrame().setTitle(value);
  }
  
  public void setSize(int width, int height) {
    getJFrame().setSize(width, height);
    surface.setSize(width, height);
  }
  
  public void setAlwaysOnTop(boolean value) {
    surface.setAlwaysOnTop(value);
  }
  
  // NSurface$Exception
  public static class Exception extends RuntimeException {
    public Exception() {
      super();
    }

    public Exception(String message) {
      super(message);
    }

    public Exception(String message, Throwable cause) {
      super(message, cause);
    }

    public Exception(Throwable cause) {
      super(cause);
    }
  }
}
