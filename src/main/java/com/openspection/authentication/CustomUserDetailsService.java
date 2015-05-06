package com.openspection.authentication;

import com.openspection.model.Person;
import com.openspection.model.Role;
import com.openspection.service.PersonService;
import com.openspection.service.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by greg on 26/04/15.
 */
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        PersonService tvoPersonService = new PersonService();
        Person tvoPerson = tvoPersonService.getPersonByEmail(s);
        if (tvoPerson == null) {
            throw new UsernameNotFoundException("Invalid credentials");
        }

        boolean tvbAccountNonExpired = true;
        boolean tvbCredentialsNonExpired = true;
        boolean tvbAccountNonLocked = true;

        RoleService tvoRoleService = new RoleService();

        List<Role> tvlRoles = tvoRoleService.getAllRolesForPerson(tvoPerson.getId());

        //I am passing a hashed password to this service. Will it work?
        return new User(
                tvoPerson.getEmail(),
                tvoPerson.getPassword(),
                tvoPerson.getIsenabled(),
                tvbAccountNonExpired,
                tvbCredentialsNonExpired,
                tvbAccountNonLocked,
                getAuthorities(tvlRoles));

    }
    public List<String> getRolesAsList(List<Role> roles) {
        List <String> rolesAsList = new ArrayList<String>();
        for(Role role : roles){
            rolesAsList.add(role.getName());
        }
        return rolesAsList;
    }
    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRolesAsList(roles));
        return authList;
    }
}
