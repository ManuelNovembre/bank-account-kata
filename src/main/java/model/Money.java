package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class Money {

    private Integer euros;
    private Integer cents;
}
