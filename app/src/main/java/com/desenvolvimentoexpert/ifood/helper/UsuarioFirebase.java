package com.desenvolvimentoexpert.ifood.helper;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UsuarioFirebase {

    public static String getIdUser() {
        return FirebaseConfig.getFirebaseAuth().getCurrentUser().getUid();
    }

    public static FirebaseUser getUsuarioAtual() {
        return FirebaseConfig.getFirebaseAuth().getCurrentUser();
    }

    public static boolean atualizarTipoUsuario(String tipo) {
        try {
            FirebaseUser user = getUsuarioAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(tipo)
                    .build();
            user.updateProfile(profile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
