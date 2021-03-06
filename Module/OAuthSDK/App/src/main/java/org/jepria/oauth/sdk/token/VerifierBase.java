package org.jepria.oauth.sdk.token;

import org.jepria.oauth.sdk.token.Verifier;
import org.jepria.oauth.sdk.token.Token;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Basic verifier for JWT tokens.
 * Checks AUD, ISS, EXP claims.
 */
public class VerifierBase implements Verifier {

  protected final List<String> aud;
  protected final String iss;
  protected final Date expiryDate;

  public VerifierBase(List<String> aud, String iss, Date expiryDate) {
    this.aud = aud;
    this.iss = iss;
    this.expiryDate = expiryDate;
  }

  @Override
  public boolean verify(Token token) throws ParseException {
    Objects.requireNonNull(token);
    return Objects.equals(token.getIssuer(), iss)
      && (expiryDate != null ?
        expiryDate.before(token.getExpirationTime())
        : true)
      && (aud != null && token.getAudience() != null ?
        aud.equals(token.getAudience()) || token.getAudience().containsAll(aud)
        : true);
  }
}
