package pro.softcom.archetype.gwt.server.rpc;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.AbstractSerializationStream;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class GwtRpcDispatcher extends RemoteServiceServlet {

	private static final long serialVersionUID = 5869433236930849094L;

	/**
	 * Der Guice injector, der mittels Servlet Modul konfiguriert wurde. Ueber
	 * den Injector wird die Implementation des angeforderten Services geholt
	 * (Dependency Injection).
	 */
	@Inject
	private Injector injector;

	/**
	 * Ueberschreibt Methode von Superklasse {@see
	 * com.google.gwt.user.server.rpc.RemoteServiceServlet#processCall(java.lang
	 * .String)}
	 * 
	 * @param payload
	 *            Der zu dekodierende String.
	 * @return Die kodierte Antwort des Services.
	 * @throws SerializationException
	 *             Wenn ein Serialisierungsfehler auftritt.
	 */
	@Override
	public String processCall(String payload) throws SerializationException {

		try {
			// context Infos setzten
			setContextInfos();
			String result = null;
			try {
				RPCRequest req = RPC.decodeRequest(payload, null, this);
				RemoteService service = getServiceInstance(req.getMethod().getDeclaringClass());
				result = invokeAndEncodeResponse(service, req.getMethod(), req.getParameters(), req.getSerializationPolicy());
			} finally {
				// Context wieder loeschen
				GwtRpcContext.clearContextInfo();
			}
			return result;

		} catch (IncompatibleRemoteServiceException ex) {
			log("IncompatibleRemoteServiceException in the processCall(String) method.", ex);
			return RPC.encodeResponseForFailure(null, ex);
		}
	}

	/**
	 * Setzt die Infos im GwtRpcContext
	 * 
	 */
	protected void setContextInfos() {
		// permutationStrongName wird vom 'StackTraceDeobfuscator' fuer die
		// gwt-logs auf der Serverseite benoetigt
		String permutationStrongName = getPermutationStrongName();
		GwtRpcContext.setPermutationStrongName(permutationStrongName);

	}

	/**
	 * Hole den Service fuer das Interface, der mittels Servlet Modul
	 * konfiguriert wurde (Dependency Injection).
	 * 
	 * @param serviceClass
	 *            Der angeforderte Service.
	 * @return Die Service Implementierung.
	 */
	private RemoteService getServiceInstance(Class<?> serviceClass) {
		return (RemoteService) injector.getInstance(serviceClass);
	}

	/**
	 * *************************************************************************
	 * ******************** Die nachfolgende Methode wurde aus der Klasse
	 * com.google.gwt.user.server.rpc.RPC kopiert und das Exception Handling
	 * f�r Runtime- und ValidationException angepasst
	 * *******************************
	 * **************************************************************
	 */

	private String invokeAndEncodeResponse(Object target, Method serviceMethod, Object[] args, SerializationPolicy serializationPolicy, int flags) throws SerializationException {
		if (serviceMethod == null) {
			throw new NullPointerException("serviceMethod");
		}

		if (serializationPolicy == null) {
			throw new NullPointerException("serializationPolicy");
		}

		String responsePayload;

		try {
			Object result = serviceMethod.invoke(target, args);
			responsePayload = RPC.encodeResponseForSuccess(serviceMethod, result, serializationPolicy, flags);

		} catch (IllegalAccessException e) {
			SecurityException securityException = new SecurityException(GwtRpcDispatcher.formatIllegalAccessErrorMessage(target, serviceMethod));
			securityException.initCause(e);
			throw securityException;

		} catch (IllegalArgumentException e) {
			SecurityException securityException = new SecurityException(GwtRpcDispatcher.formatIllegalArgumentErrorMessage(target, serviceMethod, args));
			securityException.initCause(e);
			throw securityException;

		} catch (InvocationTargetException e) {
			// Hier landen wir, wenn eine Exception in der Applikation passiert
			// ist
			// Wir moechten die echte Exception zurueckgeben, uns interessiert
			// nur der Cause
			// Die Methode 'wrapExceptionIfNecessary' erlaubt,
			// projektspezifische Anpassungen bezueglich Exception-Wrapping
			// vorzunehmen
			Throwable throwableToUse = wrapExceptionIfNecessary(e.getCause());
			responsePayload = RPC.encodeResponseForFailure(serviceMethod, throwableToUse, serializationPolicy, flags);
		}

		return responsePayload;
	}

	protected Throwable wrapExceptionIfNecessary(Throwable throwableToWrap) {
		// Sonderbehandlung fuer javax.validation.ValidationException
		if (throwableToWrap instanceof ValidationException) {
			return throwableToWrap;
		}

		if (throwableToWrap instanceof RuntimeException) {
			// Default fuer RuntimeExceptions: Wrappe die RuntimeException (der
			// Cause ist nun String-ifiziert und die Exception kann vom GWT
			// Compiler umgesetzt werden)
			return throwableToWrap;
		}

		return throwableToWrap;
	}

	/**
	 * *************************************************************************
	 * ******************** Die nachfolgenden Methoden wurden 1:1 aus der Klasse
	 * com.google.gwt.user.server.rpc.RPC kopiert Ausnahme:
	 * invokeAndEncodeResponse ist nicht mehr static
	 * ****************************
	 * *****************************************************************
	 */

	/**
	 * Kopie aus com.google.gwt.user.server.rpc.RPC, ==> NICHT MEHR STATIC <==
	 */
	public String invokeAndEncodeResponse(Object target, Method serviceMethod, Object[] args, SerializationPolicy serializationPolicy) throws SerializationException {
		return invokeAndEncodeResponse(target, serviceMethod, args, serializationPolicy, AbstractSerializationStream.DEFAULT_FLAGS);
	}

	private static String formatIllegalAccessErrorMessage(Object target, Method serviceMethod) {
		StringBuffer sb = new StringBuffer();
		sb.append("Blocked attempt to access inaccessible method '");
		sb.append(GwtRpcDispatcher.getSourceRepresentation(serviceMethod));
		sb.append("'");

		if (target != null) {
			sb.append(" on target '");
			sb.append(GwtRpcDispatcher.printTypeName(target.getClass()));
			sb.append("'");
		}

		sb.append("; this is either misconfiguration or a hack attempt");

		return sb.toString();
	}

	private static String formatIllegalArgumentErrorMessage(Object target, Method serviceMethod, Object[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append("Blocked attempt to invoke method '");
		sb.append(GwtRpcDispatcher.getSourceRepresentation(serviceMethod));
		sb.append("'");

		if (target != null) {
			sb.append(" on target '");
			sb.append(GwtRpcDispatcher.printTypeName(target.getClass()));
			sb.append("'");
		}

		sb.append(" with invalid arguments");

		if (args != null && args.length > 0) {
			sb.append(Arrays.asList(args));
		}

		return sb.toString();
	}

	private static String getSourceRepresentation(Method method) {
		return method.toString().replace('$', '.');
	}

	private static String printTypeName(Class<?> type) {
		// Primitives
		//
		if (type.equals(Integer.TYPE)) {
			return "int";
		} else if (type.equals(Long.TYPE)) {
			return "long";
		} else if (type.equals(Short.TYPE)) {
			return "short";
		} else if (type.equals(Byte.TYPE)) {
			return "byte";
		} else if (type.equals(Character.TYPE)) {
			return "char";
		} else if (type.equals(Boolean.TYPE)) {
			return "boolean";
		} else if (type.equals(Float.TYPE)) {
			return "float";
		} else if (type.equals(Double.TYPE)) {
			return "double";
		}

		// Arrays
		//
		if (type.isArray()) {
			Class<?> componentType = type.getComponentType();
			return GwtRpcDispatcher.printTypeName(componentType) + "[]";
		}

		// Everything else
		//
		return type.getName().replace('$', '.');
	}

	/**
	 * SerializationPolicy ueberschreiben da die Default Implementation prueft
	 * ob die Request Url die gleiche ist wie die Modul URL. Dies fuehrt zu
	 * Problemen, wenn im Portal fuer eine Applikation mit flip/flop gearbeitet
	 * wird.
	 * 
	 * 
	 * @param request
	 * @param moduleBaseURL
	 * @param strongName
	 * @return
	 */
	@Override
	protected SerializationPolicy doGetSerializationPolicy(HttpServletRequest request, String moduleBaseURL, String strongName) {

		// Code ubernommen aus RemoteServiceServlet

		// The request can tell you the path of the web app relative to the
		// container root.
		String contextPath = request.getContextPath();

		String modulePath = null;
		if (moduleBaseURL != null) {
			try {
				modulePath = new URL(moduleBaseURL).getPath();
			} catch (MalformedURLException ex) {
				// log the information, we will default
				log("Malformed moduleBaseURL: " + moduleBaseURL, ex);
			}
		}

		SerializationPolicy serializationPolicy = null;

		/*
		 * Check that the module path must be in the same web app as the servlet
		 * itself. If you need to implement a scheme different than this,
		 * override this method.
		 * 
		 * ANGEPASST:fuer den Vergleich flip oder flop entfernen aus der UR
		 */
		if (modulePath == null || !modulePath.startsWith(contextPath)) {
			String message = "ERROR: The module path requested, " + modulePath + ", is not in the same web application as this servlet, " + contextPath
			        + ".  Your module may not be properly configured or your client and server code maybe out of date.";
			log(message);
		} else {
			// Strip off the context path from the module base URL. It should be
			// a
			// strict prefix.
			String contextRelativePath = modulePath.substring(contextPath.length());

			String serializationPolicyFilePath = SerializationPolicyLoader.getSerializationPolicyFileName(contextRelativePath + strongName);

			// Open the RPC resource file and read its contents.
			InputStream is = getServletContext().getResourceAsStream(serializationPolicyFilePath);
			try {
				if (is != null) {
					try {
						serializationPolicy = SerializationPolicyLoader.loadFromStream(is, null);
					} catch (ParseException e) {
						log("ERROR: Failed to parse the policy file '" + serializationPolicyFilePath + "'", e);
					} catch (IOException e) {
						log("ERROR: Could not read the policy file '" + serializationPolicyFilePath + "'", e);
					}
				} else {
					String message = "ERROR: The serialization policy file '" + serializationPolicyFilePath + "' was not found; did you forget to include it in this deployment?";
					log(message);
				}
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// Ignore this error
					}
				}
			}
		}

		return serializationPolicy;

	}
}