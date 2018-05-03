package com.persistencia.persistencia_cliente.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
@Accessors(chain= true)
public class Cliente {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    private Cliente(final Cliente.Builder builder) {
        this.name = builder.name;
        this.email = builder.email;


    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonGetter("email")
    public String getEmail() {
        return email;
    }



    public static class Builder {

        private String name;
        private String email;

        public Builder name(final String newAmount) {
            this.name = newAmount;
            return this;
        }

        public Builder email(final String newCategory) {
            this.email = newCategory;
            return this;
        }

        public Cliente build() {
            return new Cliente(this);
        }

    }
}
