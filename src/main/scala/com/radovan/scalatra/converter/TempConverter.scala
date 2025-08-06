package com.radovan.scalatra.converter

import com.radovan.scalatra.dto.EmployeeDto
import com.radovan.scalatra.entity.EmployeeEntity
import org.modelmapper.ModelMapper

class TempConverter(mapper: ModelMapper) {

  def employeeEntityToDto(employeeEntity: EmployeeEntity): EmployeeDto = {
    mapper.map(employeeEntity, classOf[EmployeeDto])

  }

  def employeeDtoToEntity(employeeDto: EmployeeDto): EmployeeEntity = {
    mapper.map(employeeDto, classOf[EmployeeEntity])

  }
}
