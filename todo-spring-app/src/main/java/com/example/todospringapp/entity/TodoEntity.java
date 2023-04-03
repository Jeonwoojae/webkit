package com.example.todospringapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Todo")
// TodoRepository 클래스의 searchByUserId 메소드가 실행할 SQL을 쿼리로 지정한다.
@NamedQuery(name = "TodoRepository.searchByUserId",
query = "select t from TodoEntity t where t.userId = ?1")
public class TodoEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String id;
    private String userId;
    private String title;
    private boolean done;
}
