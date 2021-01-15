package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

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

        return 1;
    }

    public Integer updateUserCredential(Credential credentialObject) {
        return credentialMapper.updateCredential(new Credential(
                credentialObject.getCredentialid(),
                credentialObject.getUrl(),
                credentialObject.getUsername(),
                credentialObject.getKey(),
                credentialObject.getPassword(),
                credentialObject.getUserid()
        ));
    }
}
