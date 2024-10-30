package vn.iostar.SpringBoot_st4.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.MODULE)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryModel {
    @Id
    Long id;
    String name;
    String images;
    int status;
    Boolean isEdit = false;
}
