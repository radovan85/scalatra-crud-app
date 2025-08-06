package com.radovan.scalatra.dto

import jakarta.validation.constraints._
import scala.beans.BeanProperty

@SerialVersionUID(1L)
class EmployeeDto extends Serializable {

  @BeanProperty
  var id: java.lang.Long = _

  @BeanProperty
  @NotBlank
  @Size(min = 2, max = 30)
  var firstName: String = _

  @BeanProperty
  @NotBlank
  @Size(min = 2, max = 30)
  var lastName: String = _

  @BeanProperty
  @NotBlank
  @Size(max = 50)
  @Email
  var email: String = _

  @BeanProperty
  @NotBlank
  @Size(min = 2, max = 40)
  var position: String = _

  @BeanProperty
  @NotNull
  @DecimalMin("100")
  @DecimalMax("10000")
  var salary: java.lang.Float = _


  override def toString = s"EmployeeDto($id, $firstName, $lastName, $email, $position, $salary)"
}
