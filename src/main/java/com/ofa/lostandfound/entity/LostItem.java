package com.ofa.lostandfound.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Objects;


@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lost_item", indexes = {
        @Index(name = "idx_lost_item_name", columnList = "name")
})

public class LostItem extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @Column(name = "place", nullable = false)
    @NotEmpty(message = "Place cannot be empty")
    private String place;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LostItem lostItem = (LostItem) o;
        return quantity == lostItem.quantity && Objects.equals(name, lostItem.name) && Objects.equals(place, lostItem.place);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + quantity;
        result = 31 * result + Objects.hashCode(place);
        return result;
    }
}
