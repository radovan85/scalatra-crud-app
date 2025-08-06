package com.radovan.scalatra.controllers

import com.radovan.scalatra.dto.EmployeeDto
import com.radovan.scalatra.services.EmployeeService
import com.radovan.scalatra.utils.{ResponsePackage, ValidatorSupport}
import flexjson.JSONDeserializer
import org.apache.hc.core5.http.HttpStatus
import org.scalatra.ScalatraServlet


class EmployeeController(employeeService: EmployeeService) extends ScalatraServlet
  with ValidatorSupport
  with ErrorsController {



  get("/list") {
    new ResponsePackage[Array[EmployeeDto]](employeeService.listAll,HttpStatus.SC_OK).toResponse(response)
  }

  post("/add") {
    val json = request.body
    val employee = new JSONDeserializer[EmployeeDto]()
      .use(null, classOf[EmployeeDto])
      .deserialize(json, classOf[EmployeeDto])

    validateOrHalt(employee)
    val storedEmployee = employeeService.addEmployee(employee)
    new ResponsePackage[EmployeeDto](storedEmployee,HttpStatus.SC_CREATED).toResponse(response)
  }

  get("/get/:id") {
    val employeeId = params("id").toLong
    new ResponsePackage[EmployeeDto](employeeService.getEmployeeById(employeeId), HttpStatus.SC_OK).toResponse(response)
  }

  put("/update/:id") {
    val employeeId = params("id").toLong
    val json = request.body
    val employee = new JSONDeserializer[EmployeeDto]()
      .use(null, classOf[EmployeeDto])
      .deserialize(json, classOf[EmployeeDto])

    validateOrHalt(employee)
    employeeService.updateEmployee(employee, employeeId)
    new ResponsePackage[String](s"Employee with id $employeeId has been updated!",HttpStatus.SC_OK).toResponse(response)
  }

  delete("/delete/:id") {
    val employeeId = params("id").toLong
    employeeService.deleteEmployee(employeeId)
    new ResponsePackage[String](s"Employee with id $employeeId has been permanently removed!",HttpStatus.SC_OK).toResponse(response)
  }


  get("/test") {
    new ResponsePackage[String]("EmployeeController is working",HttpStatus.SC_OK).toResponse(response)
  }


}