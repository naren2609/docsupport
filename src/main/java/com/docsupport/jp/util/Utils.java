package com.docsupport.jp.util;

import com.docsupport.jp.pojo.Credential;
import com.docsupport.jp.pojo.Employer;
import com.docsupport.jp.pojo.Exceptions;
import com.docsupport.jp.pojo.Person;

public class Utils {
    public static boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }
    public static String extractEmailIdFromCred(Credential cred)
    {
        String emailId  = null;
        if(cred instanceof Person)
        {
            emailId = ((Person)cred).getEmailId();
        }
        else if(cred instanceof Employer)
        {
            emailId = ((Employer)cred).getEmailId();
        }
        else{
            throw new Exceptions.InvalidUserInfoException();
        }
        if (Utils.isBlankString(emailId)) {
            throw new Exceptions.EmailIdNotConfiguredException();
        }
        return emailId;

    }
}
