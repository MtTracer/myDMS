package thirdpower.mydms.jetty;

import com.google.common.io.Resources;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.*;

import java.net.URISyntaxException;
import java.net.URL;

public class JettyServer {

  public static final String WEBAPP_RESOURCES_LOCATION = "webapp";
  static final int DEFAULT_PORT_STOP = 8090;
  static final String STOP_COMMAND = "stop";
  private static final int DEFAULT_PORT_START = 8080;
  private final int startPort;

  public JettyServer() {
    this(DEFAULT_PORT_START, DEFAULT_PORT_STOP);
  }

  public JettyServer(final int startPort, final int stopPort) {
    this.startPort = startPort;
  }

  /**
   * Start a Jetty web server with its webapp.
   *
   * @param args first argument is the web port, second argument is the port used to stop Jetty
   * @throws Exception
   */
  public static void main(final String[] args) throws Exception {
    final JettyServer jettyServer;
    if (args.length == 2) {
      jettyServer = new JettyServer(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
    } else {
      jettyServer = new JettyServer();
    }
    jettyServer.start();
  }

  /**
   * Start a Jetty server then deploy a web application.
   *
   * @throws Exception If Jetty fails to start
   */
  public void start() throws Exception {
    final Server server = new Server(startPort);

    final WebAppContext context = createContext();

    server.setHandler(context);

    server.start();

    server.join();

  }

  private WebAppContext createContext() throws URISyntaxException {
    final WebAppContext webapp = new WebAppContext();
    webapp.setContextPath("/");
    webapp.setDescriptor(WEBAPP_RESOURCES_LOCATION + "/WEB-INF/web.xml");
    webapp.setConfigurations(new Configuration[] {new AnnotationConfiguration(),
        new WebXmlConfiguration(), new WebInfConfiguration(), new PlusConfiguration(),
        new MetaInfConfiguration(), new FragmentConfiguration(), new EnvConfiguration()});
    webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
        ".*/classes/.*");

    final URL webAppDir = Resources.getResource(WEBAPP_RESOURCES_LOCATION);
    if (webAppDir == null) {
      throw new RuntimeException(
          String.format("No %s directory was found into the JAR file", WEBAPP_RESOURCES_LOCATION));
    }
    webapp.setResourceBase(webAppDir.toURI()
      .toString());
    webapp.setParentLoaderPriority(true);
    webapp.getServletContext()
      .setExtendedListenerTypes(true);

    // webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
    // ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$");

    // context.addServlet(DefaultServlet.class, "/");

    return webapp;
  }
}
