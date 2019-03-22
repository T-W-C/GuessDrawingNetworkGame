package database.dao;

import database.domain.CodeVerification;
import database.domain.PlayerDomain;

public class CodeVerificationDAO implements IDAO {

    private CodeVerification codeVerification;

    public CodeVerificationDAO(PlayerDomain playerDomain){

    }
    @Override
    public Object get() {
        return this.codeVerification;
    }

    @Override
    public void insert(Object domain) {

    }

    @Override
    public void update(Object domain) {

    }

    @Override
    public void delete(Object domain) {

    }


}
