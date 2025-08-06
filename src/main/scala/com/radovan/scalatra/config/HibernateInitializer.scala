package com.radovan.scalatra.config

import com.radovan.scalatra.utils.HibernateUtil
import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class HibernateInitializer {

  @Inject var hibernateUtil: HibernateUtil = _

  @PostConstruct
  def init(): Unit = {
    println("HibernateBootstrap initialized.")
    hibernateUtil.getSessionFactory // Triggeruje SessionFactory i schema update
  }
}

