package org.springframework.samples.petclinic.dto;

import java.util.ArrayList;
import java.util.List;

public class AjaxResponse<T> {

    private List<String> messages = new ArrayList<>();
    private boolean isValid = true;
    private T savedElem;

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

    public T getSavedElem() {
        return savedElem;
    }

    public void setSavedElem(T savedElem) {
        this.savedElem = savedElem;
    }
}
