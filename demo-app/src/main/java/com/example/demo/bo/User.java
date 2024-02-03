package com.example.demo.bo;


import com.example.demo.constants.MultiTenantProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.annotations.AdditionalCriteria;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author i565244
 */
@Entity
@Table(name="tl_user")
@AdditionalCriteria("this.tenant_Id=:" + "tenant_id")
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = MultiTenantProperties.MULTITENANT_CONTEXT_PROPERTY, discriminatorType = DiscriminatorType.STRING, columnDefinition = "BIGINT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id	//主键id
    @GeneratedValue(strategy=GenerationType.IDENTITY)//主键生成策略
    @Column(name="id")//数据库字段名
    private Long id;

    @Column(name="no",insertable=false,updatable = false)
    private String no;

    @Column(name="name")
    private String name;

    @Column(name="age")
    private Integer age;

    @Column(name="address")
    private String address;


    @Column(name = "TENANT_ID", nullable = true, insertable = false, updatable = false)
    @JsonIgnore
    private Long tenant_Id;

    @Column(name="createTime")
    private LocalDateTime createTime;

    @Column(name="updateTime")
    private String updateTime;
}
