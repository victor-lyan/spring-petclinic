/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OwnerProfileValidator implements Validator {

    private static final String REQUIRED = "required";

    @Override
    public void validate(Object obj, Errors errors) {
        Owner owner = (Owner) obj;

        // first name validation
        if (!StringUtils.hasLength(owner.getFirstName().trim())) {
            errors.rejectValue("firstName", REQUIRED, REQUIRED);
        }

        // last name validation
        if (!StringUtils.hasLength(owner.getLastName().trim())) {
            errors.rejectValue("lastName", REQUIRED, REQUIRED);
        }

        // address validation
        if (!StringUtils.hasLength(owner.getAddress().trim())) {
            errors.rejectValue("owner", REQUIRED, REQUIRED);
        }

        // city validation
        if (!StringUtils.hasLength(owner.getCity().trim())) {
            errors.rejectValue("city", REQUIRED, REQUIRED);
        }

        // phone validation
        String phone = owner.getPhone().trim();
        if (!StringUtils.hasLength(phone)) {
            errors.rejectValue("phone", REQUIRED, REQUIRED);
        } else if (!phone.matches("^[0-9]+[0-9\\-]*$")) {
            errors.rejectValue("phone", "Invalid phone format", "Invalid phone format");
        }
    }

    /**
     * This Validator validates *just* Pet instances
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Owner.class.isAssignableFrom(clazz);
    }


}
