package com.radovan.scalatra.config

import com.radovan.scalatra.controllers.EmployeeController
import com.radovan.scalatra.converter.TempConverter
import com.radovan.scalatra.repositories.impl.EmployeeRepositoryImpl
import com.radovan.scalatra.repositories.EmployeeRepository
import com.radovan.scalatra.services.EmployeeService
import com.radovan.scalatra.services.impl.EmployeeServiceImpl
import com.radovan.scalatra.utils.HibernateUtil
import jakarta.enterprise.inject.spi.{AfterBeanDiscovery, BeanManager, Extension}
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.context.spi.CreationalContext
import jakarta.enterprise.event.Observes
import org.hibernate.SessionFactory
import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration.AccessLevel
import org.modelmapper.convention.MatchingStrategies

import java.util.function.Function

class ComponentRegistry extends Extension {

  def afterBeanDiscovery(@Observes abd: AfterBeanDiscovery, bm: BeanManager): Unit = {

    abd.addBean()
      .addType(classOf[EmployeeService])
      .addType(classOf[EmployeeServiceImpl])
      .beanClass(classOf[EmployeeServiceImpl])
      .scope(classOf[ApplicationScoped])
      .createWith(new Function[CreationalContext[Any], Any] {
        override def apply(ctx: CreationalContext[Any]): Any = {
          val repoBean = bm.getBeans(classOf[EmployeeRepository]).iterator().next()
          val repoCtx = bm.createCreationalContext(repoBean)
          val repoRef = bm.getReference(repoBean, classOf[EmployeeRepository], repoCtx)

          val converterBean = bm.getBeans(classOf[TempConverter]).iterator().next()
          val converterCtx = bm.createCreationalContext(converterBean)
          val converterRef = bm.getReference(converterBean, classOf[TempConverter], converterCtx)

          new EmployeeServiceImpl(
            repoRef.asInstanceOf[EmployeeRepository],
            converterRef.asInstanceOf[TempConverter]
          )
        }
      })


    abd.addBean()
      .addType(classOf[EmployeeRepository])
      .addType(classOf[EmployeeRepositoryImpl])
      .beanClass(classOf[EmployeeRepositoryImpl])
      .scope(classOf[ApplicationScoped])
      .createWith(new Function[CreationalContext[Any], Any] {
        override def apply(ctx: CreationalContext[Any]): Any = {
          val sessionFactoryBean = bm.getBeans(classOf[SessionFactory]).iterator().next()
          val sessionFactoryCreational = bm.createCreationalContext(sessionFactoryBean)
          val sessionFactoryInstance = bm.getReference(sessionFactoryBean, classOf[SessionFactory], sessionFactoryCreational)

          new EmployeeRepositoryImpl(sessionFactoryInstance.asInstanceOf[SessionFactory])
        }
      })


    abd.addBean()
      .addType(classOf[TempConverter])
      .beanClass(classOf[TempConverter])
      .scope(classOf[ApplicationScoped])
      .createWith(new Function[CreationalContext[Any], Any] {
        override def apply(ctx: CreationalContext[Any]): Any = {
          val mapperBean = bm.getBeans(classOf[ModelMapper]).iterator().next()
          val mapperCtx = bm.createCreationalContext(mapperBean)
          val mapperRef = bm.getReference(mapperBean, classOf[ModelMapper], mapperCtx)

          new TempConverter(mapperRef.asInstanceOf[ModelMapper])
        }
      })


    abd.addBean()
      .addType(classOf[ModelMapper])
      .beanClass(classOf[ModelMapper])
      .scope(classOf[ApplicationScoped])
      .createWith(new Function[CreationalContext[Any], Any] {
        override def apply(ctx: CreationalContext[Any]): Any = {
          val mapper = new ModelMapper()
          mapper.getConfiguration
            .setAmbiguityIgnored(true)
            .setFieldAccessLevel(AccessLevel.PRIVATE)
            .setMatchingStrategy(MatchingStrategies.STRICT)
          mapper
        }
      })

    abd.addBean()
      .addType(classOf[HibernateUtil])
      .beanClass(classOf[HibernateUtil])
      .scope(classOf[ApplicationScoped])
      .createWith(new Function[CreationalContext[Any], Any] {
        override def apply(ctx: CreationalContext[Any]): Any = new HibernateUtil()
      })

    abd.addBean()
      .addType(classOf[EmployeeController])
      .beanClass(classOf[EmployeeController])
      .scope(classOf[jakarta.enterprise.context.Dependent])
      .createWith(new Function[CreationalContext[Any], Any] {
        override def apply(ctx: CreationalContext[Any]): Any = {
          val serviceBean = bm.getBeans(classOf[EmployeeService]).iterator().next()
          val serviceCtx = bm.createCreationalContext(serviceBean)
          val serviceRef = bm.getReference(serviceBean, classOf[EmployeeService], serviceCtx)

          new EmployeeController(serviceRef.asInstanceOf[EmployeeService])
        }
      })


    abd.addBean()
      .addType(classOf[SessionFactory])
      .beanClass(classOf[SessionFactory])
      .scope(classOf[ApplicationScoped])
      .createWith(new Function[CreationalContext[Any], Any] {
        override def apply(ctx: CreationalContext[Any]): Any = {
          new HibernateUtil().getSessionFactory
        }
      })

    abd.addBean()
      .addType(classOf[HibernateInitializer])
      .beanClass(classOf[HibernateInitializer])
      .scope(classOf[ApplicationScoped])
      .createWith(new Function[CreationalContext[Any], Any] {
        override def apply(ctx: CreationalContext[Any]): Any = {
          val initializer = new HibernateInitializer()
          val hibernateUtilInstance = new HibernateUtil()
          initializer.hibernateUtil = hibernateUtilInstance
          initializer.init()
          initializer
        }
      })


  }
}
