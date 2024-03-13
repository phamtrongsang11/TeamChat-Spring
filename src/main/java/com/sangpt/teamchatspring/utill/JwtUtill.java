package com.sangpt.teamchatspring.utill;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtill {
    @Value("${security.jwt.jwk-set-uri}")
    private String jwkSetUri;

    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            JWKSet jwkSet = retrieveJWKSet();

            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSAlgorithm algorithm = signedJWT.getHeader().getAlgorithm();

            if (!algorithm.equals(JWSAlgorithm.RS256)) {
                return false;
            }

            RSAKey rsaKey = (RSAKey) jwkSet.getKeyByKeyId(signedJWT.getHeader().getKeyID());
            if (rsaKey == null) {
                return false;
            }

            RSASSAVerifier verifier = new RSASSAVerifier((RSAPublicKey) rsaKey.toPublicKey());
            if (!signedJWT.verify(verifier)) {
                return false;
            }

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            String email = claimsSet.getClaim("email").toString();

            // System.out.println(claimsSet.getClaim("email"));
            // if (claimsSet.getExpirationTime().before(new Date())) {
            // return false;
            // }

            request.setAttribute("email", email);
            // System.out.println(request.getAttribute("email"));

            return true;
        } catch (ParseException | JOSEException | IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private JWKSet retrieveJWKSet() throws IOException, ParseException {
        URL jwksURL = new URL("https://supreme-pelican-64.clerk.accounts.dev/.well-known/jwks.json");
        return JWKSet.load(jwksURL);
    }
}
