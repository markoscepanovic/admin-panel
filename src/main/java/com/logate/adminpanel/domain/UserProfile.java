package com.logate.adminpanel.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userprofile")
public class UserProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_profile_role",
        joinColumns = @JoinColumn(name = "user_profiles_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "ID"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_profile_work_group",
        joinColumns = @JoinColumn(name = "user_profiles_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "work_groups_id", referencedColumnName = "ID"))
    private Set<WorkGroup> workGroups = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_profile_department",
        joinColumns = @JoinColumn(name = "user_profiles_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "departments_id", referencedColumnName = "ID"))
    private Set<Department> departments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<WorkGroup> getWorkGroups() {
        return workGroups;
    }

    public void setWorkGroups(Set<WorkGroup> workGroups) {
        this.workGroups = workGroups;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserProfile userProfile = (UserProfile) o;
        return Objects.equals(id, userProfile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + id +
            ", fullName='" + fullName + "'" +
            '}';
    }
}
