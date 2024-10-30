package vn.iostar.SpringBoot_st4.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.MODULE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
@Data
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryID")
    Long id;
    @Column(name = "categoryName")
    String name;
    String images;
    int status;
}
