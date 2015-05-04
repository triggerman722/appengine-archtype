package com.openspection.service;


import com.openspection.model.Role;
import com.openspection.persistence.SystemDataAccess;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RoleService {

    public final List< Role> getAllRolesForPerson(Long fvlPersonid) {
        Object[][] tvoObject = new Object[1][2];
        tvoObject[0][0] = "personid";
        tvoObject[0][1] = fvlPersonid;
        List<Role> ppAll = SystemDataAccess.getWithParams("select p from Role  p where p.personid in (:personid) ", tvoObject);

        return ppAll;
    }

}
