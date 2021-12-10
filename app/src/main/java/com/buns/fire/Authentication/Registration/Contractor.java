package com.buns.fire.Authentication.Registration;

public interface Contractor {
    interface View {
        void InvalidError();
    }

    interface Presenter {
        void verifyReffer(String refId, String uid, String phone_num, String name_);
    }
}
