package br.com.fiap.authentication.model;

import br.com.fiap.pessoa.model.Pessoa;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * É o usuário de uma determinada pessoa nos sistemas da empresa
 */

@Entity
@Table(name = "TB_USER", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_EMAIL", columnNames = "EMAIL_USER")
})
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_USER")
    @Column(name = "ID_USER")
    private Long id;

    @Column(name = "EMAIL_USER", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "PESSOA",
            referencedColumnName = "ID_PESSOA", foreignKey = @ForeignKey(name = "FK_USER_PESSOA")
    )
    private Pessoa pessoa;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "TB_USER_PROFILE",
            joinColumns = {
                    @JoinColumn(name = "USER", referencedColumnName = "ID_USER", foreignKey = @ForeignKey(name = "FK_USER_PROFILE"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "PROFILE", referencedColumnName = "ID_PROFILE", foreignKey = @ForeignKey(name = "FK_PROFILE_USER"))
            }
    )
    private Set<Profile> profiles = new LinkedHashSet<>();


    public User() {
    }

    public User(Long id, String email, String password, Pessoa pessoa, Set<Profile> profiles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.pessoa = pessoa;
        this.profiles = profiles;
    }

    public User addPerfil(Profile profile) {
        this.profiles.add( profile );
        return this;
    }

    public User removePerfil(Profile profile) {
        this.profiles.remove( profile );
        return this;
    }

    public Set<Profile> getProfiles() {
        return Collections.unmodifiableSet( profiles );
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public User setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", pessoa=" + pessoa +
                ", profiles=" + profiles +
                '}';
    }
}