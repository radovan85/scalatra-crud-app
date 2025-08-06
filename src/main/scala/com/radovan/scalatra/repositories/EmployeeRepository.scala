package com.radovan.scalatra.repositories

import com.radovan.scalatra.entity.EmployeeEntity

trait EmployeeRepository {

  def findAll:Array[EmployeeEntity]

  def deleteById(employeeId:Long):Unit

  def findById(employeeId:Long):Option[EmployeeEntity]

  def save(employeeEntity: EmployeeEntity):EmployeeEntity

  def findByEmail(email:String):Option[EmployeeEntity]

}
