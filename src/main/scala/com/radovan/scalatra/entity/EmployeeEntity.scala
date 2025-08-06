package com.radovan.scalatra.entity

import jakarta.persistence.{Column, Entity, GeneratedValue, GenerationType, Id, Table}

import scala.beans.BeanProperty

@Entity
@Table(name = "employees")
@SerialVersionUID(1L)
class EmployeeEntity extends Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @BeanProperty var id: java.lang.Long = _

  @Column(name = "first_name",nullable = false,length = 30)
  @BeanProperty var firstName: String = _

  @Column(name = "last_name",nullable = false,length = 30)
  @BeanProperty var lastName: String = _

  @Column(nullable = false,length = 40,unique = true)
  @BeanProperty var email: String = _

  @Column(nullable = false,length = 50)
  @BeanProperty var position: String = _

  @Column(nullable = false)
  @BeanProperty var salary: Float = _


  override def toString = s"EmployeeEntity($id, $firstName, $lastName, $email, $position, $salary)"
}
