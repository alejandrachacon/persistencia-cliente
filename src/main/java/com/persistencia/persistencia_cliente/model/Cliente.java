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
    private String category;

    private Cliente(final Cliente.Builder builder) {
        this.name = builder.name;
        this.category = builder.category;


    }

    @JsonGetter("name")
    public String getAmount() {
        return name;
    }

    @JsonGetter("category")
    public String getCategory() {
        return category;
    }



    public static class Builder {

        private String name;
        private String category;

        public Builder name(final String newAmount) {
            this.name = newAmount;
            return this;
        }

        public Builder category(final String newCategory) {
            this.category = newCategory;
            return this;
        }

        public Cliente build() {
            return new Cliente(this);
        }

    }
}
