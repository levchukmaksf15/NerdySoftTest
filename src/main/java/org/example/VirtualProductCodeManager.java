package org.example;

import java.util.*;

public final class VirtualProductCodeManager {

    private static VirtualProductCodeManager INSTANCE;

    private final Set<String> codeRegister = new HashSet<>();

    private VirtualProductCodeManager() {

    }

    public static VirtualProductCodeManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new VirtualProductCodeManager();
        }

        return INSTANCE;
    }

    public void useCode (String code) {
        codeRegister.add(code);
    }

    public boolean isCodeUsed(String code) {
        return codeRegister.contains(code);
    }
}