# JSurface - What is this?

JSurface is an add-on designed to optimize working with the Processing PSurface interface. It provides a variety of methods for customizing your window.

# How can I use JSurface?

To use JSurface in your project, download the JSurface.java file and place it in your sketch directory. The Processing Development Environment (PDE) will automatically detect it. To create a JSurface instance, use JSurface surf; and in the `setup()` method, after `size()`, instantiate it with `surf = new JSurface(this);`. If you're using the `settings()` method, make sure to call it from within the `setup()` function as well.
