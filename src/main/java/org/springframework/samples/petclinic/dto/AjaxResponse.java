package org.springframework.samples.petclinic.dto;

import java.util.ArrayList;
import java.util.List;

public class AjaxResponse {

    private List<String> messages = new ArrayList<>();
    private boolean isValid = true;

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public String[] getMessages() {
        return this.messages.toArray(new String[this.messages.size()]);
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
