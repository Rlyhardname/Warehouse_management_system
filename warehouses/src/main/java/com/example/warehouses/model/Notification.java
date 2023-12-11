package com.example.warehouses.model;

import com.example.warehouses.configurations.Enum.ActivityType;
import com.example.warehouses.model.user.UserImpl;
import com.example.warehouses.model.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    private UserImpl owner;
    @ManyToOne
    private UserImpl agent;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    @ManyToOne
    private Warehouse warehouse;
    private LocalDate timeSent;
    private boolean isUnread;

   public Notification(UserImpl owner, UserImpl agent, Warehouse warehouse, ActivityType activityType) {
      this.owner = owner;
      this.agent = agent;
      this.warehouse = warehouse;
      this.activityType = activityType;
      this.isUnread = true;
      timeSent = LocalDate.now();
   }

}
