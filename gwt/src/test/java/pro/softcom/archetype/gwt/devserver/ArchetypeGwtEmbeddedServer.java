package pro.softcom.archetype.gwt.devserver;

import java.io.File;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.security.HashUserRealm;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.webapp.WebAppContext;

public class ArchetypeGwtEmbeddedServer {

    private static final String CONTEXT_PATH = "/archetype";

    private static final File WAR_DIR_SOURCE = new File(new File(".").getAbsolutePath() + "/src/main/webapp");
    private static final String COMPILED_CLIENT_SUBDIRNAME = "archetype";
    private static final File COMPILED_CLIENT_DIR = new File(new File(".").getAbsolutePath() + "/target/gwt-demo-application-gwt-0.99.0-SNAPSHOT/" + COMPILED_CLIENT_SUBDIRNAME);
    private static final File WAR_TEMP_DIR = System.getProperty("os.name").toLowerCase().contains("windows") ? new File("c:/tmp/archetype-war") : new File("/tmp/archetype-war");

    private ArchetypeGwtEmbeddedServer() {
    }

    public static void main(String[] args) {
        try {

            check();

            System.out.println("*********** Classpath **********************************************");
            System.out.println(System.getProperty("java.class.path")
            /* .replaceAll(":", "\n") */.replaceAll(";", "\n"));
            System.out.println("*********** Classpath **********************************************");

            // JNDI InitialContext Factory als System Property setzen, damit
            // auch "new InitialContext()" funktioniert
            System.setProperty("java.naming.factory.initial", "org.apache.openejb.client.LocalInitialContextFactory");

            // initialize OpenEJB (jetty-jndi.properties aus ejb Projekt lesen)
            Properties properties = new Properties();
            properties.load(ArchetypeGwtEmbeddedServer.class.getResourceAsStream("/jetty-jndi.properties"));

            InitialContext ctx = new InitialContext(properties);

            // jdbc/archetype_ds ist in openejb.xml definiert, wir machen jetzt
            // noch Link
            ctx.bind("jdbc/commoncomponents/protoc_req_ds", new javax.naming.LinkRef("java:openejb/Resource/jdbc/archetype_ds"));

            // Gleicher JNDI Context fuer Jetty und Weblogic
            addJndiLinksRefs("java:openejb/local/", "java:comp/env/ejb/");
            dumpJNDI("");
            dumpJNDI("java:");

            //
            // Wir kopieren die statischen html Resourcen (html, css, js) in ein
            // temporaeres Directory,
            // das von jetty als war-directory gelesen wird.
            // Wichtig:
            // - GWT braucht das generierte JavaScript auch im Dev Mode fuer das
            // Bootstrapping
            // - ist auch das generierte *.rpc File (muss bei einer Aenderung
            // der RPC Schnittstelle neu generiert werden)
            //
            FileUtils.deleteDirectory(WAR_TEMP_DIR);
            FileUtils.copyDirectory(WAR_DIR_SOURCE, WAR_TEMP_DIR);
            FileUtils.copyDirectory(COMPILED_CLIENT_DIR, new File(WAR_TEMP_DIR.getAbsolutePath() + "/" + COMPILED_CLIENT_SUBDIRNAME));

            Properties log4jProps = new Properties();
            log4jProps.load(ArchetypeGwtEmbeddedServer.class.getClassLoader().getResourceAsStream("log4j.properties"));
            PropertyConfigurator.configure(log4jProps);

            WebAppContext webAppContext = new WebAppContext();
            // angepasstes webdefault, damit welcomeServlet funktioniert
            webAppContext.setDefaultsDescriptor("etc/jetty/webdefault.xml");
            webAppContext.setWar(WAR_TEMP_DIR.getAbsolutePath());
            ServletHandler embeddedServerServletHandler = new ServletHandler();
            webAppContext.setServletHandler(embeddedServerServletHandler);
            webAppContext.setContextPath(CONTEXT_PATH);
            Server server = new Server(8181);

            HashUserRealm realm = new HashUserRealm("defaultRealm", "etc/jetty/realm.properties");
            webAppContext.getSecurityHandler().setUserRealm(realm);

            server.setHandlers(new Handler[] { webAppContext });

            server.start();
            server.join();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exit program now");
            System.exit(1);
        }
    }

    private static void check() {
        if (!WAR_DIR_SOURCE.exists()) {
            System.err.println("The directory WAR_DIR_CLIENT '" + WAR_DIR_SOURCE.getAbsolutePath() + "' does not exist. Please check your configuration");
            System.exit(4);
        }
    }

    private static void addJndiLinksRefs(String fromNode, String toNode) throws NamingException {
        InitialContext ctx = new InitialContext();
        Context fromCtx = (Context) ctx.lookup(fromNode);
        NamingEnumeration<NameClassPair> list = fromCtx.list("");
        while (list.hasMore()) {
            NameClassPair nameClassPair = list.next();
            String name = nameClassPair.getName();
            String toName = name;
            if (toName.endsWith("BeanLocal") || toName.endsWith("ImplLocal")) {
                toName = toName.substring(0, toName.length() - 9);
                ctx.bind(toNode + toName, new javax.naming.LinkRef(fromNode + name));
            }
        }
    }

    private static void dumpJNDI(String name) throws NamingException {
        Context ctx = (Context) new InitialContext().lookup(name);
        NamingEnumeration<NameClassPair> list = ctx.list("");
        while (list.hasMore()) {
            NameClassPair nameClassPair = list.next();
            String subName = nameClassPair.getName();
            String fullName = name + "/" + subName;
            if (name.length() == 0 || name.endsWith(":")) {
                fullName = name + subName;
            }
            Object o = ctx.lookup(subName);
            System.out.println(fullName + " --> " + o);
            if (o instanceof Context) {
                dumpJNDI(fullName);
            }
        }
    }

}
