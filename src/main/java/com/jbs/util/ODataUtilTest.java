package com.jbs.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rizkykojek on 4/2/17.
 */
public final class ODataUtilTest {

    public static final String URI_API = "https://api10.successfactors.com/odata/v2";
    public static final String AUTHORIZATION = "Basic " + new String(Base64.encodeBase64(("adminddg@jbsaustralD:daya111").getBytes()));

    public static final String HTTP_METHOD_PUT = "PUT";
    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_DELETE = "DELETE";

    public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HTTP_HEADER_ACCEPT = "Accept";
    public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";

    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_ATOM_XML = "application/atom+xml";
    public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
    public static final String METADATA = "$metadata";
    public static final String INDEX = "/index.jsp";
    public static final String SEPARATOR = "/";

    public static final boolean PRINT_RAW_CONTENT = false;

    public static  Edm initEdm() throws IOException, ODataException {
        print("\n----- Read Edm ------------------------------");
        InputStream content = execute(URI_API + SEPARATOR + METADATA, AUTHORIZATION, APPLICATION_XML, HTTP_METHOD_GET);
        return EntityProvider.readMetadata(content, false);
    }

    public static ODataFeed readFeed(Edm edm, String entitySetName) throws IOException, ODataException {
        print("\n----- Read Feed of entity: "+ entitySetName +" ------------------------------");
        EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
        String absolutUri = createUri(URI_API, entitySetName, null);

        InputStream content = execute(absolutUri, AUTHORIZATION, APPLICATION_JSON, HTTP_METHOD_GET);
        return EntityProvider.readFeed(APPLICATION_JSON, entityContainer.getEntitySet(entitySetName),
                content, EntityProviderReadProperties.init().build());
    }

    private static String createUri(String serviceUri, String entitySetName, String id) {
        return createUri(serviceUri, entitySetName, id, null);
    }

    private static String createUri(String serviceUri, String entitySetName, String id, String expand) {
        final StringBuilder absolutUri = new StringBuilder(serviceUri).append(SEPARATOR).append(entitySetName);
        if(id != null) {
            absolutUri.append("(").append(id).append(")");
        }
        if(expand != null) {
            absolutUri.append("/?$expand=").append(expand);
        }
        return absolutUri.toString();
    }

    private static InputStream execute(String relativeUri, String authorization, String contentType, String httpMethod) throws IOException {
        HttpURLConnection connection = initializeConnection(relativeUri, authorization, contentType, httpMethod);

        connection.connect();
        checkStatus(connection);

        InputStream content = connection.getInputStream();
        content = logRawContent(httpMethod + " request on uri '" + relativeUri + "' with content:\n  ", content, "\n");
        return content;
    }

    private static HttpURLConnection initializeConnection(String absolutUri, String authorization, String contentType, String httpMethod)
            throws IOException {
        URL url = new URL(absolutUri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(httpMethod);
        connection.setRequestProperty(HTTP_HEADER_ACCEPT, contentType);
        connection.setRequestProperty(HTTP_HEADER_AUTHORIZATION, authorization);
        if(HTTP_METHOD_POST.equals(httpMethod) || HTTP_METHOD_PUT.equals(httpMethod)) {
            connection.setDoOutput(true);
            connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, contentType);
        }

        return connection;
    }

    private static HttpStatusCodes checkStatus(HttpURLConnection connection) throws IOException {
        HttpStatusCodes httpStatusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
        if (400 <= httpStatusCode.getStatusCode() && httpStatusCode.getStatusCode() <= 599) {
            throw new RuntimeException("Http Connection failed with status " + httpStatusCode.getStatusCode() + " " + httpStatusCode.toString());
        }
        return httpStatusCode;
    }

    private static InputStream logRawContent(String prefix, InputStream content, String postfix) throws IOException {
        if(PRINT_RAW_CONTENT) {
            byte[] buffer = streamToArray(content);
            print(prefix + new String(buffer) + postfix);
            return new ByteArrayInputStream(buffer);
        }
        return content;
    }

    private static byte[] streamToArray(InputStream stream) throws IOException {
        byte[] result = new byte[0];
        byte[] tmp = new byte[8192];
        int readCount = stream.read(tmp);
        while(readCount >= 0) {
            byte[] innerTmp = new byte[result.length + readCount];
            System.arraycopy(result, 0, innerTmp, 0, result.length);
            System.arraycopy(tmp, 0, innerTmp, result.length, readCount);
            result = innerTmp;
            readCount = stream.read(tmp);
        }
        stream.close();
        return result;
    }

    private static void print(String content) {
        System.out.println(content);
    }
}
