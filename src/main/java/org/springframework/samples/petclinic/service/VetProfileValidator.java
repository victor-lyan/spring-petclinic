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

import org.springframework.samples.petclinic.model.Vet;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class VetProfileValidator implements Validator {

    private static final String REQUIRED = "required";

    @Override
    public void validate(Object obj, Errors errors) {
        Vet vet = (Vet) obj;

        // first name validation
        if (!StringUtils.hasLength(vet.getFirstName().trim())) {
            errors.rejectValue("firstName", REQUIRED, REQUIRED);
        }

        // last name validation
        if (!StringUtils.hasLength(vet.getLastName().trim())) {
            errors.rejectValue("lastName", REQUIRED, REQUIRED);
        }

        // working days validation
        if (!StringUtils.hasLength(vet.getWorkingDays().trim())) {
            errors.rejectValue("workingDays", REQUIRED, REQUIRED);
        }

        // working hours validation
        if (!StringUtils.hasLength(vet.getWorkingHours().trim())) {
            errors.rejectValue("workingHours", REQUIRED, REQUIRED);
        }

        // TODO: validate working days and hours with some patterns
    }

    /**
     * This Validator validates *just* Pet instances
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Vet.class.isAssignableFrom(clazz);
    }


}
