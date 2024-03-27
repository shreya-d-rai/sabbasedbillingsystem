package com.webtree.slabbasedbillingsystem.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "customer")
public class CustomerEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	Integer customerId;
	
	@Column(name = "customer_name")
	String customerName;
	
	@Column(name = "contact_number")
	Long contactNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	AddressEntity address;
}
