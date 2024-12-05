package com.example.gymapp.authentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Gyms")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/Gyms",
        callerQuery = "select password from trainers where name = ?",
        groupsQuery = "select role from users__roles where id = (select id from trainers where name = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
/*        hashAlgorithmParameters = {
                "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA256",
                "Pbkdf2PasswordHash.Iterations=2048",
                "Pbkdf2PasswordHash.SaltSizeBytes=32",
                "Pbkdf2PasswordHash.KeySizeBytes=32"
        }*/

)
public class AuthenticationConfig {
}
