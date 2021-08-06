//package com.vuan.entity;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.EntityListeners;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import lombok.Data;
//
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
//@Data
//public abstract class CreateAuditable {
//	
//	//tu gen ra cot cho minh
//	@CreatedDate
//	//ham lay thoi gian
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "created_date")
//	private Date createdDate;
//
//	@CreatedBy
//	@JoinColumn(name = "created_by")
//	@ManyToOne(fetch = FetchType.EAGER)
//	private User createdBy;
//
//}
