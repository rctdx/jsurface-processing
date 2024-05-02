NSurface ns;

void setup() {
  size(500, 500, JAVA2D);
  ns = new NSurface(this);
  
  System.out.println(ns.getFrame().getClass());
}
