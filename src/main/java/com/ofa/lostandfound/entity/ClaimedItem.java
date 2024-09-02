package com.ofa.lostandfound.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "claimed_item", indexes = {
        @Index(name = "idx_claimed_item_user_id", columnList = "user_id")
})
public class ClaimedItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "lost_item_id")
    private LostItem lostItem;
    @Column(name="user_id", nullable = false)
    private String userId;
    @Column(name="quantity", nullable = false)
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ClaimedItem that = (ClaimedItem) o;
        return quantity == that.quantity && Objects.equals(lostItem, that.lostItem) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(lostItem);
        result = 31 * result + Objects.hashCode(userId);
        result = 31 * result + quantity;
        return result;
    }
}