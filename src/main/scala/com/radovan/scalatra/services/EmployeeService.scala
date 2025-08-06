package com.radovan.scalatra.services

import com.radovan.scalatra.dto.EmployeeDto

trait EmployeeService {

  def addEmployee(employee:EmployeeDto):EmployeeDto

  def getEmployeeById(employeeId:Long):EmployeeDto

  def updateEmployee(employee: EmployeeDto,employeeId:Long):EmployeeDto

  def deleteEmployee(employeeId:Long):Unit

  def listAll:Array[EmployeeDto]
}
