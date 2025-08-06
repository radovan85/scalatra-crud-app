package com.radovan.scalatra.repositories.impl

import com.radovan.scalatra.entity.EmployeeEntity
import com.radovan.scalatra.repositories.EmployeeRepository
import jakarta.persistence.criteria.{CriteriaBuilder, CriteriaQuery, Predicate, Root}
import org.hibernate.{Session, SessionFactory}

import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters._

class EmployeeRepositoryImpl(sessionFactory: SessionFactory) extends EmployeeRepository {

  private def withSession[T](block: Session => T): T = {
    val session = sessionFactory.openSession()
    val transaction = session.beginTransaction()

    try {
      val result = block(session)
      transaction.commit()
      result
    } catch {
      case e: Exception =>
        transaction.rollback()
        throw e
    } finally {
      session.close()
    }
  }

  override def findAll: Array[EmployeeEntity] = {
    withSession { session =>
      val cb: CriteriaBuilder = session.getCriteriaBuilder
      val cq: CriteriaQuery[EmployeeEntity] = cb.createQuery(classOf[EmployeeEntity])
      val root: Root[EmployeeEntity] = cq.from(classOf[EmployeeEntity])
      cq.select(root)

      session.createQuery(cq).getResultList.asScala.toArray
    }
  }


  override def deleteById(employeeId: Long): Unit = {
    withSession { session =>
      val employeeEntity = session.get(classOf[EmployeeEntity], employeeId)
      if (employeeEntity != null) session.remove(employeeEntity)
    }
  }

  override def findById(employeeId: Long): Option[EmployeeEntity] = {
    withSession { session =>
      val cb: CriteriaBuilder = session.getCriteriaBuilder
      val cq: CriteriaQuery[EmployeeEntity] = cb.createQuery(classOf[EmployeeEntity])
      val root: Root[EmployeeEntity] = cq.from(classOf[EmployeeEntity])
      val predicates: Array[Predicate] = Array(cb.equal(root.get("id"), employeeId))
      cq.where(predicates: _*)
      val results = session.createQuery(cq).getResultList.asScala.toList
      results.headOption
    }
  }

  override def save(employeeEntity: EmployeeEntity): EmployeeEntity = {
    withSession { session =>
      if (employeeEntity.getId != null) {
        session.merge(employeeEntity)
      } else {
        session.persist(employeeEntity)
      }
      session.flush()
      employeeEntity
    }
  }


  override def findByEmail(email: String): Option[EmployeeEntity] = {
    withSession { session =>
      val cb: CriteriaBuilder = session.getCriteriaBuilder
      val cq: CriteriaQuery[EmployeeEntity] = cb.createQuery(classOf[EmployeeEntity])
      val root: Root[EmployeeEntity] = cq.from(classOf[EmployeeEntity])
      val predicates: Array[Predicate] = Array(cb.equal(root.get("email"), email))
      cq.where(predicates: _*)
      val results = session.createQuery(cq).getResultList.asScala.toList
      results.headOption
    }
  }
}
