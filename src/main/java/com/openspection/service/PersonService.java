package com.openspection.service;


import com.openspection.model.Person;
import com.openspection.model.Role;

import com.openspection.model.Userprofile;
import com.openspection.persistence.SystemDataAccess;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PersonService {

    //PEOPLE
    //GET
    @RequestMapping(value = "people", method = RequestMethod.GET)
    @ResponseBody
    public final List< Person> getAllPeople() {
        return SystemDataAccess.getAll("select p from Person p ");
    }

    @RequestMapping(value = "people/{id}", method = RequestMethod.GET)
    @ResponseBody
    public final Person getPerson(@PathVariable("id") final Long id) {
        return (Person) SystemDataAccess.get(Person.class, id);
    }

    public final Person getPersonByEmail(final String tvsEmail) {
        Object[][] tvoObject = new Object[1][2];
        tvoObject[0][0] = "email";
        tvoObject[0][1] = tvsEmail;
        List<Person> ppAll = SystemDataAccess.getWithParams("select p from Person  p where p.email in (:email) ", tvoObject);
        if (ppAll.size() > 0)
        {
            return (Person) ppAll.get(0);
        }
        return null;
    }

    @RequestMapping(value = "profile/{id}", method = RequestMethod.GET)
    @ResponseBody
    public final Userprofile getPersonProfile(@PathVariable("id") final Long id) {
        return (Userprofile) SystemDataAccess.get(Userprofile.class, id.toString());
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    @ResponseBody
    public final Userprofile getLoggedInPersonProfile(Principal fvoPrincipal) {
       PersonService tvoPersonService = new PersonService();
        Person tvoPerson = tvoPersonService.getPersonByEmail(fvoPrincipal.getName());
        
        return (Userprofile) SystemDataAccess.get(Userprofile.class, tvoPerson.getId().toString());
    }

    //POST
    @RequestMapping(value = "people", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public final Person addPerson(@RequestBody final Person p) {
	
        Person tvoReturn = getPersonByEmail(p.getEmail());

        if (tvoReturn != null)
        {
			return (Person) tvoReturn;
        }
        p.setId(null);
        p.setIsenabled(true);

        BCryptPasswordEncoder tvoBCryptEncoder = new BCryptPasswordEncoder();

        p.setPassword(tvoBCryptEncoder.encode(p.getPassword()));

        Person pNew = (Person) SystemDataAccess.add(p);

        Role tvoRole = new Role();
        tvoRole.setName("ROLE_CANPOST");
        tvoRole.setPersonid(pNew.getId());
        SystemDataAccess.add(tvoRole);

        Userprofile upNew = new Userprofile();
        upNew.setId_str(pNew.getId().toString());
        upNew.setName(pNew.getName());
        upNew.setReputation(1L);
        SystemDataAccess.add(upNew);
        return pNew;
    }

    @RequestMapping(value = "people/{id}/changepassword/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable("id") final Long id,
                               @RequestParam String oldpassword,
                               @RequestParam String newpassword,
                               @RequestParam String confirmpassword,
                               Principal fvoPrincipal ) {

        if (newpassword == null)
            return;
        if (oldpassword == null)
            return;
        if (confirmpassword == null)
            return;

        if (!newpassword.equals(confirmpassword))
            return;

        if (!doesExist(id))
            return;

        Person tvoPerson = getPerson(id);
        if (!tvoPerson.getEmail().equals(fvoPrincipal.getName()))
            return;

        BCryptPasswordEncoder tvoBCryptEncoder = new BCryptPasswordEncoder();

        if (!tvoBCryptEncoder.matches(oldpassword, tvoPerson.getPassword()))
            return;

        String tvsEncryptedNewPassword = tvoBCryptEncoder.encode(newpassword);

        Person tvoNewperson = new Person();
        BeanUtils.copyProperties(tvoPerson, tvoNewperson);

        tvoNewperson.setPassword(tvsEncryptedNewPassword);
        setPerson(id, tvoNewperson, fvoPrincipal);

    }

    public boolean doesExist(Long personid){
        Person tvoPerson = getPerson(personid);
        if (tvoPerson!=null)
            return true;
        return false;
    }
    //PUT 
    @RequestMapping(value = "people/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public final Person setPerson(@PathVariable("id") final Long id, @RequestBody final Person p, Principal fvoPrincipal) {

        if (!doesExist(id))
            return null;

        // Hmm...this confirms the caller is the Person
        // but does mean you can't change your email addr.
        Person tvoPerson = getPerson(id);
        if (!tvoPerson.getEmail().equals(fvoPrincipal.getName()))
            return null;

        return (Person) SystemDataAccess.set(Person.class, id, p);
    }

    //DELETE
    @RequestMapping(value = "people/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public final void deletePerson(@PathVariable("id") final Long id) {
        SystemDataAccess.delete(Person.class, id);
    }
}
