package org.jepria.oauth.sdk;

import org.jepria.oauth.sdk.util.URIUtil;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.jepria.oauth.sdk.OAuthConstants.TOKEN;

/**
 * <p>Builder for Token revocation request. Permanently blocks incoming token in OAuth provider.
 *
 * <p>Example:
 * <pre>
 *     TokenRevocationRequest request = new TokenRevocationRequest.Builder()
 *       .resourceURI(URI.create("http://server/" + OAUTH_TOKENREVOKE_CONTEXT_PATH))
 *       .token(tokenString)
 *       .clientId("clientID")
 *       .clientSecret("clientSecret")
 *       .build();
 *     request.execute();
 * </pre>
 *
 */
public class TokenRevocationRequest {

  private final URI resourceURI;
  private final String tokenString;
  private final String clientId;
  private final String clientSecret;


  public TokenRevocationRequest(URI resourceURI, String tokenString, String clientId, String clientSecret) {
    Objects.requireNonNull(resourceURI, "Request URI must be not null");
    this.resourceURI = resourceURI;
    Objects.requireNonNull(tokenString, "Token URI must be not null");
    this.tokenString = tokenString;
    Objects.requireNonNull(clientId, "Client ID must be not null");
    this.clientId = clientId;
    Objects.requireNonNull(clientSecret, "Client Secret must be not null");
    this.clientSecret = clientSecret;
  }

  private HttpURLConnection toHttpURLConnection(final HostnameVerifier hostnameVerifier, final SSLSocketFactory sslSocketFactory) throws IOException {
    Map<String, String> params = new HashMap<>();
    params.put(TOKEN, tokenString);
    String query = "";
    try {
      query = URIUtil.serializeParameters(params, null);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    URL finalURL = null;
    try {
      finalURL = new URI(resourceURI.getScheme(), resourceURI.getAuthority(), resourceURI.getPath(), "", resourceURI.getFragment()).toURL();
    } catch (URISyntaxException | MalformedURLException e) {
      e.printStackTrace();
    }

    HttpURLConnection conn = (HttpURLConnection) finalURL.openConnection();

    if (conn instanceof HttpsURLConnection) {
      HttpsURLConnection sslConn = (HttpsURLConnection)conn;
      sslConn.setHostnameVerifier(hostnameVerifier != null ? hostnameVerifier : new DefaultHostnameVerifier());
      sslConn.setSSLSocketFactory(sslSocketFactory != null ? sslSocketFactory :  (SSLSocketFactory) SSLSocketFactory.getDefault());
    }

    conn.setRequestMethod("POST");
    conn.setConnectTimeout(10000);
    conn.setReadTimeout(0);
    conn.setInstanceFollowRedirects(true);
    if (clientSecret != null) {
      conn.setRequestProperty("Authorization", "Basic " +
        Base64.getUrlEncoder().withoutPadding().encodeToString((clientId + ":" + clientSecret).getBytes()));
    }

    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    conn.setRequestProperty("charset", "utf-8");
    conn.setRequestProperty("Content-Length", Integer.toString(query.length()));
    conn.setUseCaches(false);

    conn.setDoOutput(true);
    if (query != null) {
      try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
        writer.write(query);
      }
    }
    return conn;
  }

  /**
   * Executes Token Info Request..
   *
   * @return
   * @throws IOException
   */
  public boolean execute() throws IOException {
    return execute(null, null);
  }

  /**
   * Executes Token Info Request.
   * Provide HostnameVerifier and SSLSocketFactory if your origin and destination aren't at the same Host to establish HTTPS connection.
   *
   * @param hostnameVerifier
   * @param sslSocketFactory
   * @return
   * @throws IOException
   */
  public boolean execute(final HostnameVerifier hostnameVerifier, final SSLSocketFactory sslSocketFactory) throws IOException {
    HttpURLConnection conn = toHttpURLConnection(hostnameVerifier, sslSocketFactory);
    int statusCode;
    try {
      statusCode = conn.getResponseCode();
    } catch (IOException e) {
      // HttpUrlConnection will throw an IOException if any
      // 4XX response is sent. If we request the status
      // again, this time the internal status will be
      // properly set, and we'll be able to retrieve it.
      statusCode = conn.getResponseCode();
      if (statusCode == -1) {
        throw e; // Rethrow IO exception
      } else {
        throw new IOException("HTTP Response Status Code: " + statusCode + '\n' + e.getLocalizedMessage());
      }
    }
    if (statusCode == 200) {
      return true;
    } else {
      return false;
    }
  }

  private final class DefaultHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
      return hostname.equals(session.getPeerHost());
    }
  }

  public static Builder Builder() {
    return new BuilderImpl();
  }

  public interface Builder {
    Builder token(String tokenString);
    Builder clientId(String clientId);
    Builder clientSecret(String clientSecret);
    Builder resourceURI(URI resourceURI);
    TokenRevocationRequest build();
  }

  private static final class BuilderImpl implements Builder {

    private URI resourceURI;
    private String tokenString;
    private String clientId;
    private String clientSecret;
    private boolean isBuilt = false;

    @Override
    public Builder token(String tokenString) {
      if (isBuilt) {
        new IllegalStateException("Builder is finished");
      }
      this.tokenString = tokenString;
      return this;
    }

    @Override
    public Builder clientId(String clientId) {
      if (isBuilt) {
        new IllegalStateException("Builder is finished");
      }
      this.clientId = clientId;
      return this;
    }

    @Override
    public Builder clientSecret(String clientSecret) {
      if (isBuilt) {
        new IllegalStateException("Builder is finished");
      }
      this.clientSecret = clientSecret;
      return this;
    }

    @Override
    public Builder resourceURI(URI resourceURI) {
      if (isBuilt) {
        new IllegalStateException("Builder is finished");
      }
      this.resourceURI = resourceURI;
      return this;
    }

    @Override
    public TokenRevocationRequest build() {
      if (isBuilt) {
        new IllegalStateException("Builder is finished");
      }
      return new TokenRevocationRequest(resourceURI, tokenString, clientId, clientSecret);
    }
  }
}
