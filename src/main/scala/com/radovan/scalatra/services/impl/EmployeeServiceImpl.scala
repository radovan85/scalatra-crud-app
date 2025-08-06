package com.radovan.scalatra.services.impl

import com.radovan.scalatra.converter.TempConverter
import com.radovan.scalatra.dto.EmployeeDto
import com.radovan.scalatra.exceptions.{ExistingInstanceException, InstanceUndefinedException}
import com.radovan.scalatra.repositories.EmployeeRepository
import com.radovan.scalatra.services.EmployeeService

import scala.collection.mutable.ArrayBuffer

class EmployeeServiceImpl(
                           employeeRepository: EmployeeRepository,
                           tempConverter: TempConverter
                         ) extends EmployeeService {

  override def addEmployee(employee: EmployeeDto): EmployeeDto = {
    val employeeOptional = employeeRepository.findByEmail(employee.getEmail)
    employeeOptional match {
      case Some(value) => throw new ExistingInstanceException("This email exists already")
      case None =>
    }
    val storedEmployee = employeeRepository.save(tempConverter.employeeDtoToEntity(employee))
    tempConverter.employeeEntityToDto(storedEmployee)
  }

  override def getEmployeeById(employeeId: Long): EmployeeDto = {
    employeeRepository.findById(employeeId) match {
      case Some(employeeEntity) => tempConverter.employeeEntityToDto(employeeEntity)
      case None => throw new InstanceUndefinedException("The employee has not been found!")
    }
  }

  override def updateEmployee(employee: EmployeeDto, employeeId: Long): EmployeeDto = {
    getEmployeeById(employeeId)
    employee.setId(employeeId)
    val updatedEmployee = employeeRepository.save(tempConverter.employeeDtoToEntity(employee))
    tempConverter.employeeEntityToDto(updatedEmployee)
  }

  override def deleteEmployee(employeeId: Long): Unit = {
    getEmployeeById(employeeId)
    employeeRepository.deleteById(employeeId)
  }

  override def listAll: Array[EmployeeDto] = {
    employeeRepository.findAll.collect {
      case employeeEntity => tempConverter.employeeEntityToDto(employeeEntity)
    }
  }
}
