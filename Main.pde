import java.awt.*;
import processing.awt.*;

JSurface surf;

void setup() {
  size(50, 50, P2D);

  surf = new JSurface(this);
  surf.dispose();

  println(surf.getRender());
}
