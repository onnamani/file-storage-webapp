package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getUserCredentials(Integer userId) {
        return this.credentialMapper.getCredentials(userId);
    }

    public Integer deleteUserCredential(Integer credentialId) {
        return this.credentialMapper.deleteCredential(credentialId);
    }

    public Integer saveUserCredential(Credential credential, User user) {
       String[] secureKeyAndPassword = getSecureKeyAndPassword(credential);

        return credentialMapper.saveCredential(new Credential(
                null,
                credential.getUrl(),
                credential.getUsername(),
                secureKeyAndPassword[0],
                secureKeyAndPassword[1],
                user.getUserId()
        ));
    }

    public Integer updateUserCredential(Credential credential) {
        String[] secureKeyAndPassword = getSecureKeyAndPassword(credential);

        return credentialMapper.updateCredential(new Credential(
                credential.getCredentialId(),
                credential.getUrl(),
                credential.getUsername(),
                secureKeyAndPassword[0],
                secureKeyAndPassword[1],
                credential.getUserid()
        ));
    }

    public String[] getSecureKeyAndPassword (Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        return new String[] {encodedKey, encryptedPassword};
    }
}
