package br.com.fiap.authentication.model;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Mapeia um conjunto de permissões que uma pessoa
 * pode ter nos diversos sistemas da empresa
 */
@Entity
@Table(name = "TB_PROFILE", uniqueConstraints = {
        @UniqueConstraint(name = "UK_PROFILE_NOME", columnNames = "NM_PROFILE")
})
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PROFILE")
    @Column(name = "ID_PROFILE")
    private Long id;

    @Column(name = "NM_PROFILE", nullable = false)
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "TB_PROFILE_ROLE",
            joinColumns = {
                    @JoinColumn(name = "PROFILE", referencedColumnName = "ID_PROFILE", foreignKey = @ForeignKey(name = "FK_PROFILE_ROLE"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE", referencedColumnName = "ID_ROLE", foreignKey = @ForeignKey(name = "FK_ROLE_PROFILE"))
            }
    )
    private Set<Role> roles = new LinkedHashSet<>();


    public Profile() {
    }

    public Profile(Long id, String nome, Set<Role> roles) {
        this.id = id;
        this.nome = nome;
        this.roles = roles;
    }

    public Profile addRole(Role role) {
        this.roles.add( role );
        return this;
    }

    public Profile removeRole(Role role) {
        this.roles.remove( role );
        return this;
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet( this.roles );
    }

    public Long getId() {
        return id;
    }

    public Profile setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Profile setNome(String nome) {
        this.nome = nome;
        return this;
    }


    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", roles=" + roles +
                '}';
    }
}