package com.radovan.scalatra.server

import com.radovan.scalatra.config.ComponentRegistry
import com.radovan.scalatra.controllers.EmployeeController
import com.radovan.scalatra.services.impl.EmployeeServiceImpl
import com.radovan.scalatra.repositories.impl.EmployeeRepositoryImpl
import com.radovan.scalatra.converter.TempConverter
import com.radovan.scalatra.utils.HibernateUtil
import org.eclipse.jetty.ee10.servlet.{ServletContextHandler, ServletHolder}
import org.eclipse.jetty.server.Server
import org.jboss.weld.environment.se.{Weld, WeldContainer}
import org.modelmapper.ModelMapper

object JettyLauncher {
  def main(args: Array[String]): Unit = {
    val weld = new Weld()
      .disableDiscovery()
      .addExtension(new ComponentRegistry())
      .addBeanClass(classOf[EmployeeController])
      .addBeanClass(classOf[EmployeeServiceImpl])
      .addBeanClass(classOf[EmployeeRepositoryImpl])
      .addBeanClass(classOf[TempConverter])
      .addBeanClass(classOf[ModelMapper])
      .addBeanClass(classOf[HibernateUtil])

    val container: WeldContainer = weld.initialize()

    val server = new Server(8080)
    val context = new ServletContextHandler(ServletContextHandler.SESSIONS)
    context.setContextPath("/")

    server.setHandler(context)

    try {
      server.start()

      val employeeController = container.select(classOf[EmployeeController]).get()

      context.addServlet(
        new ServletHolder("employeeController", employeeController),
        "/api/employees/*"
      )

      println("✅ Server je pokrenut na http://localhost:8080")
      println("✅ Test endpoint: http://localhost:8080/api/employees/test")

      server.join()
    } catch {
      case e: Exception =>
        println(s"❌ Greška: ${e.getMessage}")
        e.printStackTrace()
        System.exit(1)
    } finally {
      container.shutdown()
    }
  }
}
